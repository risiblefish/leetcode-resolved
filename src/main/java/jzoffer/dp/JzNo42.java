package jzoffer.dp;

/**
 * @author Sean Yu
 */
public class JzNo42 {
    public static void main(String[] args) {
        System.out.println(new SolutionJzNo42().maxSubArray(new int[]{-2,1,-3,4,-1,2,1,-5,4}));
    }
}

/**
 * 1. 最后1步：
 *
 * 假设n个数，最后一个数是k，前n-1个数的最大连续和为s，
 * 但是我们并不知道前n-1个数中，哪段是连续的，所以这里要变通一下，先求以第i个数结尾的连续最大和，然后在这些最大和里，找出最大的
 *
 * 2. 确定状态转移方程：
 *
 * 假设dp(i)是【包含第i个数】的连续最大和,所以dp(i)至少包含了nums[i]
 * 如果dp[i-1] <= 0, dp[i] = nums[i]
 * 如果dp[i-1] > 0， dp[i] = nums[i] + dp[i-1]
 *
 *
 * 3. 确定初始状态和边界条件
 * dp(0) = nums[0]
 *
 * 4. 确定计算顺序
 * 从小到大能够避免重复计算
 */
class SolutionJzNo42 {
    public int maxSubArray(int[] nums) {
        if(nums == null || nums.length == 0) {
            return 0;
        }
        int n = nums.length;
        int[] dp = new int[n];
        dp[0] = nums[0];
        int temp = dp[0];
        for(int i = 1 ; i < n ; i++) {
            dp[i] = nums[i];
            if(dp[i-1] > 0) {
                dp[i] += dp[i-1];
            }
            temp = Math.max(temp,dp[i]);
        }
        return temp;
    }
}
