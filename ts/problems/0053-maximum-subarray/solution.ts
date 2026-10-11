/**
 * LeetCode 0053. 最大子数组和 / Maximum Subarray
 * https://leetcode.cn/problems/maximum-subarray/
 *
 * 思路（闭卷手写版）：Kadane。连续子数组一定**以某个位置结尾** → 以 i 结尾的最优解只依赖 i-1。
 *   1. `sum[i]` = 一定以 nums[i] 结尾的最大和
 *   2. 递推：`sum[i] = max(sum[i - 1] + nums[i], nums[i])` —— 前面拖后腿就「另起炉灶」
 *   3. 答案不是 `sum[n-1]`，而是所有 `sum[i]` 里的最大值（子数组可以在任意位置结尾）
 *
 * 钩子：**能接就接，拖后腿就另起炉灶**；答案是扫的过程中「顺手取最大」，不是最后一个。
 *
 * 复杂度：时间 O(n) / 空间 O(n)（dp 数组；压掉数组后 O(1)，见 notes「可改进点」）
 */

function maxSubArray(nums: number[]): number {
  const n = nums.length;
  // 注意：`new Array(n)` 推出的是 any[]（strict 不报错）→ 必须写 <number>，见 PF-0005
  const sum = new Array<number>(n);

  sum[0] = nums[0];
  let curMax = sum[0];

  for (let i = 1; i < n; i++) {
    sum[i] = Math.max(sum[i - 1] + nums[i], nums[i]); // 接上前面 或 从自己重新开始
    curMax = Math.max(curMax, sum[i]);
  }

  return curMax;
}

export default maxSubArray;
export { maxSubArray };