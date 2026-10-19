import { describe, expect, it } from "vitest";
import solve from "./solution";

// 规范化 -0 → +0：负数×零产生 -0，vitest.toEqual 区分它和 +0，但 LeetCode 序列化成 0。
// 见 PF-0008：false)。所以轻量归一化，不污染 solution。
const normalize = (arr: number[]) => arr.map(x => x + 0);

describe("0238 除了自身以外数组的乘积 / Product of Array Except Self", () => {
  it("示例 1", () => {
    expect(solve([1, 2, 3, 4])).toEqual([24, 12, 8, 6]);
  });

  it("示例 2：含 1 个 0 → 只有 0 的位置非零（负数×零打 -0，见 PF-0008）", () => {
    expect(normalize(solve([-1, 1, 0, -3, 3]))).toEqual([0, 0, 9, 0, 0]);
  });

  it("反例：含 ≥2 个 0 → 全部位置都是 0（抓「单 0 特判」写错的用例）", () => {
    expect(normalize(solve([2, 0, 3, 0, 4]))).toEqual([0, 0, 0, 0, 0]);
  });

  it("反例：含负数（奇偶数负号都要对）", () => {
    expect(solve([-2, -3, -4])).toEqual([12, 8, 6]);
    expect(solve([-1, 2, 3])).toEqual([6, -3, -2]);
  });

  it("边界：恰好两个元素", () => {
    expect(solve([3, 5])).toEqual([5, 3]);
  });

  it("反例：抓「右扫边界错写 `i > 0`」—— ans[0] 必须被 rPre 乘到", () => {
    // 本题作者本次踩坑：`for (let i = n-2; i > 0; i--)` 让 right[0] 漏算，
    // ans[0] 变 undefined → NaN → LeetCode 序列化成 null。
    // 这个用例要求 ans[0] 必须等于右边所有数的积。
    expect(solve([1, 2, 3, 4])[0]).toBe(24);
  });

  it("边界：全 1 → 全 1", () => {
    expect(solve([1, 1, 1, 1])).toEqual([1, 1, 1, 1]);
  });
});
