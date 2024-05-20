from random import random
from typing import List


class Solution:
    def sortArray(self, nums: List[int]) -> List[int]:
        self.sort(nums, 0, len(nums) - 1)
        return nums

    def sort(self, nums: List[int], l: int, r: int) -> None:
        if l >= r:
            return
        randIdx = l + random.randint(0, r - l)  # java中Random的nextInt(a)范围是[0,a), 而python中random.randint(0,a)范围是[0,a]
        self.swap(nums, r, randIdx)
        eq = self.devide(nums, l, r)
        self.sort(nums, l, eq[0] - 1)
        self.sort(nums, eq[1] + 1, r)

    def devide(self, nums: List[int], l: int, r: int) -> list[int]:
        if l >= r:
            return [l, r]
        target = nums[r]
        lessThanTargetBound = l - 1
        moreThanTargetBound = r
        cur = l
        while cur < moreThanTargetBound:
            if nums[cur] < target:
                self.swap(nums, cur, lessThanTargetBound + 1)
                lessThanTargetBound += 1
                cur += 1
            elif nums[cur] == target:
                cur += 1
            else:
                self.swap(nums, cur, moreThanTargetBound - 1)
                moreThanTargetBound -= 1
        # 此时cur与mttb重合，mttb右边的数都是大于target的，而r是target，它不能在mttb右侧，将它与mttb换位则刚好
        self.swap(nums, cur, r)
        return [lessThanTargetBound + 1, cur]

    def swap(self, nums: List[int], i: int, j: int) -> None:
        if i != j:
            tmp = nums[i]
            nums[i] = nums[j]
            nums[j] = tmp
