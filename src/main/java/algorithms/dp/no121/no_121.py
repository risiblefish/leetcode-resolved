class Solution:
    def maxProfit(self, prices: List[int]) -> int:
        cur_lowest = prices[0]
        ans = 0
        for today in range(1, len(prices)):
            if prices[today] > cur_lowest:
                ans = max(ans, prices[today] - cur_lowest)
            elif prices[today] < cur_lowest:
                cur_lowest = prices[today]
        return ans
