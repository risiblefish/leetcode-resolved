package algorithms.stackandqueue;

import algorithms.linkedlist.ListNode;

/**
 * @author Sean Yu
 */
public class No148 {
    public static void main(String[] args) {
        ListNode h = new ListNode(4);
        h.next = new ListNode(2);
        h.next.next = new ListNode(1);
        h.next.next.next = new ListNode(3);
        System.out.println(new Solution148().sortList(h));
    }
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
class Solution148 {
    public ListNode sortList(ListNode head) {
        //exit of this function
        if(head == null || head.next == null) {
            return head;
        }

        //find mid
        ListNode mid = findMid(head);
        ListNode rightHead = mid.next;
        //cut off from mid
        mid.next = null;

        //divide
        ListNode left = sortList(head);
        ListNode right = sortList(rightHead);
        //conquer
        return merge2Lists(left, right);
    }

    private ListNode findMid(ListNode head){
        ListNode slow = head;
//        ListNode fast = head;
        //思考这里为啥要用head.next而不是head
        ListNode fast = head.next;
        while(fast != null && fast.next != null){
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    private ListNode merge2Lists(ListNode p1, ListNode p2){
        ListNode vh = new ListNode();
        ListNode cur = vh;
        while(p1 != null && p2 != null){
            if(p1.val <= p2.val){
                cur.next = p1;
                p1 = p1.next;
            }
            else{
                cur.next = p2;
                p2 = p2.next;
            }
            cur = cur.next;
        }
        //currently p1 is null or p2 is null
        if(p1 == null){
            cur.next = p2;
        }
        if(p2 == null){
            cur.next = p1;
        }
        return vh.next;
    }
}
