/**
 * LeetCode 0128. 最长连续序列 / Longest Consecutive Sequence
 * https://leetcode.cn/problems/longest-consecutive-sequence/
 *
 * 思路（闭卷手写版）：值全塞 Set（O(1) 查「在不在」），每个数只被数一次。
 *   1. 先去重（`Set.add` 本身就去重，不用先 has）
 *   2. 任取一个还在表里的数 val：向左、向右各伸一次，伸到的都 `delete` 掉
 *   3. 数完的整条链就从表里消失了 —— 后面的循环不会再重复数它 → **总工作量 O(n)**
 *
 * 钩子：**相邻的数用 Set 找**；数过的链当场删掉 ——「数一条，删一条，绝不重数」。
 *
 * 复杂度：时间 O(n)（每个元素最多被删一次）/ 空间 O(n)
 */

function longestConsecutive(nums: number[]): number {
  const seen = new Set<number>();
  for (const val of nums) {
    seen.add(val); // Set 天然去重
  }

  let curMax = 0;
  for (const val of seen) {
    let cnt = 1;

    let left = val - 1;
    while (seen.has(left)) {
      cnt++;
      seen.delete(left--); // 先删 left，再 left - 1 继续往左找
    }

    let right = val + 1;
    while (seen.has(right)) {
      cnt++;
      seen.delete(right++);
    }

    curMax = Math.max(curMax, cnt);
  }

  return curMax;
}

export default longestConsecutive;
export { longestConsecutive };