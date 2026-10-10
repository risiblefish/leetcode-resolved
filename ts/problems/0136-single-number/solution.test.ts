import { describe, expect, it } from "vitest";
import solve from "./solution";

describe("0136 只出现一次的数字 / Single Number", () => {
  it("示例 1", () => {
    expect(solve([2, 2, 1])).toBe(1);
  });

  it("示例 2", () => {
    expect(solve([4, 1, 2, 1, 2])).toBe(4);
  });

  it("边界：只有一个元素", () => {
    expect(solve([1])).toBe(1);
  });

  it("反例：落单的在中间，成对元素跨在它两边（验证交换律）", () => {
    expect(solve([1, 2, 3, 2, 1])).toBe(3);
  });

  it("反例：负数（XOR 只看位，不关心符号）", () => {
    expect(solve([-1, -1, -7])).toBe(-7);
  });

  it("反例：落单的正好是 0（a ^ 0 = a，别把 0 当'没找到'）", () => {
    expect(solve([5, 5, 0])).toBe(0);
  });
});