// WIP · Session 2 延伸裸写，待闭卷重写 + 走 scaffold
// 状态：LeetCode AC；但未入 ts/problems/ 流程，未纳入复习循环
// 待办：XOR 异或法（见 pattern-array-hash.md 番外 A）
// 详见 OVERVIEW.md 「当前阻塞点」

function singleNumber(nums: number[]): number {
    if(nums.length < 2) return nums[0];
    const sorted = [...nums].sort((a,b) => a-b);
    if(sorted[0] < sorted[1]) return sorted[0];
    for(let i = 1; i < sorted.length - 1 ; i++){
        if(sorted[i] > sorted[i-1] && sorted[i] < sorted[i+1]) return sorted[i];
    }
    return sorted[nums.length - 1];
};
