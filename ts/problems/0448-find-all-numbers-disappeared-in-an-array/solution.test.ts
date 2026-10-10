import { describe, expect, it } from "vitest";
import solve from "./solution";

// 注意：本题解法会原地修改传入的数组（占座标记），
// 所以每个用例都用新字面量，不要在用例之间复用同一个数组变量。

describe("0448 找到所有数组中消失的数字 / Find All Numbers Disappeared in an Array", () => {
  it("示例 1", () => {
    expect(solve([4, 3, 2, 7, 8, 2, 3, 1])).toEqual([5, 6]);
  });

  it("示例 2", () => {
    expect(solve([1, 1])).toEqual([2]);
  });

  it("边界：没有任何缺失", () => {
    expect(solve([1, 2, 3])).toEqual([]);
  });

  it("边界：只有一个元素且它就在值域内", () => {
    expect(solve([1])).toEqual([]);
  });

  it("反例：全重复，座位一个没坐", () => {
    expect(solve([2, 2])).toEqual([1]);
  });

  it("反例：同一个座位被标记 3 次（验证 -Math.abs 的幂等性）", () => {
    expect(solve([1, 1, 1])).toEqual([2, 3]);
  });

  it("反例：重复元素出现在后面时，abs 必须加（否则 idx 变负、越界）", () => {
    expect(solve([3, 3, 3])).toEqual([1, 2]);
  });

  it("反例：最小的「漏 abs」翻车例 —— [2,1] 该是空数组", () => {
    expect(solve([2, 1])).toEqual([]);
  });
});