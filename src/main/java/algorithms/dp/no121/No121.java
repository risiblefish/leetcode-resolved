package algorithms.dp;

/**
 * 121. 买卖股票的最佳时机
 */
public class No121 {
}

/**
 * O（N）的解法：
 * 从0~n-1遍历每个点，记录当前点之前的最低价格，到第i个点时，计算i到前面最低点的差，然后保留最大的差，就是解。
 * 题目要求买卖日期必须不同，所以遍历到第i天时，（1）先计算prices[i]-之前的最低价，（2）然后再更新最低价 (1)(2)顺序不能打乱
 */
class Solution121 {
    public int maxProfit(int[] prices) {
        int curLowest = prices[0];
        int ans = 0;
        for (int i = 1; i < prices.length; i++) {
            ans = Math.max(ans, prices[i] - curLowest);
            curLowest = Math.min(curLowest, prices[i]);
        }
        return ans;
    }
}

/**
 * 剪枝版本：
 * 因为我们将最大收益初始化为0
 * 只有当天价格>当前最低价时，才会有收益，（否则为负数）
 * 只有当天价格<当前最低价时，才有更新当前最低价的意义
 */
class Solution121_II {
    public int maxProfit(int[] prices) {
        int curLowest = prices[0];
        int ans = 0;
        for (int i = 1; i < prices.length; i++) {
            //剪枝
            if (prices[i] > curLowest) {
                ans = Math.max(ans, prices[i] - curLowest);
            } else if (prices[i] < curLowest) {
                curLowest = prices[i];
            }//else prices[i]==curLowest continue
        }
        return ans;
    }
}


