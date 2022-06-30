package algorithms.dp;

import java.util.Arrays;

/**
 * 198. 打家劫舍
 * @author Sean Yu
 * @create 2022/6/30 08:09
 */
public class No198 {
}

class Solution198_I {
    int[][] cache;
    int n;
    int[] arr;
    public int rob(int[] nums) {
        n = nums.length;
        arr = nums;
        cache = new int[2][n];
        for(int i = 0 ; i < 2 ; i++){
            Arrays.fill(cache[i], -1);
        }
        return rob(0,0);
    }

    private int rob(int pre, int i){
        if(cache[pre][i] != -1){
            return cache[pre][i];
        }
        if(i == n - 1){
            return pre == 0 ? arr[i]: 0;
        }
        //可能性1： 当前家不抢
        int p1 = rob(0, i+1);
        if(pre == 0){
            //可能性2： 当前家一定抢
            int p2 = rob(1, i+1) + arr[i];
            p1 = Math.max(p1, p2);
        }
        cache[pre][i] = p1;
        return p1;
    }
}

class Solution198_II {
    public int rob(int[] nums) {
        int n = nums.length;
        int[][] dp = new int[2][n];
        dp[0][0] = 0;
        dp[1][0] = nums[0];
        for(int i = 1 ; i < n ; i++){
            //如果当前家不抢，那么最大值就是前一家的Max,而不是前一家必抢
            dp[0][i] = Math.max(dp[0][i-1],dp[1][i-1]);
            dp[1][i] = dp[0][i-1] + nums[i];
        }
        return Math.max(dp[0][n-1],dp[1][n-1]);
    }
}