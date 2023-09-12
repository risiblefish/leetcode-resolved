package algorithms.ms;

/**
 * @author Sean Yu
 */
public class No17_24 {
}

class Solution17_24 {
    public int[] getMaxMatrix(int[][] matrix) {
        int max = Integer.MIN_VALUE;
        int rowLen = matrix.length;
        int colLen = matrix[0].length;
        int[] ans = new int[]{-1, -1, 200, 200};
        for (int rowUp = 0; rowUp < rowLen; rowUp++) {
            int[] colSum = new int[colLen];
            for (int rowDown = rowUp; rowDown < rowLen; rowDown++) {
                int dp = 0;
                int colStart = 0;
                for (int k = 0; k < colLen; k++) {
                    colSum[k] += matrix[rowDown][k];
                    dp += colSum[k];
                    if (max < dp) {
                        ans[0] = rowUp;
                        ans[1] = colStart;
                        ans[2] = rowDown;
                        ans[3] = k;
                        max = dp;
                    }
                    if (dp < 0) {
                        dp = 0;
                        colStart = k + 1;
                    }
                }
            }
        }
        return ans;
    }
}
