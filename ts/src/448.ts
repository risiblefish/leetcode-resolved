// WIP · Session 2 延伸裸写，待闭卷重写 + 走 scaffold
// 状态：LeetCode AC；但未入 ts/problems/ 流程，未纳入复习循环
// 待办：原地标记法 O(1) 空间（用 nums[|x|-1] 的符号当标记位）
// 详见 OVERVIEW.md 「当前阻塞点」

function findDisappearedNumbers(nums: number[]): number[] {
    const seen = new Set<number>();
    for (const val of nums) {
        seen.add(val);
    }
    const ans = [];
    for (let i = 1; i <= nums.length; i++) {
        if (!seen.has(i)) {
            ans.push(i);
        }
    }
    return ans;
};