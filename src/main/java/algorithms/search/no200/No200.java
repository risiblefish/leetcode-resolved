package algorithms.search.no200;

/**
 * 200. 岛屿数量
 * @author Sean Yu
 * @since 2024/5/22 13:53
 */

public class No200 {
}

class Solution200 {
    boolean[][] visited;

    public int numIslands(char[][] grid) {
        visited = new boolean[grid.length][grid[0].length];
        int ans = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == '1' && !visited[i][j]) {
                    ans++;
                    search(grid, i, j);
                }
            }
        }
        return ans;
    }

    private void search(char[][] grid, int i, int j) {
        if (i < 0 || j < 0 || i == grid.length || j == grid[0].length) return;
        if (grid[i][j] == '0') return;
        //now grid[i][j] == 1
        if (visited[i][j]) return;
        visited[i][j] = true;
        search(grid, i - 1, j);
        search(grid, i + 1, j);
        search(grid, i, j - 1);
        search(grid, i, j + 1);
    }
}
