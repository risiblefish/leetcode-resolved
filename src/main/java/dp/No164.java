package dp;

import java.util.Arrays;

/**
 * @author Sean Yu
 */
public class No164 {
    public static void main(String[] args) {
        System.out.println(new Solution164().maximumGap(new int[]{3, 6, 9, 1}));
    }
}


/**
 * 常规解法：
 * 排序后逐个比较，记录当前最大差值
 * dp[i]表示长度为i（i > 1）的数组里找出的最大值
 * 那么有dp[i] = max( dp[i-1], nums[i-1] - nums[i-2] )
 */
class Solution164 {
    public int maximumGap(int[] nums) {
        if(nums == null || nums.length < 2) {
            return 0;
        }
        int n = nums.length;
        int[] temp = new int[n];
        for (int i = 0; i < nums.length; i++) {
            temp[i] = nums[i];
        }

        Arrays.sort(temp);
        int[] dp = new int[n+1];

        for(int i = 2 ; i <= n ; i++) {
            int dif = temp[i-1] - temp[i-2];
            dp[i] = Math.max(dif, dp[i - 1]);
        }

        return dp[n];
    }
}