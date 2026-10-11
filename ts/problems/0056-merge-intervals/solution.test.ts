import { describe, expect, it } from "vitest";
import solve from "./solution";

describe("0056 合并区间 / Merge Intervals", () => {
  it("示例 1", () => {
    expect(solve([[1, 3], [2, 6], [8, 10], [15, 18]])).toEqual([[1, 6], [8, 10], [15, 18]]);
  });

  it("示例 2：端点相接也算重叠", () => {
    expect(solve([[1, 4], [4, 5]])).toEqual([[1, 5]]);
  });

  it("反例（第一版错在这）：右端点必须取 max，被包住的小区间不能把大区间缩回去", () => {
    expect(solve([[1, 10], [2, 3]])).toEqual([[1, 10]]);
  });

  it("反例（第一版错在这）：端点相接 = 重叠（用 `<` 会漏掉）", () => {
    expect(solve([[1, 4], [4, 4]])).toEqual([[1, 4]]);
  });

  it("反例：左端点相同（用 `> a[0]` 会漏掉）", () => {
    expect(solve([[1, 3], [1, 5]])).toEqual([[1, 5]]);
  });

  it("边界：只有一个区间", () => {
    expect(solve([[5, 7]])).toEqual([[5, 7]]);
  });

  it("边界：完全分离 / 输入乱序（先排序）", () => {
    expect(solve([[1, 1], [2, 2]])).toEqual([[1, 1], [2, 2]]);
    expect(solve([[8, 10], [1, 3], [2, 6], [15, 18]])).toEqual([[1, 6], [8, 10], [15, 18]]);
  });

  it("边界：不改入参", () => {
    const input = [[1, 3], [2, 6]];
    solve(input);
    expect(input).toEqual([[1, 3], [2, 6]]);
  });
});