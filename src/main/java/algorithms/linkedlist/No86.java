package algorithms.linkedlist;

/**
 * 86. 分隔链表
 */
public class No86 {
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
 * 与19题类似，用头插法
 */
class Solution86 {
    public ListNode partition(ListNode head, int x) {
        ListNode dummyHead = new ListNode(0);
        dummyHead.next = head;
        ListNode insertPrev = dummyHead;
        ListNode curr = head;
        ListNode guard = curr;

        //找到第1个不小于x的节点
        while (curr != null) {
            if (curr.val >= x) {
                guard = curr;
                break;
            }
            insertPrev = insertPrev.next;
            curr = curr.next;
        }

        ListNode toMove = null;
        ListNode prev = insertPrev;
        while (curr != null) {
            if (curr.val < x) {
                toMove = curr;
                prev.next = curr.next;
                curr = curr.next;
                insertPrev.next = toMove;
                toMove.next = guard;
                insertPrev = insertPrev.next;
            } else {
                curr = curr.next;
                prev = prev.next;
            }
        }
        return dummyHead.next;
    }
}

