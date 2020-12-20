package algorithms.dp;

/**
 * 279. 完全平方数
 *
 * @author Sean Yu
 * @date 2020/12/20 11:47
 */
public class No279 {
    public static void main(String[] args) {
        System.out.println(new Solution279().numSquares(12));
    }
}

/**
 * 1.分析最后一步
 * <p>
 * 假设在完全平方数最少的策略中，最后一个完全平方数是k（1 <= j <= 根号n）
 * 那么一定有： 构成n-j的完全平方数也是最少的
 * <p>
 * 2. 确定状态
 * 令构成i的完全平方数的最少个数是dp(i)
 * dp(i) = min{ dp(i-j) } + 1
 * 其中j = k^2,  k满足 1 <= k && k^2 <= i
 * <p>
 * 3. 确定初始条件和边界情况
 * 因为题目保证n为正整数，所以dp(0) = 0
 */
class Solution279 {
    public int numSquares(int n) {
        int[] dp = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            //初始化，i至少可以由i个1组成
            dp[i] = i;
            for (int k = 1; k * k <= i; k++) {
                dp[i] = Math.min(dp[i - k * k] + 1, dp[i]);
            }
        }
        return dp[n];
    }
}