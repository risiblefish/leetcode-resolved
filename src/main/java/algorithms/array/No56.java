package algorithms.array;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

/**
 * 56. 合并区间
 * @author Sean Yu
 */
public class No56 {
    public static void main(String[] args) {
        int[][] arr = new int[][]{
                {1,3},
                {2,3},
                {2,2},
                {2,2},
                {3,3},
                {4,6},
                {5,7}
        };
        System.out.println(Arrays.deepToString(new Solution56().merge(arr)));
    }
}


/**
 * 思路：
 * 将数组按照左边界排序，如果存在能合并的区间，那么排序后这些区间一定是连续的
 */
class Solution56 {
    public int[][] merge(int[][] intervals) {
        Arrays.sort(intervals, (arr1, arr2) -> {
            return arr1[0] - arr2[0];
        });
        Deque<int[]> q = new ArrayDeque();
        q.offer(intervals[0]);
        for(int i = 1; i < intervals.length ; i++){
            int[] pre = q.peek();
            int[] cur = intervals[i];
            if(pre[1] >= cur[0]){
                int newL = Math.min(pre[0], cur[0]);
                int newR = Math.max(pre[1], cur[1]);
                q.pollFirst();
                q.addFirst(new int[]{newL, newR});
            }else{
                q.addFirst(cur);
            }
        }
        int len = q.size();
        int[][] ans = new int[len][2];
        for(int i = 0 ; i < len ; i++){
            ans[i] = q.poll();
        }
        return ans;
    }
}