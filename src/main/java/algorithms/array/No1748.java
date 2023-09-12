package algorithms.array;

import java.util.Arrays;

/**
 * 1748. 唯一元素的和
 * @author Sean Yu
 */
public class No1748 {
    public static void main(String[] args) {
    }
}

class Solution1748 {
    public int sumOfUnique(int[] nums) {
        Arrays.sort(nums);
        int sum = 0;
        for(int i = 0 ; i < nums.length ; i++){
            boolean flag = true;
            if(i - 1 >= 0){
                if(nums[i] == nums[i-1]){
                    flag = false;
                }
            }
            if(i + 1 < nums.length){
                if(nums[i] == nums[i+1]){
                    flag = false;
                }
            }
            if(flag){
                sum += nums[i];
            }
        }
        return sum;
    }
}
