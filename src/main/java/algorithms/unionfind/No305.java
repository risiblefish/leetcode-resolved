package algorithms.unionfind;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Sean Yu
 * @date 13/2/2022 下午2:36
 */
public class No305 {
    public static void main(String[] args) {
//        int[][] p = new int[][]{
//                {0,0},
//                {0,1},
//                {1,2},
//                {2,1},
//                {1,0},
//                {0,0},
//                {2,2},
//                {1,2},
//                {1,1},
//                {0,1}
//        };
        int m = 8;
        int n = 2;
        int[][] p = new int[][]{{7,0}};
        System.out.println(new Solution305().numIslands2(m, n, p));
    }
}

/**
 *  思路： 并查集
 */
class Solution305 {
    public List<Integer> numIslands2(int m, int n, int[][] positions) {
        UnionFind uf = new UnionFind(m, n);
        List<Integer> ans = new LinkedList();
        for (int[] pos : positions) {
            ans.add(uf.connect(pos[0], pos[1]));
        }
        return ans;
    }

    class UnionFind {
        int[] parent;
        Deque<Integer> stack;
        int count;
        int row, col, len;
        int[][] dirs = new int[][]{{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
        boolean[] isLand;

        public UnionFind(int r, int c) {
            col = c;
            row = r;
            len = row * col;
            parent = new int[len];
            stack = new ArrayDeque<>();
            count = 0;
            isLand = new boolean[len];
        }

        //做一个2维到1维的映射，这里用x * col + y
        public int index(int x, int y) {
            return x * col + y;
        }

        public void union(int x1, int y1, int x2, int y2) {
            if (x1 < 0 || x1 == row || x2 < 0 || x2 == row || y1 < 0 || y1 == col || y2 < 0 || y2 == col) {
                return;
            }
            int i1 = index(x1, y1);
            int i2 = index(x2, y2);

            //只有都是陆地才进行Union,否则直接返回
            if (!isLand[i1] || !isLand[i2]) {
                return;
            }

            int p1 = find(i1);
            int p2 = find(i2);
            if (p1 != p2) {
                parent[p2] = p1;
                count--;
            }
        }

        //将沿途的节点记录入栈，最后一并更新栈中节点的Parent, 从而将路径压缩
        private int find(int i) {
            //先找到祖先
            while (i != parent[i]) {
                stack.push(i);
                i = parent[i];
            }
            //再将沿途入栈的所有index的祖先更新
            while (!stack.isEmpty()) {
                parent[stack.pop()] = i;
            }
            return i;
        }

        public int connect(int x, int y) {
            int i = index(x, y);//如果该点已经被初始化为岛屿，即重复访问，则直接返回
            if (isLand[i]) {
                return count;
            }
            parent[i] = i;
            isLand[i] = true;
            count++;
            for (int[] d : dirs) {
                int nx = x + d[0];
                int ny = y + d[1];
                union(x, y, nx, ny);
            }
            return count;
        }
    }
}
