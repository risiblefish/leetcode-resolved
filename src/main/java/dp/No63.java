package dp;

/**
 * @author Sean Yu
 * @date 2020/11/23 14:08
 */
public class No63 {

}

/**
 * 1. 确定子问题
 * 如果能走到右下角，那么必定是从上或者从左过来
 * <p>
 * 2.确定状态
 * 令m=行数 n=列数
 * 如果dp(m-1,n-1)走得通，则数量 = dp(m-1,n) + dp(m,n-1) //前提是上面和左面存在，否则为0
 * 如果dp(m-1,n-1)走不通，则数量 = 0
 * <p>
 * 3.确定初始条件和边界情况
 * dp(0,0) 或者 dp(m-1)(n-1)如果通则为1，否则直接返回0
 * 如果上面或左边不存在，则为0
 * <p>
 * 4.计算顺序
 * 按 行-列 或者 列-行 从小到大 均可
 */
class Solution63 {
    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        if (obstacleGrid == null || obstacleGrid.length == 0) {
            return 0;
        }

        int rowNum = obstacleGrid.length;
        int colNum = obstacleGrid[0].length;

        int[][] dp = new int[rowNum][colNum];
        if (obstacleGrid[0][0] == 1 || obstacleGrid[rowNum - 1][colNum - 1] == 1) {
            return 0;
        }
        //这里选择先 从上到下，再从左到右的顺序
        for (int i = 0; i < rowNum; i++) {
            for (int j = 0; j < colNum; j++) {
                if (obstacleGrid[i][j] == 1) {
                    dp[i][j] = 0;
                } else {
                    if (i == 0 && j == 0) {
                        dp[i][j] = 1;
                    } else {
                        dp[i][j] = 0;
                        //如果不是第1行，则加上上方的结果
                        if (i > 0) {
                            dp[i][j] += dp[i - 1][j];
                        }
                        //如果不是第1列，则加上左方的结果
                        if (j > 0) {
                            dp[i][j] += dp[i][j - 1];
                        }
                    }
                }
            }
        }
        return dp[rowNum - 1][colNum - 1];
    }
}
