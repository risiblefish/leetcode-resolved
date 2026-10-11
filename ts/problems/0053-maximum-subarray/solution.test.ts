import { describe, expect, it } from "vitest";
import solve from "./solution";

describe("0053 最大子数组和 / Maximum Subarray", () => {
  it("示例 1", () => {
    expect(solve([-2, 1, -3, 4, -1, 2, 1, -5, 4])).toBe(6);
  });

  it("示例 2：单元素", () => {
    expect(solve([1])).toBe(1);
  });

  it("示例 3", () => {
    expect(solve([5, 4, -1, 7, 8])).toBe(23);
  });

  it("反例：全负数 → 答案是「最大的那个负数」，不是 0（初值写 0 就错）", () => {
    expect(solve([-3, -1, -2])).toBe(-1);
    expect(solve([-1])).toBe(-1);
    expect(solve([-5, -2, -8, -1])).toBe(-1);
  });

  it("反例：答案是扫的过程中取的 max，不是最后一项（结尾是负数不能吐出来）", () => {
    expect(solve([-1, 2, -1, 3, -5])).toBe(4);
    expect(solve([1, -2, 3, -1, 4, -10])).toBe(6);
  });

  it("反例：拖后腿就另起炉灶", () => {
    expect(solve([-2, 1])).toBe(1);
    expect(solve([1, -1, -1, -1, 5])).toBe(5);
  });

  it("边界：单个 0 / 开头就是最大", () => {
    expect(solve([0])).toBe(0);
    expect(solve([9, -1, -1])).toBe(9);
  });
});