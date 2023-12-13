package algorithms.linkedlist;

/**
 * @author Sean Yu
 */
public class No82 {
}


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

/**
 * 思路1：
 * 递归
 * （1）明确递归函数本身的定义： 即问题之所求
 * （2）明确出口条件
 * （3）递归求解一般是需要子问题的解，这里问题的"范围"就是"链表的大小"
 *  这里head分需要删除&需要保留2种情况
 */
class Solution82 {
    public ListNode deleteDuplicates(ListNode head) {
        //出口条件
        if(head == null || head.next == null) return head;
        //如果head和它下一个节点值不同，那么head是需要保留的，所以返回head，但head.next要不要保留是不能确定的，所以把它丢给递归函数
        if(head.val != head.next.val){
            head.next = deleteDuplicates(head.next);
            return head;
        }
        //head和它下一个节点值相同，说明head和它的下一个节点都需要被删除，一直删到某个节点值和head不同，或者已经删光了，才停下
        else{
            ListNode cur = head.next;
            while(cur != null && cur.val == head.val){
                cur = cur.next;
            }
            //currently cur.val != head.val;
            return deleteDuplicates(cur);
        }
    }
}
