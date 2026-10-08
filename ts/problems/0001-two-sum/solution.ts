/**
 * LeetCode 0001. 两数之和 / Two Sum
 * https://leetcode.cn/problems/two-sum/
 *
 * 思路：哈希一遍过。遍历时在 Map 里查「我需要的搭档之前出现过吗」。
 *      先查再存，避免 [3,3] target=6 把自己当搭档。
 *
 * 复杂度：时间 O(n) / 空间 O(n)
 */

function twoSum(nums: number[], target: number): number[] {
  const seen = new Map<number, number>();
  for (let i = 0; i < nums.length; i++) {
    const another = target - nums[i];
    if (seen.has(another)) {
      return [seen.get(another)!, i];
    }
    seen.set(nums[i], i);
  }
  return [];
}

export default twoSum;
export { twoSum };
