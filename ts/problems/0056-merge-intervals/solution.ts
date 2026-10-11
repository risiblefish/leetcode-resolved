/**
 * LeetCode 0056. 合并区间 / Merge Intervals
 * https://leetcode.cn/problems/merge-intervals/
 *
 * 思路（闭卷手写版，第二版）：按左端点排序 + 扫一遍。
 *   1. 按左端点升序排序 → 排序后「能合并的区间一定相邻」，只需看当前区间能不能接上上一个
 *   2. `pre` = 目前答案里最后那个区间；当前区间左端点 `cur[0] <= pre[1]` → 重叠，合并进答案
 *   3. 合并时右端点必须取 **max**（`max(cur[1], pre[1])`）—— 被包住的小区间不能把大区间缩回去
 *   4. 接不上 → 当前区间开新的一段
 *
 * 钩子：**按左端点排队，看能不能接上**；接上就延长右端（取 max），接不上就另起一段。
 *
 * 复杂度：时间 O(n log n)（排序主导）/ 空间 O(n)（答案）
 *   —— 另：排序动的是副本（`[...intervals]`）、push 的也是副本，**不改入参**。
 */

function merge(intervals: number[][]): number[][] {
  const sorted = [...intervals].sort((a, b) => a[0] - b[0]);

  let pre = sorted[0];
  const ans: number[][] = [[...pre]];

  for (let i = 1; i < sorted.length; i++) {
    const cur = sorted[i];
    if (cur[0] <= pre[1]) {
      // 重叠（含「端点相接」：cur[0] === pre[1] 也算）
      ans.pop();
      const merged = [pre[0], Math.max(cur[1], pre[1])];
      ans.push(merged);
      pre = merged;
    } else {
      ans.push([...cur]);
      pre = cur;
    }
  }

  return ans;
}

export default merge;
export { merge };