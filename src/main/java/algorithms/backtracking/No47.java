package algorithms.backtracking;

import java.util.*;

/**
 * 47. 全排列II
 * @author Sean Yu
 */
public class No47 {
    public static void main(String[] args) {
        int[] nums = new int[]{1, 1, 2};
        System.out.println(new Solution47_I().permuteUnique(nums));
    }
}

/**
 * 思路1： 求出全排列，在最后加入结果之前，进行去重
 * 去重的思路就是将每个序列转换成字符串
 */
class Solution47_I {
    List<List<Integer>> ans;
    List<Integer> cur;
    int[] arr;
    Set<String> set;

    public List<List<Integer>> permuteUnique(int[] nums) {
        ans = new ArrayList();
        arr = nums;
        cur = new ArrayList();
        set = new HashSet();
        search(0);
        return ans;
    }

    private void search(int curLen) {
        if (curLen == arr.length) {
            if (set.add(build(cur))) {
                ans.add(new ArrayList(cur));
            }
        }
        for (int i = 0; i < arr.length; i++) {
            if (cur.contains(arr[i])) {
                continue;
            }
            cur.add(arr[i]);
            search(curLen + 1);
            cur.remove(cur.size() - 1);
        }
    }

    private String build(List<Integer> list) {
        String s = "";
        for (int i : list) {
            s += i;
        }
        return s;
    }
}

/**
 * 思路2： 在搜索过程中剪枝
 *
 * 主要理解剪枝2
 * 举个实际的例子，比如[1，1'，2]
 * 因为共3个数，所以脑海中构造一个分叉数为3，且深度为3的树
 *
 *                        /                    |                     \
 *                       1                     1'                      2
 *                /     |    \            /     |    \             /    |      \
 *              1x      1'     2         1     1'     2         1      1'       2
 *          /  |  \   / | \   / | \   / | \  / | \  / | \    / | \   / | \    / | \
 *        1   1'  2  1  1'2  1  1'2  1 1' 2 1  1'2 1  1'2   1  1'2  1  1'2   1  1'2
 *
 *  剪枝1： 位于同一下标的元素不能重复使用
 *
 *                          /                       |                                \
 *                          1                       1'                                2
 *                  /     |      \            /     |       \                  /        |         \
 *                1x      1'       2         1     1'x          2               1          1'       2x
 *                     / |  \    / | \     / | \             /  |  \          /  | \     / |  \
 *                    1  1'x 2  1x  1'2x  1x 1'x           2x  1  1'2      1x  1' 2x  1  1'x 2x
 *
 *  得到：
 *                            /                     |                  \
 *                            1                    1' (x)                2
 *                         |      \             /        \             /    \
 *  *                       1'       2         1           2         1      1'
 *                           \     |           \           /          /      /
 *                            2    1'          2          1          1'     1
 *
 *  剪枝2：
 *  可以看到在剪枝1以后， 112 121分别出现了2次, 均由(x)处开始产生
 *  a -- (x）处是1'，它和1虽然在数组中的下标不同，但值相同， 我们希望在(x)处就终止流程
 *  b -- 在11'2和21'1或者211'时，出现了下标不同但值相同的元素，但流程却能继续
 *  如何来区分a,b两种情况呢？
 *  我们可以让原数组排序，变成1，1'，2
 *  当遍历到1'时，前一个数也是1，如过前一个1加入到了当前序列中，我们就继续，如果前一个1不在当前序列中，说明这个1才进行了cur.remove()和used[]=false操作，所以1'就属于重复操作，我们就要进行剪枝
 */
class Solution47_II {
    List<List<Integer>> ans;
    boolean[] used;
    List<Integer> cur;
    int[] nums;

    public List<List<Integer>> permuteUnique(int[] nums) {
        this.nums = nums;
        ans = new ArrayList();
        used = new boolean[nums.length];
        cur = new ArrayList();
        Arrays.sort(nums);
        search();
        return ans;
    }

    private void search() {
        if (cur.size() == nums.length) {
            ans.add(new ArrayList(cur));
        } else {
            for (int i = 0; i < nums.length; i++) {
                //剪枝1
                if (used[i]) {
                    continue;
                }
                //剪枝2
                    //防止越界
                if (i - 1 >= 0
                        // 当前加入和数和前一个数一样，并且前一个数没用过，这表示前面一个数的情况才考虑完，才进行了撤销
                        && nums[i] == nums[i - 1] && !used[i - 1]) {
                    continue;
                }
                cur.add(nums[i]);
                used[i] = true;
                search();
                cur.remove(cur.size() - 1);
                used[i] = false;
            }
        }
    }
}


