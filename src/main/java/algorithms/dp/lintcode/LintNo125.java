package algorithms.dp.lintcode;

/**
 * 125. 背包问题II
 *
 * @author Sean Yu
 */
public class LintNo125 {
}


/**
 * n个物体，每个物体有对应的重量和价值，在背包重量范围内 找出能组合出的最高价值
 * 思路：
 * 将重量计入状态，作为条件，即重量为w时，最大的价值
 * <p>
 * 确定状态转移方程：
 * 令dp[i][w]表示前i(0~i-1)个物品凑出重量刚好为w的最大价值，那么有2种情况
 * （1）第i-1个物品不放进背包, 最大价值为dp[i-1][w]
 * （2）第i-1个物体放进背包，即前i-1个物品只需凑出重量w-A[i-1]，最大价值为dp[i-1][w-A[i-1]] + V[i-1]
 * dp[i][w]就是上述2种情况种的更大者
 * dp[i][w] = max{ dp[i-1][w] , dp[i-1][w-A[i-1]] + v[i-1] }
 * 最后要求的是dp[n][m]
 * <p>
 * 确定初始条件和边界情况
 * dp[0][w] = 0
 */
class SolutionLintNo125 {
    /**
     * @param m: An integer m denotes the size of a backpack
     * @param A: Given n items with size A[i]
     * @param V: Given n items with value V[i]
     * @return: The maximum value
     */
    public int backPackII(int m, int[] A, int[] V) {
        int n = A.length;
        int[][] dp = new int[n + 1][m + 1];
        for (int w = 0; w <= m; w++) {
            dp[0][w] = 0;
        }
        for (int i = 1; i <= n; i++) {
            for (int w = 0; w <= m; w++) {
                dp[i][w] = dp[i - 1][w];
                if (w - A[i - 1] >= 0) {
                    dp[i][w] = Math.max(dp[i - 1][w], dp[i - 1][w - A[i - 1]] + V[i - 1]);

                }
            }
        }
        return dp[n][m];
    }
}