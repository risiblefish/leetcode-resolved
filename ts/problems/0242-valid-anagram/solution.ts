/**
 * LeetCode 0242. 有效的字母异位词 / Valid Anagram
 * https://leetcode.cn/problems/valid-anagram/
 *
 * 思路：一张计数表打天下（与 #169 同族）。
 *   先拿 s 建计数表（+1），再用 t 去抵消（-1）；某位抵消到 0 就把键删掉。
 *   长度不等直接 `false`（最便宜的短路）。
 *
 * 钩子：计数表 —— 一边 +1、一边 -1，抵消完看表空不空。
 *       ⚠️ 抵消后**必须把新值写回表**（`Map.get` 拿到的是值的副本，改它不会改到表里）
 *
 * 复杂度：时间 O(n + m) / 空间 O(k)（k = 字符种类数；小写字母时是常数 26）
 */

function isAnagram(s: string, t: string): boolean {
  if (s.length !== t.length) return false;

  const cntMap = new Map<string, number>();
  for (let i = 0; i < s.length; i++) {
    const curChar = s.charAt(i);
    let cnt = 0;
    if (cntMap.has(curChar)) {
      cnt = cntMap.get(curChar)!;
    }
    cntMap.set(curChar, cnt + 1);
  }

  for (let i = 0; i < t.length; i++) {
    const curChar = t.charAt(i);
    if (cntMap.has(curChar)) {
      let cnt = cntMap.get(curChar)!;
      cnt--;
      if (cnt === 0) {
        cntMap.delete(curChar);
      } else {
        cntMap.set(curChar, cnt); // ← 没这一行，计数永远减不下去
      }
    } else {
      return false;
    }
  }

  return cntMap.size === 0;
}

export default isAnagram;
export { isAnagram };