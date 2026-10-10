/**
 * LeetCode 0350. 两个数组的交集 II / Intersection of Two Arrays II
 * https://leetcode.cn/problems/intersection-of-two-arrays-ii/
 *
 * 思路：两边都排序，再用双指针同行比较。
 *   小的一边前进（它不可能在另一边配对成功），相等就收进答案、两边一起前进。
 *   「带重复」的交集要求取两边出现次数的较小值，双指针天然满足（各消耗一次）。
 *
 * 钩子：两个有序数组逐位对齐 —— 小的走，相等就一起走。
 *
 * 复杂度：时间 O(n log n + m log m)（排序占主导）/ 空间 O(n + m)（两份复制，原数组不动）
 *   ⚠️ 也可用哈希计数 O(n + m)：见「可改进点」
 */

function intersect(nums1: number[], nums2: number[]): number[] {
  let shorter = undefined,
    longer = undefined;
  if (nums1.length >= nums2.length) {
    longer = [...nums1].sort((a, b) => a - b);
    shorter = [...nums2].sort((a, b) => a - b);
  } else {
    longer = [...nums2].sort((a, b) => a - b);
    shorter = [...nums1].sort((a, b) => a - b);
  }
  let ans: number[] = [];
  let s = 0,
    l = 0;
  while (s < shorter.length && l < longer.length) {
    if (shorter[s] < longer[l]) {
      s++;
    } else if (shorter[s] === longer[l]) {
      ans.push(shorter[s]);
      s++;
      l++;
    } else {
      l++;
    }
  }
  return ans;
}

export default intersect;
export { intersect };
