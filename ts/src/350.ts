// WIP · Session 2 延伸裸写，待闭卷重写 + 走 scaffold
// 状态：LeetCode AC；但未入 ts/problems/ 流程，未纳入复习循环
// 待办：哈希计数 O(n+m)；或直接保留排序双指针（题目进阶解）
// 详见 OVERVIEW.md 「当前阻塞点」

function intersect(nums1: number[], nums2: number[]): number[] {
    let shorter = undefined, longer = undefined;
    if (nums1.length >= nums2.length) {
        longer = [...nums1].sort((a, b) => a - b);
        shorter = [...nums2].sort((a, b) => a - b);
    } else {
        longer = [...nums2].sort((a, b) => a - b);
        shorter = [...nums1].sort((a, b) => a - b);
    }
    let ans: number[] = [];
    let s = 0, l = 0;
    while (s < shorter.length && l < longer.length) {
        if (shorter[s] < longer[l]) {
            s++;
        } else if (shorter[s] === longer[l]) {
            ans.push(shorter[s]);
            s++;
            l++;
        } else {
            l++;
        }
    }
    return ans;
};