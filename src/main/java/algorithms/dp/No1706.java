package algorithms.dp;

/**
 * 1706. 球会落何处
 */
public class No1706 {
}

/**
 * 思路： 根据题意模拟，写出递归，然后将重复点进行缓存
 */
class Solution1706 {
    int[][] g;
    int row, col;
    int[][] cache;

    public int[] findBall(int[][] grid) {
        g = grid;
        row = g.length;
        col = g[0].length;
        cache = new int[row][col];
        for (int r = 0; r < row; r++) {
            for (int c = 0; c < col; c++) {
                cache[r][c] = -2;
            }
        }
        int[] res = new int[col];
        for (int y = 0; y < col; y++) {
            res[y] = process(0, y);
        }
        return res;
    }

    private int process(int x, int y) {
        if (x < 0 || x >= row || y < 0 || y >= col) {
            return -1;
        }
        if (cache[x][y] != -2) {
            return cache[x][y];
        }
        int res = -1;
        //如果当前格子向左引导
        if (g[x][y] == -1) {
            //仅当它左边存在格子，且左边格子也向左，球才能滑到左下处
            if (y - 1 >= 0 && g[x][y - 1] == -1) {
                //如果当前已经到底部，则球滑向左边一格停住
                if (x == row - 1) {
                    res = y - 1;
                }
                //否则如果当前不是底部，则球滑向左下
                else {
                    res = process(x + 1, y - 1);
                }
            }
        }
        //如果当前格子向右引导
        else {
            //仅当它右边存在格子，且右边格子也向右，球才能滑到右下处
            if (y + 1 < col && g[x][y + 1] == 1) {
                //如果当前已到底部，则球滑向右边一格
                if (x == row - 1) {
                    res = y + 1;
                }
                //否则如果当前不是底部，则球滑向右下
                else {
                    res = process(x + 1, y + 1);
                }
            }
        }
        cache[x][y] = res;
        return res;
    }
}
