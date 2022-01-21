package algorithms.search;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;

/**
 * 1036. 逃离大迷宫
 *
 * @author Sean Yu
 */
public class No1036 {
}

class Solution1036 {
    static Set<String> blockSet;
    static int MAX_COUNT;
    static int[][] direction = new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

    public boolean isEscapePossible(int[][] blocked, int[] source, int[] target) {
        if (blocked.length == 0) {
            return true;
        }
        int n = blocked.length;
        MAX_COUNT = n * (n - 1) / 2;
        blockSet = new HashSet();
        for (int[] i : blocked) {
            blockSet.add(i[0] + "_" + i[1]);
        }
        return bfs(source, target) && bfs(target, source);
    }

    private boolean bfs(int[] a, int[] b) {
        Set<String> visited = new HashSet();
        Deque<int[]> s = new ArrayDeque();
        s.push(a);
        visited.add(a[0] + "_" + a[1]);
        while (!s.isEmpty() && visited.size() <= MAX_COUNT) {
            int[] curr = s.pop();
            int x = curr[0];
            int y = curr[1];
            if (x == b[0] && y == b[1]) {
                return true;
            }
            for (int[] dir : direction) {
                int nx = x + dir[0];
                int ny = y + dir[1];

                if (nx < 0 || nx >= 1e6 || ny < 0 || ny >= 1e6) {
                    continue;
                }
                if (blockSet.contains(nx + "_" + ny)) {
                    continue;
                }
                if (visited.contains(nx + "_" + ny)) {
                    continue;
                }
                s.push(new int[]{nx, ny});
                visited.add(nx + "_" + ny);
            }
        }
        return visited.size() > MAX_COUNT;
    }
}