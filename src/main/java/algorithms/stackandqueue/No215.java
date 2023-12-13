package algorithms.stackandqueue;

import java.util.PriorityQueue;

/**
 * No.215 数组中的第K个最大元素
 * @author Sean Yu
 * @create 2022/7/19 20:03
 */
public class No215 {
    public static void main(String[] args) {
        int[] nums = new int[]{3,2,1,5,6,4};
        int k = 2;
        System.out.println(new Solution215().findKthLargest(nums, k));
    }
}

/**
 * 维护一个大小为k的小根堆，堆里分别存放第1大，第2大，,,,，第k大的元素，其中第k大的元素在堆顶
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