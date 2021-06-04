package algorithms.linkedlist;

import java.util.HashSet;
import java.util.Set;

/**
 * 142. 环形链表 II
 */
public class No142 {
    public static void main(String[] args) {
        ListNode n3 = new ListNode(3);
        ListNode n2 = new ListNode(2);
        ListNode n0 = new ListNode(0);
        ListNode n4 = new ListNode(4);
        n3.next = n2;
        n2.next = n0;
        n0.next = n4;
        n4.next = n2;
        ListNode res = new Solution142().detectCycle_2(n3);
        System.out.println(res.val);
    }
}

class Solution142 {
    /**
     * 思路1 ： 利用SET
     *
     * @param head
     * @return
     */
    public ListNode detectCycle_1(ListNode head) {
        Set<ListNode> set = new HashSet();
        while (head != null) {
            if (set.contains(head)) {
                return head;
            }
            set.add(head);
            head = head.next;
        }
        return null;
    }

    /**
     * 思路2 ： 利用数学
     * 初始时，fast和slow同时出发，其中，fast的速度是slow的2倍
     * 如果有环，假设fast和slow在meet点相遇
     * 假设环入口在A点，令DA为head到A的举例， 令DB为A到meet的距离，令DC为meet到A的距离
     * 则相遇时，fast走过的路程为DA+DB+DC+DB, slow走过的路程为DA+DB,
     * 因为fast速度为slow的2倍，所以DA+DB+DC+DB = 2*(DA+DB) ,解得 DA = DC
     * 如果令slow从meet处出发，令p从head处同速出发，两者相遇时，slow刚好走过DC,p刚好走过DA，即它们走过相等的距离，相遇点必然在环的入口A处
     *
     * @param head
     * @return
     */
    public ListNode detectCycle_2(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;
        while (fast != null) {
            fast = fast.next;
            if (fast == null) {
                return null;
            }
            fast = fast.next;
            slow = slow.next;
            if (slow == fast) {
                ListNode p = head;
                while (p != slow) {
                    slow = slow.next;
                    p = p.next;
                }
                return slow;
            }
        }
        return null;
    }
}