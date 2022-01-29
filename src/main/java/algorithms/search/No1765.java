package algorithms.search;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * 1765. 地图中的最高点
 * @author Sean Yu
 * @date 29/1/2022 上午9:34
 */
public class No1765 {
}


/**
 * 思路： BFS
 */
class Solution1765 {
    public int[][] highestPeak(int[][] isWater) {
        int m = isWater.length, n = isWater[0].length;
        int[][] g = new int[m][n];
        Deque<int[]> q = new ArrayDeque();
        for(int i = 0 ; i < m ; i++){
            for(int j = 0 ; j < n ; j++){
                if(isWater[i][j] == 1){
                    g[i][j] = 0;
                    q.offer(new int[]{i,j});
                }
                else g[i][j] = -1;
            }
        }

        int[][] dir = new int[][]{{0,-1}, {0,1}, {-1,0}, {1,0}};
        while(!q.isEmpty()){
            int[] curr = q.poll();
            int x = curr[0], y = curr[1];
            for(int[] d : dir){
                int nx = x + d[0];
                int ny = y + d[1];
                if(nx >= 0 && nx < m && ny >= 0 && ny < n && g[nx][ny] == -1){
                    g[nx][ny] = g[x][y] + 1;
                    q.offer(new int[]{nx,ny});
                }
            }
        }
        return g;
    }
}
