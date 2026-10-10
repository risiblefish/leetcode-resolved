// WIP · Session 2 延伸裸写，待闭卷重写 + 走 scaffold
// 状态：LeetCode AC；但未入 ts/problems/ 流程，未纳入复习循环
// 待办：先判长度短路；s[i] 替 charAt
// 详见 OVERVIEW.md 「当前阻塞点」

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
};