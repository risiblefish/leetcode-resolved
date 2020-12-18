package algorithms.dp;

/**
 * @author Sean Yu
 */
public class No746 {
    public static void main(String[] args) {
        System.out.println(new Solution746().minCostClimbingStairs(new int[]{1, 100, 1, 1, 1, 100, 1, 1, 100, 1}));
    }
}

/**
 * 1. 最后一步
 * n = c.length
 * 对于最优解，最后那个阶梯c[i]至多走2步就能出去，即 i + 2 >= n
 * c[i]如果是初始阶梯,即i前面没有阶梯（i <= 1)，则花费就是c[i]
 * 如果c[i]不是初始阶梯，则包含c[i]的总花费 = c[i] + min { 走到i-1的最低花费, 走到i-2的最低花费 }
 * <p>
 * 问题就变成： 求走到i-1和走到i-2的最低花费
 * <p>
 * 2. 确定状态转移方程
 * 若dp[i]表示最后一步是阶梯i的最低花费，则有：
 * algorithms.dp[i] = cost[i] + min {algorithms.dp[i-1],algorithms.dp[i-2]} //如果i-1, i-2存在的话
 * 解就是min{algorithms.dp[n-2],algorithms.dp[n-1]} //因为从最后那个和倒数第2个阶梯都能走出去
 * <p>
 * <p>
 * 3. 确定初始状态
 * algorithms.dp[0] = cost[0]
 * algorithms.dp[1] = cost[1]
 * <p>
 * 4. 确定计算顺序
 * 由小到大
 */
class Solution746 {
    public int minCostClimbingStairs(int[] cost) {
        if (cost == null || cost.length == 0) {
            return 0;
        }
        int n = cost.length;
        if (n == 1) {
            return cost[0];
        }
        int[] dp = new int[n];
        dp[0] = cost[0];
        dp[1] = cost[1];
        for (int i = 2; i < n; i++) {
            dp[i] = cost[i] + Math.min(dp[i - 1], dp[i - 2]);
        }
        return Math.min(dp[n - 2], dp[n - 1]);
    }
}
