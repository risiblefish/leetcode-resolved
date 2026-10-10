// WIP · Session 2 延伸裸写，待闭卷重写 + 走 scaffold
// 状态：LeetCode AC；但未入 ts/problems/ 流程，未纳入复习循环
// 待办：Boyer-Moore 投票法（见 pattern-array-hash.md 番外 B）
// 详见 OVERVIEW.md 「当前阻塞点」

function majorityElement(nums: number[]): number {
    const seen = new Map<number, number>();
    for (const val of nums) {
        let cnt = seen.get(val);
        if (cnt === undefined) cnt = 0;
        cnt++;
        if (cnt > nums.length / 2) return val;
        seen.set(val, cnt);
    }
    return -1;
};