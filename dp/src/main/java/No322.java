/**
 * @author Sean Yu
 * @date 2020/11/22 13:29
 */
public class No322 {
}


/**
 * 5个步骤：
 *
 * 1.精炼题目：
 * 共有n = coins.length种硬币，面值分别为coins[0],coins[1],,,,coins[n-1]，需要判断是否能用任意数量的这些硬币恰好凑出amount，如果存在，求出最少使用的硬币数量
 *
 * 2.确定子问题
 * 假设能凑出amount，且最少数量为k, 假设这k枚硬币的面值分别为a1,a2,,,,ak, 则应有，去掉最后一枚硬币ak之后，剩下amount-ak 需用 k-1 枚硬币仍然为最优解
 * 子问题就变成了：
 * 如何用最少数量的硬币凑出amount- ak, 其中，ak的面值只可能是 coins[0] ~ coins[n-1]
 *
 * 3.确定状态
 * 假设dp(x) = 凑出总和恰好为x所用的最少硬币数量
 * 则有状态转移方程： dp(x) = min{ dp(x-coins[0])+1, dp(x-coins[1])+1, ... , dp(x-coins[n-1]) + 1 }
 *
 * 4.确定初始状态和边界条件
 * dp(0) = 0
 * dp(负数) = 正无穷（即无解），之所以用正无穷，是为了便于Math.min()方法
 *
 * 5.确定计算顺序
 * 从状态转移方程可以看到 dp(x)的值取决于dp(x - coins[i])，即x取决于比x小的值，所以要先算小的，再算大的，否则就可能出现重复计算的问题。
 * 即应当从dp(0),dp(1),...,dp(amount)这个顺序来计算
 */
class Solution322 {
    public int coinChange(int[] coins, int amount) {
        int[] dp = new int[amount + 1];
        dp[0] = 0;
        for(int i = 1 ; i <= amount ; i++) {
            //令MAX_VALUE表示正无穷
            dp[i] = Integer.MAX_VALUE;
            //dp[i] = min {dp[i-coin[j]] + 1}
            for(int j = 0 ; j < coins.length ; j++) {
                if(i - coins[j] >= 0) {
                    int count = dp[i - coins[j]];
                    //如果已经是正无穷，则继续正无穷，防止溢出错误
                    count = count == Integer.MAX_VALUE ? Integer.MAX_VALUE : count + 1;
                    if(count < dp[i]) {
                        dp[i] = count;
                    }
                }
            }
        }
        return dp[amount] == Integer.MAX_VALUE ? -1 : dp[amount];
    }
}
