package algorithms;

import java.util.ArrayList;
import java.util.List;

/**
 * 39. 组合总和
 * @author Sean Yu
 */
public class No39 {
    public static void main(String[] args) {
        System.out.println(new Solution39().combinationSum(new int[]{2, 3, 6, 7}, 7));
    }
}

/**
 * 思路： 回溯
 */
class Solution39 {
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> res = new ArrayList<>();
        List<Integer> list = new ArrayList<>();
        dfs(candidates, target, res, list, 0);
        return res;
    }

    private void dfs(int[] candidates, int target, List<List<Integer>> res, List<Integer> list, int start) {
        if (target == 0) {
            res.add(new ArrayList<>(list));
            return;
        }
        for (int idx = start; idx < candidates.length; idx++) {
            if (target - candidates[idx] >= 0) {
                list.add(candidates[idx]);
                dfs(candidates, target - candidates[idx], res, list, idx);
                list.remove(list.size() - 1);
            }
        }
    }
}
