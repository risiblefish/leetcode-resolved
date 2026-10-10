import { describe, expect, it } from "vitest";
import solve from "./solution";

describe("0121 买卖股票的最佳时机 / Best Time to Buy and Sell Stock", () => {
  it("示例 1", () => {
    expect(solve([7, 1, 5, 3, 6, 4])).toBe(5);
  });

  it("示例 2", () => {
    expect(solve([7, 6, 4, 3, 1])).toBe(0);
  });

  it("边界：只有一个价格（不能交易 → 0）", () => {
    expect(solve([5])).toBe(0);
  });

  it("边界：一涨一跌的最小两天", () => {
    expect(solve([1, 2])).toBe(1);
    expect(solve([2, 1])).toBe(0);
  });

  it("反例：最低点在最后（买了没机会卖）", () => {
    expect(solve([2, 4, 1])).toBe(2);
  });

  it("反例：先暴跌再反弹，答案在靠后位置", () => {
    expect(solve([3, 3, 5, 0, 0, 3, 1, 4])).toBe(4);
  });

  it("反例：不断新高（全程持有最赚）", () => {
    expect(solve([1, 2, 3, 4, 5])).toBe(4);
  });
});