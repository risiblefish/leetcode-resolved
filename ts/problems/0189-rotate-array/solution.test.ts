import { describe, expect, it } from "vitest";
import solve from "./solution";

describe("0189 轮转数组 / Rotate Array", () => {
  it("示例 1", () => {
    const nums = [1, 2, 3, 4, 5, 6, 7];
    solve(nums, 3);
    expect(nums).toEqual([5, 6, 7, 1, 2, 3, 4]);
  });

  it("示例 2", () => {
    const nums = [-1, -100, 3, 99];
    solve(nums, 2);
    expect(nums).toEqual([3, 99, -1, -100]);
  });

  it("边界：k === 0（不能动）", () => {
    const nums = [1, 2, 3];
    solve(nums, 0);
    expect(nums).toEqual([1, 2, 3]);
  });

  it("反例：k === n 时原样返回（`slice(-0)` 会返回整个数组的坑）", () => {
    const nums = [1, 2, 3];
    solve(nums, 3);
    expect(nums).toEqual([1, 2, 3]);
  });

  it("边界：k > n → 先取模", () => {
    const nums = [1, 2];
    solve(nums, 5); // 5 % 2 === 1
    expect(nums).toEqual([2, 1]);
  });

  it("边界：单元素 / 空数组", () => {
    const one = [7];
    solve(one, 9);
    expect(one).toEqual([7]);

    const empty: number[] = [];
    solve(empty, 3);
    expect(empty).toEqual([]);
  });

  it("反例：原地修改（返回 void，且不能新建数组了事）", () => {
    const nums = [1, 2, 3, 4];
    const ret = solve(nums, 1);
    expect(ret).toBeUndefined();
    expect(nums).toEqual([4, 1, 2, 3]);
  });
});