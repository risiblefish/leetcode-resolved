/**
 * LeetCode 0189. 轮转数组 / Rotate Array
 * https://leetcode.cn/problems/rotate-array/
 *
 * 思路（闭卷手写版）：暂存 + 从后往前整体后移 + 回填。
 *   1. k %= n（k 可以大于 n）；k === 0 / 空数组直接返回
 *   2. 先把「会绕到前面去」的尾部 k 个暂存起来
 *   3. 从后往前搬：nums[i + k] = nums[i]（**必须从后往前**，否则前一个覆盖后一个）
 *   4. 把暂存的 k 个写回 [0, k)
 *
 * 钩子：先存尾巴，再从后往前倒着搬，最后回填 ——「倒着搬，不踩自己」。
 *
 * 复杂度：时间 O(n) / 空间 O(k)（另有原地 O(1) 版：三次翻转，见 notes「可改进点」）
 */

function rotate(nums: number[], k: number): void {
  const n = nums.length;
  if (n === 0) return;

  k %= n;
  if (k === 0) return;

  // 用 n - k 而不是 -k：k 为 0 时 slice(-0) 会返回**整个数组**，是个容易埋雷的写法
  const tail = nums.slice(n - k);

  for (let i = n - k - 1; i >= 0; i--) {
    nums[i + k] = nums[i];
  }
  for (let i = 0; i < k; i++) {
    nums[i] = tail[i];
  }
}

export default rotate;
export { rotate };