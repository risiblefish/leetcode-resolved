package algorithms.linkedlist;

/**
 * 83. 删除排序链表中的重复元素
 * @author Sean Yu
 */
public class No83 {
}

/**
 * 思路：
 * 由于链表已排序，只需不停地删除当前节点后面重复的元素即可
 */
class Solution83 {
    public ListNode deleteDuplicates(ListNode head) {
        if(head == null){
            return head;
        }
        ListNode curr = head;
        while(curr.next != null){
            if(curr.next.val == curr.val){
                curr.next = curr.next.next;
            }else {
                curr = curr.next;
            }
        }
        return head;
    }
}
