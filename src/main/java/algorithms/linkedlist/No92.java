package algorithms.linkedlist;

/**
 * 92. 反转链表 II
 */
public class No92 {
}

/**
 *  思路： 头插法
 *  https://leetcode-cn.com/problems/reverse-linked-list-ii/solution/java-shuang-zhi-zhen-tou-cha-fa-by-mu-yi-cheng-zho/
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
class Solution {
    public ListNode reverseBetween(ListNode head, int left, int right) {
        if(left == right){
            return head;
        }
        //虚拟头
        ListNode dummyH = new ListNode(0);
        dummyH.next = head;
        ListNode prev = dummyH;
        ListNode curr = head;

        int i = 1;
        //初始化，找到prev的位置
        while(i < left){
            prev = prev.next;
            curr = curr.next;
            i++;
        }
        //此时curr在left处
        while(i < right){
            //要移动的节点
            ListNode toMove = curr.next;
            //指向下一个要移动的点
            curr.next = toMove.next;

            //移动toMove后，确定它前后的指向
            toMove.next = prev.next;
            prev.next = toMove;
            i++;
        }
        return dummyH.next;
    }
}
