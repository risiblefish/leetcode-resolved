/**
 * LeetCode 0238. 除了自身以外数组的乘积 / Product of Array Except Self
 * https://leetcode.cn/problems/product-of-array-except-self/
 *
 * 思路（前后缀乘积 · O(1) 额外空间）：
 *   每个位置的答案 = **左边所有数的积 × 右边所有数的积**（空积 = 1）。
 *
 *   1. 第一遍从左扫，让 ans[i] 存「左积」：
 *        ans[0] = 1；ans[i] = ans[i-1] * nums[i-1]
 *   2. 第二遍从右扫，用一个滚动变量 rPre（= 当前位置右边累积的积，初始 1）：
 *        先用：ans[i] *= rPre
 *        再更新：rPre *= nums[i]
 *      ⚠️ 顺序别反——反了会把 nums[i] 自己也算进右积，错一位。
 *
 * 为什么这题走前后缀：
 *   - 禁用除法 → 不能走 total / nums[i]
 *   - 一次扫做不完 → 分段扫（左扫 + 右扫）
 *   - 要求 O(1) 额外空间（输出不计）→ 答案数组兼做 left[]，right 用滚动变量
 *
 * 含 0 天然处理，不用特判：
 *   - 1 个 0：那个位置是左右积（非零），其他位置都是 0（L 或 R 必有一个含 0）
 *   - ≥2 个 0：全部位置都是 0
 *
 * 复杂度：时间 O(n) / 空间 O(1) 额外（输出 ans 不计）
 */

function productExceptSelf(nums: number[]): number[] {
    const ans: number[] = new Array<number>(nums.length);  // ⚠️ 必须写 <number>，否则 any[]（PF-0005）
    ans[0] = 1;
    for (let i = 1; i < nums.length; i++) {
        ans[i] = ans[i - 1] * nums[i - 1];
    }
    // 此时 ans[i] = left[i]
    // rPre = r[i+1]
    let rPre = 1;
    for (let i = nums.length - 1; i >= 0; i--) {
        ans[i] *= rPre;
        rPre *= nums[i];
    }
    return ans;
}

export default productExceptSelf;
export { productExceptSelf };
