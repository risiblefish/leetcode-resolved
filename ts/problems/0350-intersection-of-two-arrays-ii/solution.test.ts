import { describe, expect, it } from "vitest";
import solve from "./solution";

/** 题目只要求「元素可任意顺序」，比较前统一排序 */
const sorted = (xs: number[]) => [...xs].sort((a, b) => a - b);

describe("0350 两个数组的交集 II / Intersection of Two Arrays II", () => {
  it("示例 1", () => {
    expect(sorted(solve([1, 2, 2, 1], [2, 2]))).toEqual([2, 2]);
  });

  it("示例 2", () => {
    expect(sorted(solve([4, 9, 5], [9, 4, 9, 8, 4]))).toEqual([4, 9]);
  });

  it("边界：无交集", () => {
    expect(solve([1, 2], [3, 4])).toEqual([]);
  });

  it("边界：单元素相同", () => {
    expect(solve([1], [1])).toEqual([1]);
  });

  it("反例：重复次数取两边较小值（[2,2,2] vs [2,2] → 只能两个）", () => {
    expect(sorted(solve([2, 2, 2], [2, 2]))).toEqual([2, 2]);
  });

  it("反例：一边只有一个（[3,3] vs [3] → 只能一个）", () => {
    expect(solve([3, 3], [3])).toEqual([3]);
  });

  it("反例：同一个值在不同位置反复出现，不能越界重复计数", () => {
    expect(sorted(solve([1, 1, 1], [1, 1, 1, 1]))).toEqual([1, 1, 1]);
  });
});
