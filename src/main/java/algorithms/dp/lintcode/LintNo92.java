package algorithms.dp.lintcode;

/**
 * LintCode No.92 背包问题 backpack
 *
 * @author Sean Yu
 * @date 2020/12/23 9:08
 */
public class LintNo92 {
    public static void main(String[] args) {
        System.out.println(new SolutionLintNo92().backPack(10, new int[]{3, 4, 8, 5}));
    }
}

/**
 * 状态转移方程：
 * 令dp[i][k]表示前i个物品能否刚好组成重量k
 * 分2种情况:
 * 如果前i-1个物品就能组成重量K,那么前i个也一定能
 * 如果前i-1个物品能刚好组成重量K-A[i-1],那么前i个物品就能刚好组成 K-A[i-1] + A[i-1] = k
 *
 * 初始条件和边界情况
 * 初始：
 * dp[0][0] = true
 * dp[0][1~m] = false
 * 边界：
 * 前i-1个物品能刚好组成重量K-A[i-1] 的前提是 K - A[i-1] >= 0，否则数组会越界
 */
class SolutionLintNo92 {
    /**
     * @param m: An integer m denotes the size of a backpack
     * @param A: Given n items with size A[i]
     * @return: The maximum size
     */
    public int backPack(int m, int[] A) {
        // write your code here
        if (A == null || A.length == 0) {
            return 0;
        }

        int n = A.length;
        boolean[][] dp = new boolean[n + 1][m + 1];
        dp[0][0] = true;

        for (int i = 1; i <= n; i++) {
            for (int k = 0; k <= m; k++) {
                if (dp[i - 1][k]) {
                    dp[i][k] = true;
                } else {
                    //边界条件
                    if (k - A[i - 1] >= 0) {
                        dp[i][k] = dp[i - 1][k - A[i - 1]];
                    }
                }
            }
        }

        //逆序找到第一个为true的返回
        for (int k = m; k >= 0; k--) {
            if (dp[n][k]) {
                return k;
            }
        }
        return 0;
    }
}