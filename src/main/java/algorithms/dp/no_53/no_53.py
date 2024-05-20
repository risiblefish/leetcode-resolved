from typing import List


class Solution:
    def maxSubArray(self, nums: List[int]) -> int:
        ans = nums[0]
        pre = ans
        n = len(nums)
        for i in range(1, n):
            cur = nums[i]
            if pre >= 0:
                cur += pre
            ans = max(ans, cur)
            pre = cur
        return ans
