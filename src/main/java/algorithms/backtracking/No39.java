package algorithms.backtracking;

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
 * 可以想象画一颗n叉树，n是candidates中元素的数量，,顶点是传入的target，每次取出一个candidate，target就会变小
 * 比如target是7，从candidates中取出2，target就变成7-2=5，同理，取出3，target就变成7-3=4
 * 对于5而言，它又是一个n叉的子树
 * 以此往复，进行纵向搜索，直到target为0，说明能刚好凑出，这时需要记录结果，然后撤销最后一步，进行横向遍历
 *
 * 剪枝： 当取出的candidate 比 target 大时，说明肯定不能凑到刚好为target，这种直接return
 */
class Solution39 {
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> res = new ArrayList<>();
        List<Integer> list = new ArrayList<>();
        backTrack(candidates, target, res, list, 0);
        return res;
    }

    /**
     * dfs
     * 其中，for循环是横向遍历，不断缩小的t是纵向遍历，在每一次横向遍历中进行纵向遍历
     * @param candidates
     * @param target
     * @param res
     * @param list
     * @param start
     */
    private void backTrack(int[] candidates, int target, List<List<Integer>> res, List<Integer> list, int start) {
        if (target == 0) {
            res.add(new ArrayList<>(list));
            return;
        }
        for (int idx = start; idx < candidates.length; idx++) {
            int t = target - candidates[idx];
            if (t >= 0) {
                list.add(candidates[idx]);
                backTrack(candidates, t, res, list, idx);
                list.remove(list.size() - 1);
            }
        }
    }
}
