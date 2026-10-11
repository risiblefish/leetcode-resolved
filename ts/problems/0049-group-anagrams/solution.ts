/**
 * LeetCode 0049. 字母异位词分组 / Group Anagrams
 * https://leetcode.cn/problems/group-anagrams/
 *
 * 思路（闭卷手写版）：哈希分组 —— 「异位词排序后长得一样」，把排序后的字符串当 key。
 *   1. 每个串拆成字符数组、排序、拼回字符串 → key
 *   2. key 已有桶就往里塞，没有就先建空桶（`?? []` + set 回去，见 PF-0004）
 *   3. 表的 values 就是答案
 *
 * 钩子：**异位词排序后长得一样** —— 排序后的串当 key，桶里装原串。
 *
 * 复杂度：时间 O(n · m log m)（n 个串，每个长 m，排序 m log m）/ 空间 O(n · m)
 */

function groupAnagrams(strs: string[]): string[][] {
  const groups = new Map<string, string[]>();

  for (const str of strs) {
    const key = [...str].sort().join("");
    const bucket = groups.get(key) ?? []; // 没有桶 → 现场建一个（读到啥用啥，不靠 ! 断言）
    bucket.push(str);
    groups.set(key, bucket);
  }

  return [...groups.values()];
}

export default groupAnagrams;
export { groupAnagrams };