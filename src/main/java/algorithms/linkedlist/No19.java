package algorithms.linkedlist;

/**
 * 19. 删除链表的倒数第 N 个结点
 * @author Sean Yu
 */
public class No19 {
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
 * 一遍扫描。 通过手动debug得知，使用虚拟头 + 快慢指针
 */
class Solution19 {
    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode dh = new ListNode(1);
        dh.next = head;
        ListNode fast = dh;
        ListNode slow = dh;

        for (int i = 0; i < n; i++) {
            fast = fast.next;
        }

        while (fast.next != null) {
            fast = fast.next;
            slow = slow.next;
        }

        ListNode newNext = slow.next.next;
        slow.next = newNext;
        return dh.next;
    }
}
