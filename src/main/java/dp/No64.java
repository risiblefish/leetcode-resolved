package dp;

/**
 * @author Sean Yu
 * @date 2020/11/28 21:13
 */
public class No64 {
    public static void main(String[] args) {
        System.out.println(new Solution64_RollingArray().minPathSum(new int[][]{{1, 3, 1}, {1, 5, 1}, {4, 2, 1}}));
        System.out.println(new Solution64().minPathSum(new int[][]{{1, 3, 1}, {1, 5, 1}, {4, 2, 1}}));
    }
}


/**
 * 最小路径和
 * <p>
 * 1. 确定最后一步
 * 假设最小路径存在，那么要走到最后一格，必然是从上或者从左过来,如果要和最小，那么必然有上方的和或者左边的和最小
 * <p>
 * 2. 确定状态转移方程
 * 令dp(i,j)表示走到点[i][j]的矩阵右下方的最短路径和，那么有dp(i,j) = g[i-1][j-1] + min{ dp(i-1,j), dp(i,j-1) } //前提是上方，左方存在的情况下
 * <p>
 * 3. 确定初始状态和边界情况
 * dp(0,0) = grid[0][0]
 * <p>
 * 4. 计算顺序
 * 当前结果依赖前面的计算结果，所以从小到大计算并保留计算结果，避免重复计算
 */
class Solution64 {
    public int minPathSum(int[][] grid) {
        if (grid == null || grid.length == 0) {
            return 0;
        }
        int rowNum = grid.length;
        int colNum = grid[0].length;

        int[][] dp = new int[rowNum][colNum];
        //初始化初始状态
        dp[0][0] = grid[0][0];
        for (int i = 0; i < rowNum; i++) {
            for (int j = 0; j < colNum; j++) {
                if (!(i == 0 && j == 0)) {
                    int fromLeft = Integer.MAX_VALUE;
                    int fromUp = Integer.MAX_VALUE;
                    //如果上方存在
                    if (i - 1 >= 0) {
                        fromUp = dp[i - 1][j];
                    }
                    //如果左方存在
                    if (j - 1 >= 0) {
                        fromLeft = dp[i][j - 1];
                    }
                    dp[i][j] = grid[i][j] + Math.min(fromLeft, fromUp);
                }
            }
        }
        return dp[rowNum - 1][colNum - 1];
    }
}

/**
 * 利用滚动数组节省空间
 * 在前面解法的基础上进行分析，我们是先逐列遍历，再逐行遍历
 * 我们发现，对于每个点，计算它的值只需要用到当前行和上一行，所以我们数组只用开2行
 */
class Solution64_RollingArray {
    public int minPathSum(int[][] grid) {
        if (grid == null || grid.length == 0) {
            return 0;
        }
        int rowNum = grid.length;
        int colNum = grid[0].length;

        int[][] dp = new int[2][colNum];

        //用former表示上一行，curr表示当前行,因为在遍历行的时候2者要交换，所以先倒置
        int former = 1, curr = 0;
        for (int i = 0; i < rowNum; i++) {
            former = curr;
            curr = 1 - former;
            for (int j = 0; j < colNum; j++) {
                //把初始化状态放到循环里来做，而不是dp[0][0] = grid[0][0]
                if(i == 0 && j == 0){
                    dp[curr][0] = grid[0][0];
                }
                else {
                    int fromLeft = Integer.MAX_VALUE;
                    int fromUp = Integer.MAX_VALUE;
                    //如果上方存在
                    if (i - 1 >= 0) {
                        fromUp = dp[former][j];
                    }
                    //如果左方存在
                    if (j - 1 >= 0) {
                        fromLeft = dp[curr][j - 1];
                    }
                    dp[curr][j] = grid[i][j] + Math.min(fromLeft, fromUp);
                }
            }
        }
        return dp[curr][colNum - 1];
    }
}
