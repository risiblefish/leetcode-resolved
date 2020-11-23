package dp;

/**
 * @author Sean Yu
 * @date 2020/11/23 22:25
 */
public class No256 {
}

/**
 * 1. 确定子问题
 * 假设有 n 栋房子，下标0 ~ n-1
 * 看最后一步：
 * 最后那栋房子（第n-1）的颜色，一定是红，蓝，绿 中的一种
 * 如果是红色，需要保证前n-1栋房子的最后一栋不是红色，但是我们并不能保证，万一前n-1栋房子的最优解里，第n-2栋就是红色呢？
 * 对于这种不能确定的情况，可以把条件加到状态里（由求1个解，变成求多个解），即，求前n-1栋房子且最后那栋为红色/蓝色/绿色的最优解，如此一来，求前n栋房子的最优解，就可以根据前面固定的颜色，从容地选取
 * <p>
 * 2. 确定状态
 * 令dp[i][0],dp[i][1],dp[i][2]分别表示前i栋房子里最后那栋房子为红，蓝，绿情况下的最优解
 * 那么有:
 * dp[i][0] = min{dp[i-1][1],dp[i-1][2]} + costs[i-1][0]
 * dp[i][1] = min{dp[i-1][0],dp[i-1][2]} + costs[i-1][1]
 * dp[i][0] = min{dp[i-1][0],dp[i-1][1]} + costs[i-1][2]
 * <p>
 * 3. 确定边界条件和初始状态
 * 初始状态：
 * dp[0][0] = dp[0][1] = dp[0][2] = 0 //前0栋房子，即没有房子的最少价格为0
 * dp[1][0] = costs[0][0]
 * dp[1][1] = costs[0][1]
 * dp[1][2] = costs[0][2]
 * 边界条件：
 * 无
 * <p>
 * 4. 确定计算顺序
 * 由于后面的状态依赖于前面的值，所以选择从小到大计算
 */
class Solution256 {
    public int minCost(int[][] costs) {
        if (costs == null || costs.length == 0) {
            return 0;
        }
        int n = costs.length;
        int[][] dp = new int[n+1][3];

        for (int i = 1; i <= n; i++) {
            dp[i][0] = Math.min(dp[i - 1][1], dp[i - 1][2]) + costs[i - 1][0];
            dp[i][1] = Math.min(dp[i - 1][0], dp[i - 1][2]) + costs[i - 1][1];
            dp[i][2] = Math.min(dp[i - 1][0], dp[i - 1][1]) + costs[i - 1][2];
        }

        int temp = Math.min(dp[n][0], dp[n][1]);
        return Math.min(temp, dp[n][2]);
    }
}
