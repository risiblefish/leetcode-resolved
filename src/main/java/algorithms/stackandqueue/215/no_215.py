import heapq
from typing import List


class Solution215:
    def findKthLargest(self, nums: List[int], k: int) -> int:
        pq = []
        for i in range(0, k):
            heapq.heappush(pq, nums[i])
        for i in range(k, len(nums)):
            topVal = pq[0]
            if nums[i] > topVal:
                heapq.heappop(pq)
                heapq.heappush(pq, nums[i])
        return heapq.heappop(pq)


if __name__ == "__main__":
    # t = Solution3()
    # print(t.lengthOfLongestSubstring("dvds"))
    for i in range(0, 1):
        print(i)
