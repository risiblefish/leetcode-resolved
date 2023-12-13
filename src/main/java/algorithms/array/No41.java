package algorithms.array;

/**
 * @author Sean Yu
 */
public class No41 {
    public static void main(String[] args) {
        int[] nums = new int[]{1,2,0};
        Solution41 test = new Solution41();
        System.out.println(test.firstMissingPositive(nums));
    }
}

/**
 * 缺失的第一个正整数
 *
 * 思路：
 * 重要结论：对于一个长度为 len 的数组，其中没有出现的最小正整数只能在 [1,len+1] 中
 * 具体操作：
 * 利用数组的下标作为参照，把数组当做哈希表来处理
 * 假如数组nums每个数都"不缺失"，即每个nums[i] 都满足 nums[i] = i + 1 （下标从0开始,值从1开始），那么缺失的就是len + 1
 * 假如出现负数，不连续的情况，如何处理？
 * 先试用预处理，把所有负数变为值为len+1的正数
 *
 */
class Solution41 {
     public int firstMissingPositive(int[] nums) {
         int len = nums.length;
         //step 1 : pre-handle nums
         for(int i = 0 ; i < len;  i++){
             if(nums[i] <= 0){
                 nums[i] = len + 1;
             }
         }
         //step 2 :F
         for(int num : nums){
             int abs = Math.abs(num);
             if(1 <=abs && abs <= len){
                 nums[abs-1] = -Math.abs(nums[abs-1]);
             }
         }
         //step3 :
         for(int i = 0 ; i < len ; i++){
             if(nums[i] > 0) return i+1;
         }
         return len+1;
     }
}


