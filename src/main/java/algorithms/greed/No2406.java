package algorithms.greed;

import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * 2406.将区间分为最少组数
 * @author Sean Yu
 */
public class No2406 {
}

/**
 * 思路： https://leetcode.cn/problems/divide-intervals-into-minimum-number-of-groups/solution/by-endless_developy-soib/
 */
class Solution2406 {
    public int minGroups(int[][] intervals) {
        Arrays.sort(intervals, (a, b) -> {
            if(a[0] == b[0]) return a[1] - b[1];
            return a[0] - b[0];
        });
        PriorityQueue<Integer> pq = new PriorityQueue();
        pq.add(intervals[0][1]);
        for(int i = 1 ; i < intervals.length ; i++){
            int curLeft = intervals[i][0];
            int curRight = intervals[i][1];
            int minRight = pq.peek();
            if(curLeft > minRight){
                pq.poll();
                pq.add(curRight);
            }
            else{
                pq.add(curRight);
            }
        }
        return pq.size();
    }
}
