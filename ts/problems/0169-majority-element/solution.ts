/**
 * LeetCode 0169. 多数元素 / Majority Element
 * https://leetcode.cn/problems/majority-element/
 *
 * 思路：Boyer-Moore 投票法（打群架）。
 *   同数抱团，不同数一换一同归于尽；多数元素出现 > n/2，
 *   就算其他所有数都拿来和它对消，它也至少剩 1 票 → 最后站着的一定是它。
 *
 * 钩子：多数派抵消不完，最后站着的就是它。
 *   cand 换人 ⇔ cnt 归零；cnt 是「净剩票数」而不是 candidate 的真实计数。
 *
 * 复杂度：时间 O(n) / 空间 O(1)     ← 排序/哈希都做不到 O(1) 额外空间
 * 前提：题目保证多数元素一定存在，所以不需要第二遍核对真实次数
 */

function majorityElement(nums: number[]): number {
  let cnt = 0;
  let candidate = nums[0];
  for (const num of nums) {
    if (cnt === 0) {
      candidate = num;
      cnt = 1;
    } else if (num === candidate) {
      cnt++;
    } else {
      cnt--;
    }
  }
  return candidate;
}

export default majorityElement;
export { majorityElement };