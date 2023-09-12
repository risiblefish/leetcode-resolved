package algorithms.linkedlist;

/**
 * 19. 删除链表的倒数第 N 个结点
 * @author Sean Yu
 */
public class No19 {
}

/**
 * 思路：
 * 一遍扫描。 通过手动debug得知，使用虚拟头 + 快慢指针
 */
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class Solution19 {
    public ListNode removeNthFromEnd(ListNode head, int n) {
        //因为有可能被删除的节点是头结点，所以要使用虚拟头结点
        ListNode vh = new ListNode();
        vh.next = head;
        ListNode fast = vh;
        while(n > 0){
            fast = fast.next;
            n--;
        }
        ListNode slow = vh;
        while(fast.next != null){
            fast = fast.next;
            slow = slow.next;
        }
        //此时slow.next就是要删除的节点
        slow.next = slow.next.next;
        return vh.next;
    }
}
