package algorithms.unionfind;

/**
 * @author Sean Yu
 * @date 12/2/2022 上午11:30
 */
public class No1020 {
    public static void main(String[] args) {
        Solution1020 test = new Solution1020();
        System.out.println(test.numEnclaves(new int[][]{{0, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1},
                {1, 1, 1, 1, 0, 1, 0, 1, 1, 0, 0},
                {0, 1, 0, 1, 1, 0, 0, 0, 0, 1, 0},
                {1, 0, 1, 1, 1, 1, 1, 0, 0, 0, 1},
                {0, 0, 1, 0, 1, 1, 0, 0, 1, 0, 0},
                {1, 0, 0, 1, 1, 1, 0, 0, 0, 1, 1},
                {0, 1, 0, 1, 1, 0, 0, 0, 1, 0, 0},
                {0, 1, 1, 0, 1, 0, 1, 1, 1, 0, 0},
                {1, 1, 0, 1, 1, 1, 0, 0, 0, 0, 0},
                {1, 0, 1, 1, 0, 0, 0, 1, 0, 0, 1}}));
    }
}

/**
 * 思路： 这里没用并查集，用的搜索
 */
class Solution1020 {
    int[][] g;
    int[][] dir = new int[][]{{0,1}, {0,-1}, {1,0}, {-1,0}};
    int m, n;
    public int numEnclaves(int[][] grid) {
        g = grid;
        m = g.length;
        n = g[0].length;
        //第1列和最后1列
        for(int x = 0; x < m ; x++){
            infect(x , 0);
            infect(x , n - 1);
        }
        //第1行和最后1行
        for(int y = 0 ; y < n ; y++){
            infect(0 , y);
            infect(m-1 , y);
        }
        int count = 0;
        for(int x = 0 ; x < m ; x++){
            for(int y = 0; y < n ; y++){
                if(g[x][y] == 1){
                    count++;
                }
            }
        }
        return count;
    }

    private void infect(int x, int y){
        if(x < 0 || x >= m || y < 0 || y >= n){
            return;
        }
        if(g[x][y] != 1){
            return;
        }
        g[x][y] = 2;
        for(int[] d : dir){
            int nx = x + d[0];
            int ny = y + d[1];
            infect(nx, ny);
        }
    }
}