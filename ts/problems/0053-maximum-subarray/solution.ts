/**
 * LeetCode 0053. 最大子数组和 / Maximum Subarray
 * https://leetcode.cn/problems/maximum-subarray/
 *
 * 思路（Kadane · O(1) 空间）：连续子数组一定**以某个位置结尾** → 以 i 结尾的最优只依赖 i-1。
 *   `cur` = 一定以当前位置结尾的最大和
 *   递推：`cur = max(cur + nums[i], nums[i])` —— 前面拖后腿就「另起炉灶」
 *   答案 = 扫的过程中顺手取的最大值（不是 cur 的终值，因为结尾可能是负数）
 *
 * 钩子：**能接就接，拖后腿就另起炉灶**；答案边扫边取最大。
 *
 * 复杂度：时间 O(n) / 空间 O(1)
 *
 * r1 更新（2026-10-13）：自己察觉首版的 dp 数组冗余 —— `sum[i]` 只用到 `sum[i-1]`，
 *   一个变量滚起来就够。压掉数组，空间 O(n) → O(1)。顺带避开 PF-0005（`new Array(n)` 是 any[]）。
 */

function maxSubArray(nums: number[]): number {
  let cur = nums[0];
  let best = nums[0];

  for (let i = 1; i < nums.length; i++) {
    cur = Math.max(cur + nums[i], nums[i]);   // 接上前面 或 从自己重新开始
    best = Math.max(best, cur);
  }

  return best;
}

export default maxSubArray;
export { maxSubArray };
