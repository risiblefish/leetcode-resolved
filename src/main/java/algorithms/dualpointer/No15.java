package algorithms.dualpointer;

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
 *
 * 时间复杂度：O(n^2)
 */
class Solution15 {
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> res = new ArrayList();
        if (nums == null || nums.length < 3) {
            return res;
        }
        Arrays.sort(nums);
        //排序后，从左到右遍历每个数，对于第i个数，从它的右边开始双指针夹逼，找到nums[l] + nums[r] = - nums[i]
        for (int i = 0; i < nums.length; i++) {
            //剪枝：如果nums[i]已经大于0，它右边的nums[l],nums[r]必然均大于nums[i]，所以和必然大于-nums[i]
            if (nums[i] > 0) {
                break;
            }
            //去重
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            int l = i + 1, r = nums.length - 1;
            while (l < r) {
                int sum = nums[l] + nums[r];
                if (sum < -nums[i]) {
                    l++;
                } else if (sum > -nums[i]) {
                    r--;
                } else {
                    List<Integer> list = new ArrayList();
                    list.add(nums[i]);
                    list.add(nums[l]);
                    list.add(nums[r]);
                    res.add(list);
                    l++;
                    r--;
                    //去重
                    while (l < r && nums[l] == nums[l - 1]) {
                        l++;
                    }
                    //去重
                    while (l < r && nums[r] == nums[r + 1]) {
                        r--;
                    }
                }
            }
        }
        return res;
    }
}
