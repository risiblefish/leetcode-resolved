package dp;

/**
 * @author Sean Yu
 * @date 2020/11/22 22:02
 */
public class No62 {
    public static void main(String[] args) {
        System.out.println(new Solution62().uniquePaths(3, 7));
    }
}

/**
 * m * n 的网格
 * <p>
 * 1. 最后一步
 * 不管哪种方式，最后到了右下角，它要么是从上面来的（如果上面存在），要么是从左边来的（如果左边存在）
 * <p>
 * 2. 确定状态转移方程
 * 如果用dp(m,n)代表所有到达右下角的路径数量，那么
 * 走到右下角上方的路径数量就是dp(m-1,n)
 * 走到右下角左方的路径数量就是dp(m,n-1)
 * <p>
 * 所以dp(m,n) = dp(m-1,n) + dp(m,n-1)
 * <p>
 * 3. 确定初始条件和边界情况
 * dp(1,y) = 1 和 dp(x,1) = 1
 * 上方不存在： 横坐标 < 0
 * 左方不存在： 纵坐标 < 0
 * m,n均要大于0
 * <p>
 * 4. 确定计算顺序
 * 按行从上到下 + 按列从左到右 （先遍历行 或者 先遍历列 均可）
 */
class Solution62 {
    public int uniquePaths(int m, int n) {
        if (m <= 0 || n <= 0) {
            return 0;
        }
        int[][] dp = new int[m + 1][n + 1];
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (i == 1 || j == 1) {
                    dp[i][j] = 1;
                } else {
                    dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
                }
            }
        }
        return dp[m][n];
    }
}
