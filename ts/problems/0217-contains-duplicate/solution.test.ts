import { describe, expect, it } from "vitest";
import solve from "./solution";

describe("0217 存在重复元素 / Contains Duplicate", () => {
  it("示例 1", () => {
    expect(solve([1, 2, 3, 1])).toBe(true);
  });

  it("示例 2", () => {
    expect(solve([1, 2, 3, 4])).toBe(false);
  });

  it("示例 3", () => {
    expect(solve([1, 1, 1, 3, 3, 4, 3, 2, 4, 2])).toBe(true);
  });

  it("边界：只有一个元素", () => {
    expect(solve([7])).toBe(false);
  });

  it("边界：空数组（防御性；题面保证至少 1 个）", () => {
    expect(solve([])).toBe(false);
  });

  it("反例：0 是合法元素，别拿 0 当=没找到=的哨兵", () => {
    expect(solve([0, 0])).toBe(true);
  });

  it("反例：重复的两个恰好在有序后的两端（[5,1,2,3,4,5]）", () => {
    expect(solve([5, 1, 2, 3, 4, 5])).toBe(true);
  });

  it("反例：负数重复", () => {
    expect(solve([-3, -1, -3, 2])).toBe(true);
  });

  it("反例：重复的只出现两次，其余全是不同值", () => {
    expect(solve([1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 10])).toBe(true);
  });
});
