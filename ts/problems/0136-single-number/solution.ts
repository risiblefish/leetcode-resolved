/**
 * LeetCode 0136. 只出现一次的数字 / Single Number
 * https://leetcode.cn/problems/single-number/
 *
 * 思路：全部异或（XOR）一遍。
 *   a ^ a = 0（成对互消）  a ^ 0 = a（起点）  交换律 + 结合律（顺序无关）
 *   → 成对的自消，剩下那个就是落单的。
 *
 * 钩子：成对互消，留下单身。
 *
 * 复杂度：时间 O(n) / 空间 O(1)     ← 这是本题要求的「线性时间 + 不用额外空间」
 */

function singleNumber(nums: number[]): number {
  let ans = nums[0];
  for (let i = 1; i < nums.length; i++) {
    ans ^= nums[i];
  }
  return ans;
}

export default singleNumber;
export { singleNumber };