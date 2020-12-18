package algorithms.dp;

/**
 * 122. 买卖股票的最佳时机 II
 *
 * @author Sean Yu
 * @date 2020/12/3 22:46
 */
public class No122 {
}

/**
 * 思路： 想象股价走势是一个有升有降的折线图，最大的收益是把所有上升部分买到
 * 所以，只要第i-1天的股价比第i天低，就把它加到结果里去
 */
class Solution122 {
    public int maxProfit(int[] prices) {
        if (prices == null || prices.length <= 1) {
            return 0;
        }
        int sum = 0;
        for (int i = 1 ; i < prices.length ; i++){
            int gap = prices[i] - prices[i-1];
            if(gap > 0) {
                sum += gap;
            }
        }
        return sum;
    }
}
