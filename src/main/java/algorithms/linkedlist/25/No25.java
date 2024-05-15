package algorithms.linkedlist;

import java.util.Arrays;

/**
 * 25. K 个一组翻转链表
 *
 * @author Sean Yu
 */
public class No25 {
    public static void main(String[] args) {
        ListNode n1 = new ListNode(1);
//        ListNode n2 = new ListNode(2);
//        ListNode n3 = new ListNode(3);
//        ListNode n4 = new ListNode(4);
//        ListNode n5 = new ListNode(5);
        int k = 1;
        Solution25 test = new Solution25();
        System.out.println(test.reverseKGroup(connect(n1), k));
    }

    private static ListNode connect(ListNode... nodes) {
        ListNode vh = new ListNode();
        ListNode cur = vh;
        for (ListNode each : nodes) {
            cur.next = each;
            cur = cur.next;
        }
        return vh.next;
    }
}

/**
 * Definition for singly-linked list.
 * public class ListNode {
 * int val;
 * ListNode next;
 * ListNode() {}
 * ListNode(int val) { this.val = val; }
 * ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */

/**
 * 思路：
 * k个一组反转链表，可以将问题拆解为，先将原链表分成一段又一段长度不超过k的链表，然后依次将这些链表进行反转，然后拼接
 * 由于是单向链表，所以要拼接的话，我们需要知道每段长度为K的链表的头部和尾部，以便进行拼接
 * e.g.
 * 1 -> 2 -> 3 -> 4 -> 5 -> 6 -> 7 -> 8 按k=3 进行反转
 * 可以分成3段长度小于等于k的链表，如下
 * [1 -> 2 -> 3] -> [4 -> 5 -> 6] -> [7 -> 8]
 * 我们需要先翻转123，再翻转456，最后78长度不足3，所以不翻转
 * 所以我们要先将 3 和 4 断开，
 * 令123是当前正在翻转的链表，我们需要知道该链表的头部curHead，以及尾部curTail
 * 同时我们需要知道下一段链表的头部nextHead
 */
class Solution25 {
    public ListNode reverseKGroup(ListNode head, int k) {
        //特殊情况
        if (head == null || head.next == null) {
            return head;
        }
        ListNode curHead = head;
        ListNode curTail = head;
        //定位curTail ：一开始curHead和curTail指向同一个位置，因为k个一组，所以从curHead后移k-1步
        for (int i = 0; i < k - 1; i++) {
            curTail = curTail.next;
            //如果后移过程中出现空，说明长度小于k，不用反转，直接返回curHead
            if (curTail == null) {
                return curHead;
            }
        }
        //保存下一段链表的头部
        ListNode nextHead = curTail.next;
        //断开当前链表与下一段链表
        curTail.next = null;
        //对当前链表进行全体反转
        reverse(curHead);
        //反转之后，curTail成了头结点，curHead成了尾节点，用尾节点和下一段链表重新连接起来
        curHead.next = reverseKGroup(nextHead, k);
        //返回新的头结点
        return curTail;
    }

    private void reverse(ListNode head) {
        if (head == null || head.next == null) {
            return;
        }
        reverse(head.next);
        head.next.next = head;
        head.next = null;
    }
}
