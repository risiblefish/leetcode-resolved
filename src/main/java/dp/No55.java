package dp;

/**
 * @author Sean Yu
 * @date 2020/11/22 23:15
 */
public class No55 {
    public static void main(String[] args) {
        new Solution55().canJump(new int[]{2,0,0});
    }
}


/**
 * 1. 确定子问题
 * n = nums.length， 则石头的编号为0,1,2,...n-1
 * 假设能从第i块石头跳到最后一块石头(n-1)上，则有：
 * （1）能跳到第i块石头上(i <= n-1)
 * （2）i + nums[i] >= n-1
 * <p>
 * 子问题就变成了： 能否跳到第i块石头上， 0 <= i < n-1
 * <p>
 * 2. 确定状态转移方程
 * 假设dp(i)表示能否跳到第i块石头上
 * <p>
 * 则，dp(i) = 枚举 k = 0,1,,,i-1 ,若同时满足2个条件
 * （1）dp[k] = true
 * （2）k + nums[k] >= i
 *  则dp[i] = true
 * <p>
 * 3. 确定初始情况和边界条件
 * dp(0) = true
 * <p>
 * 4. 计算顺序
 * 从小到大枚举
 */
class Solution55 {
    public boolean canJump(int[] nums) {
        if(nums == null || nums.length == 0) {
            return false;
        }
        int n = nums.length;
        boolean[] dp = new boolean[n];
        dp[0] = true;
        for (int i = 1; i < n; i++) {
            for (int k = 0; k < i; k++) {
                if(dp[k] && k + nums[k] >= i) {
                    dp[i] = true;
                     break; //优化写法
                }
                //思考：第一次提交写成这样有什么问题？
                //问题在于：dp[i]无论如何都会被更新，而上面那种写法，dp[i]只有在满足if条件时才会被更新，下面这种写法可能导致dp[i]本来是true，然后被更新为false
                //dp[i] = dp[k] && k + nums[k] >= i;
            }
        }
        return dp[n - 1];
    }
}
