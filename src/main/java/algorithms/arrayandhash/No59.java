package algorithms.arrayandhash;

/**
 * @author Sean Yu
 */
public class No59 {
    public static void main(String[] args) {
        new Solution59().generateMatrix(3);
    }
}

class Solution59 {
    public int[][] generateMatrix(int n) {
        int[][] matrix = new int[n][n];
        int currDir = 0;
        int currCount = 1;
        int totoalCount = n * n;
        int dirCount = 0;
        int r = 0, c = 0;
        while (currCount <= totoalCount) {
            if (currDir == 0) {
                if (c + 1 == n || ((c + 1 < n) && matrix[r][c + 1] != 0)) {
                    currDir = 1;
                    dirCount++;
                } else {
                    dirCount = 0;
                    matrix[r][c] = currCount++;
                    c++;
                }
            } else if (currDir == 1) {
                if (r + 1 == n || ((r + 1 < n) && matrix[r + 1][c] != 0)) {
                    currDir = 2;
                    dirCount++;
                } else {
                    dirCount = 0;
                    matrix[r][c] = currCount++;
                    r++;
                }
            } else if (currDir == 2) {
                if (c - 1 < 0 || ((c >= 0) && matrix[r][c - 1] != 0)) {
                    currDir = 3;
                    dirCount++;
                } else {
                    dirCount = 0;
                    matrix[r][c] = currCount++;
                    c--;
                }
            } else {
                if (r - 1 < 0 || (matrix[r - 1][c] != 0)) {
                    currDir = 0;
                    dirCount++;
                } else {
                    dirCount = 0;
                    matrix[r][c] = currCount++;
                    r--;
                }
            }
            if (dirCount == 3) {
                matrix[r][c] = currCount++;
            }
        }
        return matrix;
    }
}
