/**
 * LeetCode 0448. 找到所有数组中消失的数字 / Find All Numbers Disappeared in an Array
 * https://leetcode.cn/problems/find-all-numbers-disappeared-in-an-array/
 *
 * 思路：原地标记（in-place marking）。
 *   前提：nums 长度 n、元素取值 1..n → **值可以直接当座位号**（idx = 值 - 1）；
 *   数组允许原地改；要存的信息只有 1 bit（来过 / 没来过）→ 用符号位当标记。
 *
 *   第一圈：每个值 x「占座」—— nums[|x| - 1] 标记成负数。
 *           Math.abs 必须加，因为座位可能已被前面的值标记成负数。
 *           `-Math.abs(nums[idx])` 是幂等的：同一座位标 N 次结果一样。
 *   第二圈：座位仍是正数 ⇔ 值 i+1 从没出现过 → 它就是答案。
 *
 * 钩子：值域限定 → 值当座位号；符号位占位，原值靠 abs 还原。
 * 易错：答案必须等第二圈才能收（第一圈结束时标记才完整）。
 *
 * 复杂度：时间 O(n) / 空间 O(1)（返回的数组不算额外空间）
 */

function findDisappearedNumbers(nums: number[]): number[] {
  for (const x of nums) {
    const idx = Math.abs(x) - 1;
    nums[idx] = -Math.abs(nums[idx]);
  }

  const ans: number[] = [];
  for (let i = 0; i < nums.length; i++) {
    if (nums[i] > 0) ans.push(i + 1);
  }
  return ans;
}

export default findDisappearedNumbers;
export { findDisappearedNumbers };