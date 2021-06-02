package algorithms.linkedlist;

/**
 * 206. 反转单链表
 */
public class No206 {
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
class Solution206 {
    /**
     * 思路1 ： 迭代，这里用了头插法。 由于头插法的curr节点可能为空，所以要开局判一下空。
     * @param head
     * @return
     */
    public ListNode reverseList_1(ListNode head) {
        if(head == null) {
            return head;
        }
        ListNode dummyHead = new ListNode(0);
        dummyHead.next = head;
        ListNode curr = head;
        ListNode toMove = null;
        while(curr.next != null){
            toMove = curr.next;
            curr.next = toMove.next;

            toMove.next = dummyHead.next;
            dummyHead.next = toMove;
        }
        return dummyHead.next;
    }

    /**
     * 思路2 ： 递归
     * 如果把链表，理解为 头结点 + 剩余节点， 那么反转1个链表，就是分别反转头节点和剩余节点
     * 而剩余节点，又可以看做是 新的头结点 + 新的剩余节点
     * 由于反转头结点等于只是改变它指针的指向
     */
    public ListNode reverseList(ListNode head) {
        if(head == null || head.next == null){
            return head;
        }
        ListNode h = reverseList(head.next);
        head.next.next = head;
        head.next = null;
        return h;
    }
}
