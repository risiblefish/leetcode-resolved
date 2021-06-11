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
//        ListNode[] listNodes = generate(Arrays.asList(Arrays.asList(1, 4, 5), Arrays.asList(1, 3, 4), Arrays.asList(2, 6)));
//        ListNode res = new Solution23().mergeKLists(listNodes);
//        System.out.println(res);
        System.out.println(System.currentTimeMillis());
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
 * int val;
 * ListNode next;
 * ListNode() {}
 * ListNode(int val) { this.val = val; }
 * ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class Solution23 {
    public ListNode mergeKLists(ListNode[] lists) {
        ListNode dummyHead = new ListNode(0);
        Map<Integer, List<Integer>> map = new HashMap();
        boolean isEmpty = false;
        ListNode curr = null;
        ListNode insert = dummyHead;
        int min;
        while (!isEmpty) {
            isEmpty = true;
            min = Integer.MAX_VALUE;
            for (int i = 0; i < lists.length; i++) {
                curr = lists[i];
                if (curr == null) {
                    continue;
                } else {
                    isEmpty = false;
                    if (curr.val <= min) {
                        if (map.containsKey(curr.val)) {
                            List<Integer> idxList = map.get(curr.val);
                            idxList.add(i);
                        } else {
                            map.clear();
                            ArrayList<Integer> idxList = new ArrayList<>();
                            idxList.add(i);
                            map.put(curr.val, idxList);
                        }
                        min = curr.val;
                    }
                }
            }
            if (!isEmpty) {
                insert = combine(insert, map.get(min), lists);
                map.clear();
            }
        }
        return dummyHead.next;
    }

    private ListNode combine(ListNode head, List<Integer> idxList, ListNode[] lists) {
        for (Integer index : idxList) {
            ListNode node = lists[index];
            lists[index] = node.next;
            head.next = node;
            head = head.next;
        }
        return head;
    }
}
