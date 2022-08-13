package algorithms.linkedlist;

/**
 * 160. 相交链表
 * @author Sean Yu
 */
public class No160 {
    public static void main(String[] args) {
        ListNode headA = new ListNode(4);
        headA.next = new ListNode(1);

        ListNode intersectNode = new ListNode(8);
        intersectNode.next = new ListNode(4);
        intersectNode.next.next = new ListNode(5);
        headA.next.next = intersectNode;

        ListNode headB = new ListNode(5);
        headB.next = new ListNode(6);
        headB.next.next = new ListNode(1);
        headB.next.next.next = intersectNode;

        ListNode h1 = new ListNode(1);
        h1.next = new ListNode(2);

        ListNode h2 = new ListNode(3);


        System.out.println(new Solution160().getIntersectionNode(h1, h2));
    }
}

/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) {
 *         val = x;
 *         next = null;
 *     }
 * }
 */

/**
 * 思路：
 * 假设A,B而分别为2个头节点，2条链表长度分别为a,b
 *
 * 同时从A,B向后遍历，当自己的路走完时，再走一次对方的路：
 * 那么A走的路程是 a + b， 而b走的路程是 b + a， 也就是如果它们不相交，那么当走完（a+b）路程后，一定同时指向null
 *
 * 如果它们在C处相交，在D处结尾
 * 同样地先走自己的路，再走对方的路，
 * 那么它们相交的时候， A走的路程是 AC + CD + BC， B走的路程是 BC + CD + AC， 可见路程是相等的，所以按上述方法，它们一定能在交点处相遇
 */
class Solution160 {
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if (headA == null || headB == null) {
            return null;
        }
        ListNode pA = headA, pB = headB;
        while (pA != pB) {
            pA = pA == null ? headB : pA.next;
            pB = pB == null ? headA : pB.next;
        }
        return pA;
    }
}
