package algorithms.backtracking;

import java.util.ArrayList;
import java.util.List;

/**
 * 46. 全排列
 * @author Sean Yu
 */
public class No46 {
    public static void main(String[] args) {
        System.out.println(new Solution46().permute(new int[]{1, 2, 3}));
    }
}


/**
 * 思路： 回溯
 * times指的是寻找的次数，全排列n个数 表示 一共要寻找n次
 * 脑子里可以先开始画一颗n叉树，
 * 比如求[1,2,3]的全排列
 * 从根节点开始，分别有1，2，3个子节点，对于子节点1，继续画3个分叉，1（由于已经记录过了，所以不再记录），2，3，对于子节点2，3，可依次类推
 * 每次向下分叉时，记录一次当前深度，当深度为n时，就返回。然后删掉最后添加的结果，继续向右查找。
 * 所以回溯本质上是一个横向+纵向的遍历，回溯这个动作，就是返回上一层，然后向右遍历一个，再继续向下遍历 的体现。
 */
class Solution46 {
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        List<Integer> list = new ArrayList<>();
        backTracking(res, list, 0, nums);
        return res;
    }

    /**
     * 回溯的通用模板：
     * （1）先确定出口条件，将结果保存，然后返回
     * （2）剪枝：如果当前遍历的数，已经记录了，就跳过
     * @param res
     * @param list
     * @param times
     * @param nums
     */
    private void backTracking(List<List<Integer>> res, List<Integer> list, int times, int[] nums) {
        if (times == nums.length) {
            res.add(new ArrayList<>(list));
            return;
        }
        int len = nums.length;
        for (int i = 0; i < len; i++) {
            if (list.contains(nums[i])) {
                continue;
            }
            list.add(nums[i]);
            backTracking(res, list, times + 1, nums);
            list.remove(list.size() - 1);
        }
    }
}