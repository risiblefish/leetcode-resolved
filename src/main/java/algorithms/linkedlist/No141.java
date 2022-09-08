package algorithms.linkedlist;

/**
 * 141. 环形链表
 * @author Sean Yu
 */
public class No141 {
}
/**
 * Definition for singly-linked list.
 * class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) {
 *         val = x;
 *         next = null;
 *     }
 * }
 */

/**
 * 思路： 如果有环的话，快指针肯定能追上慢指针
 */
class Solution141 {
    public boolean hasCycle(ListNode head) {
        ListNode slow, fast;
        slow = fast = head;
        while(fast != null){
            slow = slow.next;
            fast = fast.next;
            if(fast == null) {
                return false;
            }
            fast = fast.next;
            if(fast == slow) {
                return true;
            }
        }
        return false;
    }
}
