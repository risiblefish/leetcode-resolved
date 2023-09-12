package algorithms.linkedlist;

import java.util.*;

/**
 * 23. 合并K个升序链表
 *
 * @author Sean Yu
 */
public class No23 {

    public static void main(String[] args) {
        /**
         * lists = [[1,4,5],[1,3,4],[2,6]]
         */
        ListNode[] listNodes = generate(Arrays.asList(Arrays.asList(1, 4, 5), Arrays.asList(1, 3, 4), Arrays.asList(2, 6)));
        new Solution23().mergeKLists(listNodes);
    }

    /**
     * 生成测试数据
     *
     * @param list
     * @return
     */
    private static ListNode[] generate(List<List<Integer>> list) {
        int len = list.size();
        ListNode[] arr = new ListNode[len];
        for (int i = 0; i < len; i++) {
            List<Integer> integers = list.get(i);
            ListNode dummyHead = new ListNode(1);
            ListNode curr = dummyHead;
            for (int j = 0; j < integers.size(); j++) {
                curr.next = new ListNode(integers.get(j));
                curr = curr.next;
            }
            arr[i] = dummyHead.next;
        }
        return arr;
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
class Solution23 {
    public ListNode mergeKLists(ListNode[] lists) {
        //这个if如果这里不写，那么65行的if(l > r)就要打开
        if(lists == null || lists.length == 0) return null;
        return devideAndConquer(lists, 0, lists.length - 1);
    }

    private ListNode devideAndConquer(ListNode[] lists, int l, int r){
        if(l == r){
            return lists[l];
        }
        // if(l > r){
        //     return null;
        // }
        int mid = l + (r - l) / 2;
        //先分到不能再分
        ListNode lhead= devideAndConquer(lists, l, mid);
        ListNode rhead = devideAndConquer(lists, mid+1, r);
        //再治
        return mergeTwoOrderedLists(lhead, rhead);
    }

    private ListNode mergeTwoOrderedLists(ListNode h1, ListNode h2){
        ListNode vh = new ListNode();
        ListNode cur = vh;
        while(h1 != null && h2 != null){
            if(h1.val <= h2.val){
                cur.next = h1;
                h1 = h1.next;
            }
            else {
                cur.next = h2;
                h2 = h2.next;
            }
            cur = cur.next;
        }
        //now h1 == null or h2 == null
        if(h1 == null){
            cur.next = h2;
        }else{
            cur.next = h1;
        }
        return vh.next;
    }
}

