package algorithms.backtracking;

import java.util.ArrayList;
import java.util.List;

/**
 * 思路：
 * 递归搜索
 * 要找到结果，每个位置上的数只有2种选择，要or不要
 *
 * @author Sean Yu
 */
public class No78 {
    public static void main(String[] args) {

    }
}

class Solution78 {
    List<List<Integer>> ans;
    int[] nums;

    public List<List<Integer>> subsets(int[] nums) {
        //init
        this.nums = nums;
        ans = new ArrayList();
        //search
        search(new ArrayList(), 0);
        return ans;
    }

    private void search(List<Integer> list, int curIdx) {
        if (curIdx == nums.length) {
            ans.add(new ArrayList(list));
            return;
        }
        list.add(nums[curIdx]);
        search(list, curIdx + 1);
        list.remove(list.size() - 1);
        search(list, curIdx + 1);
    }
}
