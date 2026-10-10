/**
 * LeetCode 0217. 存在重复元素 / Contains Duplicate
 * https://leetcode.cn/problems/contains-duplicate/
 *
 * 思路：先排序，再看相邻两位是否相等。
 *   排序后相等的元素必然挨着，所以扫一遍相邻对就够了。
 *
 * 钩子：排序后，重复的必然相邻。
 *
 * 复杂度：时间 O(n log n) / 空间 O(n)（复制了一份数组；原数组不动）
 *   ⚠️ 本题有 O(n) 解：Set 边扫边查（见「可改进点」）
 */

function containsDuplicate(nums: number[]): boolean {
  const sorted = [...nums].sort((a, b) => a - b);
  for (let i = 0; i < sorted.length - 1; i++) {
    if (sorted[i] == sorted[i + 1]) return true;
  }
  return false;
}

export default containsDuplicate;
export { containsDuplicate };
