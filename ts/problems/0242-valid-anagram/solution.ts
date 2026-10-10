/**
 * LeetCode 0242. 有效的字母异位词 / Valid Anagram
 * https://leetcode.cn/problems/valid-anagram/
 *
 * 思路：一张计数表打天下（与 #169 同族）。
 *   先拿 t 建计数表（+1），再用 s 去抵消（-1）；某位抵消到 0 就把键删掉。
 *   最后表如果空了，说明两边字符同量同种类。
 *
 * 钩子：计数表 —— 一边 +1、一边 -1，抵消完看表空不空。
 *
 * 复杂度：时间 O(n + m) / 空间 O(k)（k = 字符种类数；小写字母时是常数 26）
 */

function isAnagram(s: string, t: string): boolean {
  const map = new Map<string, number>();
  for (let i = 0; i < t.length; i++) {
    let cnt = map.get(t.charAt(i));
    if (cnt === undefined) cnt = 0;
    map.set(t.charAt(i), cnt + 1);
  }
  for (let i = 0; i < s.length; i++) {
    let cnt = map.get(s.charAt(i));
    if (cnt === undefined) return false;
    cnt--;
    if (cnt === 0) {
      map.delete(s.charAt(i));
    } else {
      map.set(s.charAt(i), cnt);
    }
  }
  return map.size === 0;
}

export default isAnagram;
export { isAnagram };
