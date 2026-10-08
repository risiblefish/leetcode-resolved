/**
 * LeetCode 0121. 买卖股票的最佳时机 / Best Time to Buy and Sell Stock
 * https://leetcode.cn/problems/best-time-to-buy-and-sell-stock/
 *
 * 思路：一次扫描 + 维护历史最小值。
 *      先更新 lowest 再算收益（否则等于偷看未来）。
 *
 * 复杂度：时间 O(n) / 空间 O(1)
 */

function maxProfit(prices: number[]): number {
  let lowest = prices[0];
  let curMaxProfit = 0;
  for (let i = 1; i < prices.length; i++) {
    curMaxProfit = Math.max(curMaxProfit, prices[i] - lowest);
    lowest = Math.min(lowest, prices[i]);
  }
  return curMaxProfit;
}

export default maxProfit;
export { maxProfit };
