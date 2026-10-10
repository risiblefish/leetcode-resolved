import { describe, expect, it } from "vitest";
import solve from "./solution";

describe("0001 两数之和 / Two Sum", () => {
  it("示例 1", () => {
    expect(solve([2, 7, 11, 15], 9)).toEqual([0, 1]);
  });

  it("示例 2", () => {
    expect(solve([3, 2, 4], 6)).toEqual([1, 2]);
  });

  it("反例：[3,3] target=6 —— 不能把自己当搭档（先查再存）", () => {
    expect(solve([3, 3], 6)).toEqual([0, 1]);
  });

  it("边界：答案在数组末尾", () => {
    expect(solve([5, 75, 25], 100)).toEqual([1, 2]);
  });

  it("边界：含负数 / 目标为 0", () => {
    expect(solve([-3, 4, 3, 90], 0)).toEqual([0, 2]);
  });

  it("边界：两元素即答案", () => {
    expect(solve([1, 2], 3)).toEqual([0, 1]);
  });
});