package algorithms.stackandqueue;

import java.util.PriorityQueue;

/**
 * No.215 数组中的第K个最大元素
 * @author Sean Yu
 * @create 2022/7/19 20:03
 */
public class No215 {
    public static void main(String[] args) {
//        int[] nums = new int[]{3,2,1,5,6,4};
//        int k = 2;
//        System.out.println(new Solution215().findKthLargest(nums, k));
        PriorityQueue<Integer> q = new PriorityQueue<>();
        q.add(2);
        q.add(1);
        System.out.println(q.peek());
    }
}

/**
 * 如果是求最大的元素，只需要不断比较，只保留当前最大的元素，直到遍历结束
 * 如果是求第2大的元素，需要维护1个队列，不断比较，存放当前最大的2个值，直到遍历结束
 * 同理，如果是求第k大的元素，需要维护一个队列，不断比较，存放当前最大的k个值，直到遍历结束
 * 其中，不需要一一比较，如果这个队列是自动有序的，那么只需拿队列中最小的值出来和当前的值比较，将较小的那个淘汰掉，然后让队列有序即可
 *
 * 具体做法：
 * 维护一个大小为k的小根堆，堆里分别存放 最大，第2大，,,,，第k大的元素，共k个元素，其中第k大的元素在堆顶
 * 以优先队列为例，容量初始化为k，默认为升序排列，从队列头部到尾部，分别存放第k大，第k-1大，第k-2大，...， 第2大，最大 的元素
 */
class Solution215 {
    public int findKthLargest(int[] nums, int k) {
        PriorityQueue<Integer> q = new PriorityQueue<>();
        for(int i = 0 ; i < k ; i++){
            q.add(nums[i]);
        }
        for(int i = k ; i < nums.length ; i++){
            int topVal = q.peek();
            if(nums[i] > topVal){
                q.poll();
                q.add(nums[i]);
            }
        }
        return q.peek();
    }
}