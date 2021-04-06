package algorithms.linkedlist;

import java.util.ArrayList;
import java.util.List;

/**
 * 143. 重排链表
 * @author Sean Yu
 */
public class No143 {
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
 * <p>
 * 思路：
 * 由于链表没有散列性质，所以使用散列表或者数组来存放每个节点。
 * 存放过程中提前断开节点之间的链接，避免环的出现
 * 然后用双指针进行拼接即可
 */
class Solution143 {
    public void reorderList(ListNode head) {
        List<ListNode> list = new ArrayList<>();
        while (head != null) {
            ListNode temp = head;
            list.add(head);
            head = head.next;
            //提前断开每个链表节点
            temp.next = null;
        }
        ListNode curr = new ListNode();
        int l = 0, r = list.size() - 1;
        while (l < r) {
            curr.next = list.get(l);
            curr = curr.next;
            curr.next = list.get(r);
            curr = curr.next;
            l++;
            r--;
        }
        if (l == r) {
            curr.next = list.get(l);
        }
        head = list.get(0);
    }

    class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }
}