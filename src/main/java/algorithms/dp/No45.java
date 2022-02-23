package algorithms.dp;

import java.util.Arrays;

/**
 * 45. 跳跃游戏 II
 */
public class No45 {
    public static void main(String[] args) {
        int[] arr = {2, 3, 1, 1, 4};
        System.out.println(new Solution45().jump(arr));
    }
}

class Solution45 {
    int[] nums;
    int n;
    int[] cache;

    public int jump(int[] nums) {
        this.nums = nums;
        n = nums.length;
        cache = new int[n];
        Arrays.fill(cache, -1);
        return process(0);
    }

    /**
     * 从 idx 出发，到达数组末尾所需最小步数
     */
    private int process(int idx) {
        //如果idx就在末尾，则不需要跳跃，返回0
        if (idx == n - 1) {
            return 0;
        }
        //如果idx处已经被缓存过，则直接返回缓存值
        if (cache[idx] != -1) {
            return cache[idx];
        }
        //idx处能跳的最大步长
        int maxStep = nums[idx];
        int res = Integer.MAX_VALUE - 1;
        //在不越界的情况下，步长从1到maxStep递增，然后取最小值
        for (int step = 1; idx + step < n && step <= maxStep; step++) {
            int nextIdx = idx + step;
            int p = process(nextIdx);
            res = Math.min(res, p);
        }
        res++;
        cache[idx] = res;
        return res;
    }
}

class Solution45_II{
    //根据前面傻缓存法改的dp
    public int jump2(int[] nums) {
        int n = nums.length;
        int[] dp = new int[n];
        // 根据base case，dp[n-1] = 0，就不填了
        // 根据递归，process(idx)依赖process(nextIdx)，而nextIdx比idx大
        // 说明dp[左]依赖dp[右]，所以从右往左填
        for (int idx = n - 2; idx >= 0; idx--) {
            int maxStep = nums[idx];
            int res = Integer.MAX_VALUE - 1;
            for (int step = 1; idx + step < n && step <= maxStep; step++) {
                int nextIdx = idx + step;
                int p = dp[nextIdx];
                res = Math.min(res, p);
            }
            res++;
            dp[idx] = res;
        }
        return dp[0];
    }
}


