// WIP · Session 2 延伸裸写，待闭卷重写 + 走 scaffold
// 状态：LeetCode AC；但未入 ts/problems/ 流程，未纳入复习循环
// 待办：Set O(n) 一遍过
// 详见 OVERVIEW.md 「当前阻塞点」

function containsDuplicate(nums: number[]): boolean {
    const sorted = [...nums].sort((a, b) => a - b);
    for (let i = 0; i < sorted.length - 1; i++) {
        if (sorted[i] == sorted[i + 1]) return true;
    }
    return false;
};