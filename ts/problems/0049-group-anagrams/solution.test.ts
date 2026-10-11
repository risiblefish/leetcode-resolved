import { describe, expect, it } from "vitest";
import solve from "./solution";

/** 题目只要求「组内顺序 / 组间顺序任意」，比较前把组内、组间都排好 */
const norm = (groups: string[][]) =>
  groups.map((g) => [...g].sort()).sort((a, b) => a[0].localeCompare(b[0]));

describe("0049 字母异位词分组 / Group Anagrams", () => {
  it("示例 1", () => {
    expect(norm(solve(["eat", "tea", "tan", "ate", "nat", "bat"]))).toEqual(
      norm([["bat"], ["nat", "tan"], ["ate", "eat", "tea"]]),
    );
  });

  it("示例 2：空字符串", () => {
    expect(solve([""])).toEqual([[""]]);
  });

  it("示例 3：单字符", () => {
    expect(solve(["a"])).toEqual([["a"]]);
  });

  it("边界：同一个串出现两次 → 同桶两个元素", () => {
    expect(norm(solve(["ab", "ab"]))).toEqual(norm([["ab", "ab"]]));
  });

  it("反例：异位词必须同组（长度相同才可能）", () => {
    expect(norm(solve(["ab", "ba"]))).toEqual(norm([["ab", "ba"]]));
    expect(norm(solve(["aab", "abb"]))).toEqual(norm([["aab"], ["abb"]]));
  });

  it("反例：互为前缀但不是异位词（key 写错就并到一起）", () => {
    expect(norm(solve(["ab", "abc"]))).toEqual(norm([["ab"], ["abc"]]));
  });

  it("边界：空输入", () => {
    expect(solve([])).toEqual([]);
  });
});