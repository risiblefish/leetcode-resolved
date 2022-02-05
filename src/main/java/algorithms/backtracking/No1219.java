package algorithms.backtracking;

/**
 * 1219. 黄金矿工
 * @author Sean Yu
 */
public class No1219 {
}

class Solution1219 {
    int ans = 0;
    int[][] dir = new int[][]{{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
    int m, n;

    public int getMaximumGold(int[][] grid) {
        m = grid.length;
        n = grid[0].length;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] != 0) {
                    dfs(i, j, grid, 0);
                }
            }
        }
        return ans;
    }

    private void dfs(int x, int y, int[][] grid, int sum) {
        if (x < 0 || x >= m || y < 0 || y >= n) {
            return;
        }
        if (grid[x][y] == 0) {
            return;
        }
        int temp = grid[x][y];
        sum += temp;
        ans = Math.max(sum, ans);
        //经过非0的grid[x][y]，暂时置为0
        grid[x][y] = 0;
        for (int[] d : dir) {
            int nx = x + d[0];
            int ny = y + d[1];
            dfs(nx, ny, grid, sum);
        }
        //回溯，将grid[x][y]恢复原有值
        grid[x][y] = temp;
    }
}
