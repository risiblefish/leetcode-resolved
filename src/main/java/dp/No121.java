package dp;

/**
 * 121. 买卖股票的最佳时机
 *
 * 给定一个数组，它的第 i 个元素是一支给定股票第 i 天的价格。
 *
 * 如果你最多只允许完成一笔交易（即买入和卖出一支股票一次），设计一个算法来计算你所能获取的最大利润。
 *
 * 注意：你不能在买入股票前卖出股票。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/best-time-to-buy-and-sell-stock
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author Sean Yu
 * @date 2020/12/3 22:06
 */
public class No121 {
}

/**
 *   O（N）的解法：
 *   从0~n-1遍历每个点，记录当前点之前的最低价格，到第i个点时，计算i到前面最低点的差，然后保留最大的差，就是解。
 *   因为只有先卖，再卖，所以低点必须在卖出点之前。
 *   比如 5 3 4 2 1, 虽然最低点是1，但比如对4这天而言，1在4之后，所以要找在4之前的最低点，就是3。
 *
 */
class Solution121_2 {
    public int maxProfit(int[] prices) {
        if(prices == null || prices.length <= 1){
            return 0;
        }
        int n = prices.length;
        //记录当前点之前的最低点
        int formerMin = Integer.MAX_VALUE;
        //记录当前的最大差值
        int currMax = 0;

        for (int i = 0 ; i < n ; i++) {
            formerMin = Math.min(formerMin, prices[i]);
            currMax = Math.max(currMax, prices[i] - formerMin);
        }
        return currMax;
    }
}


