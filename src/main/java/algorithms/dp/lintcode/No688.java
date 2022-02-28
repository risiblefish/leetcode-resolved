package algorithms.dp.lintcode;

/**
 * 688. 骑士在棋盘上的概率
 */
public class No688 {
    public static void main(String[] args) {
        System.out.println(new Solution688().knightProbability(3, 2, 0, 0));
    }
}

/**
 * 思路：
 * 先写出暴力递归，然后加缓存
 */
class Solution688 {
    int n;
    double[][][] cache;
    int[][] dir = new int[][]{{-2, 1}, {-1, 2}, {1, 2}, {2, 1}, {2, -1}, {1, -2}, {-1, -2}, {-2, -1}};

    public double knightProbability(int n, int k, int row, int column) {
        this.n = n;
        cache = new double[n][n][k + 1];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                for (int r = 0; r <= k; r++) {
                    cache[i][j][r] = -1;
                }
            }
        }
        return process(row, column, k);
    }

    /**
     * 当前从(x,y)处出发，走完rest步时，在棋盘外的概率
     */
    private double process(int x, int y, int rest) {
        //如果越界，返回0
        if (x < 0 || x >= n || y < 0 || y >= n) {
            return 0;
        }
        //如果命中缓存，直接返回
        if (cache[x][y][rest] != -1) {
            return cache[x][y][rest];
        }
        double res = 0;
        //如果此时没有剩余步数，（因为最开始判断过了越界），则返回1
        if (rest == 0) {
            res = 1;
        }
        //如果还有剩余步数，则朝8个方向继续递归，并将概率汇总
        else {
            for (int[] d : dir) {
                int nx = x + d[0], ny = y + d[1];
                double p = process(nx, ny, rest - 1) / 8;
                res += p;
            }
        }
        //进行缓存
        cache[x][y][rest] = res;
        return res;
    }
}