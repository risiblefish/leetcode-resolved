package algorithms.dp;

/**
 * 304. 二维区域和检索 - 矩阵不可变
 *
 * @author Sean Yu
 */
public class No304 {
}

/**
 * 思路1：一维前缀和
 * 对于每一行，记录从第0列到第i列的总和。
 * <p>
 * 时间复杂度O（N）
 */
class NumMatrix {
    private final int[][] rowSum;

    public NumMatrix(int[][] matrix) {
        if (matrix == null || matrix.length == 0) {
            rowSum = new int[0][0];
        } else {
            int rowLen = matrix.length;
            int colLen = matrix[0].length;
            rowSum = new int[rowLen][colLen];
            //initialize rowSum
            for (int i = 0; i < rowLen; i++) {
                for (int j = 0; j < colLen; j++) {
                    if (j == 0) {
                        rowSum[i][j] = matrix[i][j];
                    }
                    //j > 0
                    else {
                        rowSum[i][j] = rowSum[i][j - 1] + matrix[i][j];
                    }
                }
            }
        }
    }

    public int sumRegion(int row1, int col1, int row2, int col2) {
        int sum1 = 0, sum2 = 0;
        for (int i = row1; i <= row2; i++) {
            sum1 += rowSum[i][col2];
            if (col1 - 1 >= 0) {
                sum2 += rowSum[i][col1 - 1];
            }
        }
        return sum1 - sum2;
    }
}

/**
 * Your NumMatrix object will be instantiated and called as such:
 * NumMatrix obj = new NumMatrix(matrix);
 * int param_1 = obj.sumRegion(row1,col1,row2,col2);
 */


/**
 * 思路2： 二维前缀和
 * 用sum(i,j)记录从0，0到点i,j的和
 * 时间复杂度：O（1）
 */
class NumMatrixII {
    private final int[][] sum;

    public NumMatrixII(int[][] matrix) {
        if (matrix == null || matrix.length == 0) {
            sum = new int[0][0];
        } else {
            int rowLen = matrix.length;
            int colLen = matrix[0].length;
            sum = new int[rowLen][colLen];
            sum[0][0] = matrix[0][0];
            //初始化第1列的和
            for (int i = 1; i < rowLen; i++) {
                sum[i][0] = sum[i - 1][0] + matrix[i][0];
            }
            //初始化第一行的和
            for (int j = 1; j < colLen; j++) {
                sum[0][j] = sum[0][j - 1] + matrix[0][j];
            }
            //初始化非第一行和第一列的和
            for (int i = 1; i < rowLen; i++) {
                for (int j = 1; j < colLen; j++) {
                    sum[i][j] = sum[i - 1][j] + sum[i][j - 1] - sum[i - 1][j - 1] + matrix[i][j];
                }
            }
        }
    }

    //画图可知，左上角row1,col1到右下角row2,col2的矩阵和为： sum[row2][col2] - sum[row2][col1-1] - sum[row1-1][col2] + sum[row1-1][col1-1]
    //其中，需要对数组越界做检查
    public int sumRegion(int row1, int col1, int row2, int col2) {
        int res = sum[row2][col2];
        if (col1 - 1 >= 0) {
            res -= sum[row2][col1 - 1];
        }
        if (row1 - 1 >= 0) {
            res -= sum[row1 - 1][col2];
        }
        if (row1 - 1 >= 0 && col1 - 1 >= 0) {
            res += sum[row1 - 1][col1 - 1];
        }
        return res;
    }
}

/**
 * Your NumMatrix object will be instantiated and called as such:
 * NumMatrix obj = new NumMatrix(matrix);
 * int param_1 = obj.sumRegion(row1,col1,row2,col2);
 */
