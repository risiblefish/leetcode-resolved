package algorithms.linkedlist;

import java.util.Random;

/**
 * 382. 链表随机节点
 * @author Sean Yu
 */
public class No382 {
}

/**
 * 思路： 关键点：题目保证节点数量不超过10000
 */
class Solution382 {

    int[] arr = new int[10000];
    Random rand = new Random();
    int len = 0;

    public Solution382(ListNode head) {
        while(head != null){
            arr[len++] = head.val;
            head = head.next;
        }
    }

    public int getRandom() {
        return arr[rand.nextInt(len)];
    }
}
