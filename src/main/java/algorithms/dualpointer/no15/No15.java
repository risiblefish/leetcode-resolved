package algorithms.dualpointer.no15;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 30. 三数之和
 *
 * @author Sean Yu
 */
public class No15 {
}


/**
 * 思路: 双指针
 * 难点是去重，去重需要做2件事：排序 + 相同点跳过
 * <p>
 * 时间复杂度：O(n^2)
 */
class Solution15 {
    public List<List<Integer>> threeSum(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> ans = new ArrayList();
        for (int a = 0; a < nums.length; a++) {
            //去重
            if (a > 0 && nums[a] == nums[a - 1]) {
                continue;
            }
            //剪枝：排序后，a <= b <= c, 又有 a + b + c = 0,  如果a已经>0， 那b+c必然也大于0，就可以停止遍历了
            if (nums[a] > 0) {
                break;
            }
            int l = a + 1, r = nums.length - 1;
            int target = -nums[a];
            while (l < r) {
                int sum = nums[l] + nums[r];
                if (sum == target) {
                    //找到一个解之后，需要继续l++, r--， 比如 -5 0 1 2 3 4, 1+4是解, 2+3也是解
                    ans.add(Arrays.asList(nums[a], nums[l++], nums[r--]));
                    //去重
                    while (l < r && nums[l] == nums[l - 1]) {
                        l++;
                    }
                    //去重
                    while (r > l && nums[r] == nums[r + 1]) {
                        r--;
                    }
                } else if (sum < target) {
                    l++;
                } else {
                    r--;
                }
            }
        }
        return ans;
    }
}
