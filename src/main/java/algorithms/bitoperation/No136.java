package algorithms.bitoperation;

/**
 * 136. 只出现一次的数字
 * @author Sean Yu
 */
public class No136 {
}

/**
 * 利用异或： 同一个数与自身异或为0的性质
 */
class Solution136 {
    public int singleNumber(int[] nums) {
        int res = nums[0];
        for(int i = 1; i < nums.length;  i++){
            res ^= nums[i];
        }
        return res;
    }
}
