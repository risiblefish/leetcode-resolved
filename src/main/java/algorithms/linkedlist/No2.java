package algorithms.linkedlist;

/**
 * 2. 两数相加
 * @author Sean Yu
 */
public class No2 {
}

/**
 * 思路：
 *
 * 由于链表本身已经是逆序了，所以直接将2个链表从头遍历对位相加即可，每一位的和可能产生进位，另外最后一位相加之后仍然可能产生进位，不要忘记这一步
 *
 *
 *
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class Solution2 {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode dummy = new ListNode();
        ListNode cur = dummy;
        int flag = 0, sum = 0;
        while(l1 != null || l2 != null){
            sum = 0;
            if(l1 != null){
                sum += l1.val;
                l1 = l1.next;
            }
            if(l2 != null){
                sum += l2.val;
                l2 = l2.next;
            }
            sum += flag;
            if(sum >= 10){
                flag = 1;
                cur.next = new ListNode(sum - 10);
            }else{
                flag = 0;
                cur.next = new ListNode(sum);
            }
            cur = cur.next;
        }
        //另外最后一位相加之后仍然可能产生进位
        if(flag == 1) cur.next = new ListNode(1);
        return dummy.next;
    }
}
