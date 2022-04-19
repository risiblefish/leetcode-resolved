package algorithms.segmenttree;

import java.util.*;

/**
 * No.699 掉落的方块
 * @author Sean Yu
 * @create 2022/4/18 13:37
 */
public class No699 {
    public static void main(String[] args) {
        int[][] positions = new int[][]{{9, 7}, {1, 9}, {3, 1}};
//        int[][] positions = new int[][]{{2, 2}, {2, 2}, {2, 2}};
        System.out.println(new Solution699().fallingSquares(positions));
    }
}


class Solution699 {
    public List<Integer> fallingSquares(int[][] positions) {
        Map<Integer, Integer> map = index(positions);
        SegmentTree seg = new SegmentTree(map.size());
        int max = 0;
        List<Integer> res = new ArrayList();
        for (int[] pos : positions) {
            int missionL = map.get(pos[0]);
            int missionR = map.get(pos[0] + pos[1] - 1);
            int height = seg.query(missionL, missionR) + pos[1];
            max = Math.max(height, max);
            res.add(max);
            seg.update(missionL, missionR, height);
        }
        return res;
    }

    /**
     * 坐标压缩，将离散化的坐标升序排列后记录
     * @param positions
     * @return
     */
    private Map<Integer, Integer> index(int[][] positions) {
        Map<Integer, Integer> map = new HashMap();
        TreeSet<Integer> set = new TreeSet();
        for (int[] pos : positions) {
            set.add(pos[0]);
            set.add(pos[0] + pos[1] - 1);
        }
        int cnt = 0;
        for (Integer i : set) {
            map.put(i, ++cnt);
        }
        return map;
    }

    class SegmentTree {
        int len;
        int[] max, change;
        boolean[] update;

        public SegmentTree(int size) {
            len = size + 1;
            max = new int[len * 4];
            change = new int[len * 4];
            update = new boolean[len * 4];
        }

        public void update(int missionL, int missionR, int height) {
            update(missionL, missionR, height, 1, len - 1, 1);
        }

        private void update
                (int missionL, int missionR, int height, int rootL, int rootR, int rootIdx) {
            if (missionL <= rootL && rootR <= missionR) {
                update[rootIdx] = true;
                change[rootIdx] = height;
                max[rootIdx] = height;
            } else {
                int mid = rootL + (rootR - rootL) / 2;
                spreadLazy(rootIdx);

                if (missionL <= mid) {
                    update(missionL, missionR, height, rootL, mid, rootIdx * 2);
                }

                if (missionR > mid) {
                    update(missionL, missionR, height, mid + 1, rootR, rootIdx * 2 + 1);
                }

                max[rootIdx] = Math.max(max[rootIdx * 2], max[rootIdx * 2 + 1]);
            }
        }

        private void spreadLazy(int rootIdx) {
            if(update[rootIdx]){
                update[rootIdx] = false;
                update[rootIdx * 2] = true;
                update[rootIdx * 2 + 1] = true;
                change[rootIdx * 2] = change[rootIdx];
                change[rootIdx * 2 + 1] = change[rootIdx];
                max[rootIdx * 2] = change[rootIdx];
                max[rootIdx * 2 + 1] = change[rootIdx];
            }
        }

        public int query(int missionL, int missionR) {
            return query(missionL, missionR, 1, len - 1, 1);
        }

        private int query(int missionL, int missionR, int rootL, int rootR, int rootIdx) {
            if (missionL <= rootL && missionR >= rootR) {
                return max[rootIdx];
            } else {
                int mid = rootL + (rootR - rootL) / 2;
                spreadLazy(rootIdx);
                int res = 0;
                if (missionL <= mid) {
                    res = Math.max(res, query(missionL, missionR, rootL, mid, rootIdx * 2));
                }
                if (missionR > mid) {
                    res = Math.max(res, query(missionL, missionR, mid + 1, rootR, rootIdx * 2 + 1));
                }
                return res;
            }
        }
    }
}
