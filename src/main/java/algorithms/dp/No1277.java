package algorithms.dp;

/**
 * 1277. 统计全为 1 的正方形子矩阵
 * @author Sean Yu
 */
public class No1277 {
    public static void main(String[] args) {
        System.out.println(new Solution1277().countSquares(new int[][]{{0, 1, 1, 1}, {1, 1, 1, 1}, {0, 1, 1, 1}}));
    }
}

/**
 * 思路:
 *
 * 二维dp，记录以i,j为右下角的能组成的最大正方形的边长。与221题类似。
 */
class Solution1277 {
    public int countSquares(int[][] matrix) {
        //题目保证矩阵非空
        int rowLen = matrix.length;
        int colLen = matrix[0].length;
        int[][] dp = new int[rowLen][colLen];
        int count = 0;

        for (int r = 0; r < rowLen; r++) {
            for (int c = 0; c < colLen; c++) {
                if (r == 0 || c == 0) {
                    dp[r][c] = matrix[r][c];
                } else {
                    if (matrix[r][c] == 1) {
                        int currLen = Math.min(Math.min(dp[r - 1][c], dp[r][c - 1]), dp[r - 1][c - 1]) + 1;
                        dp[r][c] = currLen;
                    }
                }
                count += dp[r][c];
            }
        }
        return count;
    }
}
