import { describe, expect, it } from "vitest";
import solve from "./solution";

describe("0242 有效的字母异位词 / Valid Anagram", () => {
  it("示例 1", () => {
    expect(solve("anagram", "nagaram")).toBe(true);
  });

  it("示例 2", () => {
    expect(solve("rat", "car")).toBe(false);
  });

  it("边界：完全相同", () => {
    expect(solve("aa", "aa")).toBe(true);
  });

  it("边界：都是空串（两边表都空 → 也算异位词）", () => {
    expect(solve("", "")).toBe(true);
  });

  it("反例：s 比 t 长（s 有字符在表里找不到，必须立刻 false）", () => {
    expect(solve("ab", "a")).toBe(false);
  });

  it("反例：s 比 t 短（s 抵消完，表里还剩字符 → size 不为 0）", () => {
    expect(solve("a", "ab")).toBe(false);
  });

  it("反例：字符种类一样但个数不一样（只比集合会错）", () => {
    expect(solve("aab", "abb")).toBe(false);
  });

  it("反例：`aacc` vs `ccac`（s 想要两个 a，但 t 里只有一个 → 第二次拿 a 就该在表里找不到）", () => {
    expect(solve("aacc", "ccac")).toBe(false);
  });

  it("反例：`aabb` vs `bbaa`（计数必须真的减下去）", () => {
    expect(solve("aabb", "bbaa")).toBe(true);
  });

  it("反例：`aaab` vs `baaa`（第一次减到 2 时不写回，最后就会误判 false）", () => {
    expect(solve("aaab", "baaa")).toBe(true);
  });
});
