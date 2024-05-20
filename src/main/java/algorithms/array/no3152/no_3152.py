from typing import List


class Solution:
    def isArraySpecial(self, nums: List[int], queries: List[List[int]]) -> List[bool]:
        n = len(nums)
        start = [0] * n
        for i in range(1, n):
            if nums[i] % 2 != nums[i - 1] % 2:
                start[i] = start[i - 1]
            else:
                start[i] = i
        q_len = len(queries)
        ans = [False] * q_len
        for j in range(0, q_len):
            q_from = queries[j][0]
            q_to = queries[j][1]
            if start[q_from] == start[q_to]:
                ans[j] = True
        return ans
