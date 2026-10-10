/**
 * LeetCode 0350. 两个数组的交集 II / Intersection of Two Arrays II
 * https://leetcode.cn/problems/intersection-of-two-arrays-ii/
 *
 * 思路：哈希计数（最优解 O(n + m)）——「带重复的交集」= 取两边出现次数的较小值。
 *   1. 用小的一边建计数表（省内存）
 *   2. 扫大的一边：表里还有额度就收一个、额度 -1，扣光就删键
 *
 * 钩子：小表存额度，大数组来花；花完即止。
 *
 * 复杂度：时间 O(n + m) / 空间 O(min(n, m))
 *   （另有一条路：都排序 + 双指针 O(n log n + m log m)，是本题「输入已排序」进阶问的答案
 *     —— 那是 two-pointers 类型的主课，见 notes「可改进点 / 标准要点」）
 */

function intersect(nums1: number[], nums2: number[]): number[] {
  const longer = nums1.length >= nums2.length ? nums1 : nums2;
  const shorter = nums1.length >= nums2.length ? nums2 : nums1;

  const quota = new Map<number, number>();
  for (const val of shorter) {
    quota.set(val, (quota.get(val) ?? 0) + 1);
  }

  const ans: number[] = [];
  for (const val of longer) {
    const left = quota.get(val) ?? 0;
    if (left > 0) {
      ans.push(val);
      if (left - 1 === 0) {
        quota.delete(val);
      } else {
        quota.set(val, left - 1);
      }
    }
  }
  return ans;
}

export default intersect;
export { intersect };