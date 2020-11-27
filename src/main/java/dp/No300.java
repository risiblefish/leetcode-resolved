package dp;

/**
 * @author Sean Yu
 */
public class No300 {
    public static void main(String[] args) {
        System.out.println(new Solution300().lengthOfLIS(new int[]{1,3,6,7,9,4,10,5,6}));
    }
}

/**
 * 1. 确认最后一步
 * 假设数组是a ，令 n = a.length
 * 若最长上升子序列存在，则必然存在最后一个数a[i]
 * 若a[i-1] >= a[i]，则这个最长上升子序列仅有1个元素，即a[i]
 * 若a[i-1] < a[i]，则这个最长上升子序列必然包含a[i-1]
 * 所以子问题是：求包含a[i-1]的最长上升子序列
 * <p>
 * 2.确定状态转移方程
 * 令dp[i]表示下标从0到i且包含a[i]的数组里的最长上升子序列的长度，则有：
 * 对于 k ∈ [0,i),
 * 如果a[k] < a[i]，dp[i] = max { dp[k] + 1 },
 * 如果a[k] >= a[i]，dp[i] = 1
 * <p>
 * 3.确定初始状态和边界条件
 * dp[0] = 1
 * <p>
 * 4.计算顺序
 * 因为dp[i]依赖于dp[i-1]，所以从小到大计的顺序来计算
 */
class Solution300 {
    public int lengthOfLIS(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int n = nums.length;
        int[] dp = new int[n];
        dp[0] = 1;
        int temp = 1;
        for (int i = 1; i < n; i++) {
            dp[i] = 1;
            for (int k = 0; k < i; k++) {
                if (nums[k] < nums[i]) {
                    dp[i] = Math.max(dp[i], dp[k] + 1);
                }
            }
            temp = Math.max(temp,dp[i]);
        }
        return temp;
    }
}