package algorithms.stackandqueue;

/**
 * @Author: Sean Yu
 * @Date: 2021/3/24 8:17
 */
public class No456 {
    public static void main(String[] args) {
        new Solution456().find132pattern(new int[]{3,1,4,2});
    }
}


class Solution456 {
    public boolean find132pattern(int[] nums) {
        int min, max;
        boolean findMax;
        for(int i = 0 ; i < nums.length ; i++){
            min = nums[i];
            max = min;
            findMax = false;
            for(int j = i +1; j < nums.length; j++){
                if(nums[j] > max){
                    findMax = true;
                    max = nums[j];
                }else if(nums[j] < max){
                    if(findMax && nums[j] > min){
                        return true;
                    }
                }
            }
        }
        return false;
    }
}