package algorithms.linkedlist;

/**
 * 203. 移除链表元素
 * @author Sean Yu
 */
public class No203 {
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
 * 思路： 利用伪头
 * 伪头的作用是，保证头永不为空，最后返回时，只需返回伪头.next
 * 单链表可以用2个指针来控制删除
 * 由于prev在curr之前，所以只需保证curr不为空
 */
class Solution203 {
    public ListNode removeElements(ListNode head, int val) {
        ListNode pseudoHead = new ListNode(0);
        pseudoHead.next = head;
        ListNode prev = pseudoHead;
        ListNode curr = head;
        while (curr != null) {
            if (curr.val == val) {
                prev.next = curr.next;
            } else {
                prev = curr;
            }
            curr = curr.next;
        }
        return pseudoHead.next;
    }
}