package jzoffer.dp;

/**
 * 剑指 Offer 63. 股票的最大利润
 *
 * @author Sean Yu
 */
public class JzNo63 {
    public static void main(String[] args) {
        new SolutionJzNo63().maxProfit(new int[]{});
    }
}


/**
 * 因为只能交易1次，对于第i天而言，当天能赚的最多钱的办法，就是在第i天之前的价格最低那天买入，然后在第i天卖出
 * 所以，可以用一个变量，来保留到第i天之前，所找到的最低价格，并在每次遍历后更新这个最低价格。
 *
 * 对于第i天而言，
 * （1）如果当天价格比前面最低的价格更低，那就把最低价格更新为第i天的价格
 * （2）如果当天价格比前面最低的价格高，那么第i天最多能赚 prices[i] - 之前的最低价格，然后用一个变量来更新所有赚钱的情况下，赚的最多的那次交易的差值
 *
 */
class SolutionJzNo63 {
    public int maxProfit(int[] prices) {
        //题目保证数组长度非负
        int n = prices.length;

        if (n == 0) {
            return 0;
        }

        int currMin = prices[0], currMax = 0;

        for (int i = 1; i < n; i++) {
            if (prices[i] < currMin) {
                currMin = prices[i];
            } else if (prices[i] > currMin) {
                currMax = Math.max(prices[i] - currMin, currMax);
            }
        }

        return currMax;
    }
}
