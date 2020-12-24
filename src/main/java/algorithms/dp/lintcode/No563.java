package algorithms.dp.lintcode;

/**
 * @author Sean Yu
 * @date 2020/12/24 12:17
 */
public class No563 {
    public static void main(String[] args) {
        System.out.println(new Solution563().backPackV(new int[]{1, 2, 3, 3, 7}, 7));
    }
}

/**
 * 1.分析最后一步
 * 对前i个数而言，如果存在方案，使得nums[0]到nums[i-1]数能够凑出target，那么分2种情况：
 * （1）包含第nums[i-1]，那么前i-1个数必定能够凑出target - nums[i-1]
 * (2)不包含第i-1个数，即在使用nunms[i-1]之前，前面的数已经能够凑出target
 * 所以，对于前i-1个数，凑出target的方案总数，是（1）+（2）
 * 子问题：求前i-1个数，凑出w的总数（w <= target）
 * <p>
 * 2. 确定状态转移方程
 * 令dp[i][w]，表示前i个数刚好凑出w的方案总数
 * 那么有
 * dp[i][w] = dp[i-1][w-nums[i-1]] + dp[i-1][w], 其中 0 <= w <= target
 * <p>
 * 3. 确定初始条件和边界情况
 * dp[0][0] = 1 //令前0个数凑出0，一定有1种方案
 * dp[0][w] | w>0 //前0个数凑不出大于0的数
 * <p>
 * w - nums[i-1] >= 0 时才进行计算
 */
class Solution563 {
    /**
     * @param nums:   an integer array and all positive numbers
     * @param target: An integer
     * @return: An integer
     */
    public int backPackV(int[] nums, int target) {
        // write your code here
        // 题目保证nums均为正数
        int n = nums.length;
        //初始化,数组要各多开一位
        int[][] dp = new int[n + 1][target+1];
        dp[0][0] = 1;
        for (int i = 1; i <= n; i++) {
            for (int w = 0; w <= target; w++) {
                dp[i][w] = dp[i - 1][w];
                if (w - nums[i - 1] >= 0) {
                    dp[i][w] += dp[i - 1][w - nums[i - 1]];
                }
            }
        }
        return dp[n][target];
    }
}