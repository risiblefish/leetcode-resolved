/**
 * LeetCode 0217. 存在重复元素 / Contains Duplicate
 * https://leetcode.cn/problems/contains-duplicate/
 *
 * 思路：Set 边扫边查（最优解 O(n)）。
 *   每读一个数，先问 Set「见过吗」——见过就是重复；没见过就记下来。
 *
 * 钩子：见过就 has，没见过就 add；命中即重复。
 *
 * 复杂度：时间 O(n) / 空间 O(n)
 *   （首版用的是「排序后看相邻」O(n log n)，r1 复习时换成了这版）
 */

function containsDuplicate(nums: number[]): boolean {
  const seen = new Set<number>();
  for (const val of nums) {
    if (seen.has(val)) return true;
    seen.add(val);
  }
  return false;
}

export default containsDuplicate;
export { containsDuplicate };