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
        Arrays.sort(intervals, (in1, in2) -> {
            return in1[0] - in2[0];
        });
        Deque<int[]> stack = new ArrayDeque();
        stack.push(intervals[0]);
        for(int i = 1; i < intervals.length ; i++){
            //pre不用出栈，如果有交集，只需要更新pre的右边界，因为已经按左边界排序，所以pre的左边界一定是最小的
            int[] pre = stack.peek();
            int[] cur = intervals[i];
            //如果pre右边界 >= cur左边界 说明有交集
            if(pre[1] >= cur[0]){
                //
                pre[1] = Math.max(pre[1], cur[1]);
            }
            //否则没有交集，把cur入栈
            else{
                stack.push(cur);
            }
        }
        int len = stack.size();
        int[][] res = new int[len][2];
        //由于栈是后进先出，所以转化为数组的时候，从数组的最后一个下标开始向前更新
        for(int i = len - 1; i >= 0 ; i--){
            int[] cur = stack.pop();
            res[i][0] = cur[0];
            res[i][1] = cur[1];
        }
        return res;
    }
}