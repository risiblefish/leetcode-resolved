package algorithms.dp;

/**
 * 714. 买卖股票的最佳时机含手续费
 *
 * @author Sean Yu
 */
public class No714 {
    public static void main(String[] args) {
        System.out.println(new Solution714().maxProfit(new int[]{1, 3, 2, 8, 4, 9}, 2));
    }
}

/**
 * 因为一次只能持有一只股票，对于第i天结束后：要么手里有股票，要么没股票。
 *
 * 令dp[i][0]表示第i天手里没股票的最大收益，algorithms.dp[i][1]表示第i天手里有股票的最大收益，那么有
 *
 * algorithms.dp[i][0] = max{
 *     algorithms.dp[i-1][0] //前一天也没有股票，今天也没有股票，收益与前一天没股票的情况相同
 *     algorithms.dp[i-1][1] + p[i] - p[i-1] - fee  //前一天有股票，今天没股票，所以收益是 前一天有股票的最大收益 + 今天卖出股票并扣掉手续费的额外收益
 *     }
 *
 * algorithms.dp[i][1] = max{
 *    algorithms.dp[i-1][1] + p[i] - p[i-1] //前一天手里有股票，今天继续持有，额外收益就是今天股票价格与前一天价格的差值，因为没有交易，所以不扣手续费
 *    algorithms.dp[i-1][0] //前一天手里没股票，今天有股票，是买入情况，所以没有额外收益
 *    }
 *
 */

class Solution714 {
    public int maxProfit(int[] prices, int fee) {
        //题目保证prices非空
        int n = prices.length;

        int[][] dp = new int[n][2];

        for (int i = 1 ; i < n ; i++){
            dp[i][0] = Math.max(dp[i-1][0], dp[i-1][1] + prices[i] - prices[i-1] - fee);
            dp[i][1] = Math.max(dp[i-1][1] + prices[i] - prices[i-1] , dp[i-1][0]);
        }

        return dp[n-1][0];
    }
}