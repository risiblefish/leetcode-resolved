import { describe, expect, it } from "vitest";
import solve from "./solution";

describe("0128 最长连续序列 / Longest Consecutive Sequence", () => {
  it("示例 1", () => {
    expect(solve([100, 4, 200, 1, 3, 2])).toBe(4);
  });

  it("示例 2：带重复值", () => {
    expect(solve([0, 3, 7, 2, 5, 8, 4, 6, 0, 1])).toBe(9);
  });

  it("边界：空数组 / 单元素", () => {
    expect(solve([])).toBe(0);
    expect(solve([1])).toBe(1);
  });

  it("反例：重复值不重复计数", () => {
    expect(solve([1, 1, 1])).toBe(1);
    expect(solve([1, 2, 0, 1])).toBe(3);
  });

  it("反例：负数也连续（只盯正数就错）", () => {
    expect(solve([-1, 0, 1])).toBe(3);
    expect(solve([-3, -2, -1, 5])).toBe(3);
  });

  it("反例：连续指「数值连续」，不是相邻下标", () => {
    expect(solve([1, 10, 2, 20, 3])).toBe(3);
    expect(solve([5, 7, 9])).toBe(1);
  });

  it("边界：两段等长连续 → 取到任一段都行", () => {
    expect(solve([1, 2, 3, 10, 11, 12])).toBe(3);
  });
});