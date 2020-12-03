package jzoffer.dp;

/**
 * 礼物的最大价值
 *
 * @author Sean Yu
 */
public class JzNo47 {
}

/**
 * dp解法（注意，本解法仍然可以优化，可以在grid数组直接修改来节省空间）
 *
 * 1.最后一步
 * 如果不为空，则最优解中必然存在最后的g[i][j]
 * 要到达g[i][j]，要么从上面来，要么从左面来
 * <p>
 * 子问题：求到达g[i-1][j],g[i][j-1]的最优解
 * <p>
 * 2. 确定状态转移方程
 * 令dp(i,j)表示最后到达g[i][j]的最大值，则dp(i,j) = max{dp(i-1,j), dp(i,j-1)}
 * <p>
 * 3. 初始状态 和 边界情况
 * 如果没有左边和上面，则不加
 * <p>
 * 4. 计算顺序
 * 从上到下，从左到右
 * <p>
 * 使用滚动数组节省空间
 */
class SolutionJzNo47 {
    public int maxValue(int[][] grid) {
        if (grid == null || grid.length == 0) {
            return 0;
        }

        int rowNum = grid.length;
        int colnum = grid[0].length;

        int[][] dp = new int[2][colnum];
        int former = 1, curr = 0;

        for (int i = 0; i < rowNum; i++) {
            former = curr;
            curr = 1 - former;
            for (int j = 0; j < colnum; j++) {
                if (i == 0 && j == 0) {
                    dp[curr][j] = grid[i][j];
                    continue;
                }

                int fromUp = Integer.MIN_VALUE;
                int fromLeft = Integer.MIN_VALUE;

                //如果有上面那行
                if (i - 1 >= 0) {
                    fromUp = grid[i][j] + dp[former][j];
                }

                //如果有左边那列
                if (j - 1 >= 0) {
                    fromLeft = grid[i][j] + dp[curr][j - 1];
                }

                dp[curr][j] = Math.max(fromUp, fromLeft);
            }
        }

        return dp[curr][colnum-1];
    }
}
