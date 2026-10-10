import { describe, expect, it } from "vitest";
import solve from "./solution";

describe("0169 多数元素 / Majority Element", () => {
  it("示例 1", () => {
    expect(solve([3, 2, 3])).toBe(3);
  });

  it("示例 2", () => {
    expect(solve([2, 2, 1, 1, 1, 2, 2])).toBe(2);
  });

  it("边界：只有一个元素", () => {
    expect(solve([1])).toBe(1);
  });

  // ↓↓↓ 下面三组是「循环上界用 candidate 的值」那个版本的照妖镜 ↓↓↓
  it("反例：多数元素的值(2) 小于数组长度时，循环不能提前结束", () => {
    expect(solve([1, 2, 2, 2, 2])).toBe(2);
  });

  it("反例：多数元素最后才翻盘", () => {
    expect(solve([9, 1, 9, 1, 9, 1, 9])).toBe(9);
  });

  it("反例：开头被少数派把票耗空", () => {
    expect(solve([1, 9, 9, 9, 9])).toBe(9);
  });

  it("反例：多数元素票数远超 n/2（中途 cnt 归零后重新站起来）", () => {
    expect(solve([5, 5, 5, 5, 1, 2, 3])).toBe(5);
  });

  it("反例：票被耗空后换人却不重置 cnt 的写法（[1,2,2,2,1] → 会误答 1）", () => {
    expect(solve([1, 2, 2, 2, 1])).toBe(2);
  });
});