package algorithms.dualpointer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 1. 两数之和
 */
public class No1 {
    public static void main(String[] args) {
        int[] arr = {3, 2, 4};
        int target = 6;
        System.out.println(Arrays.toString(new Solution1().twoSum(arr, target)));
    }
}

/**
 * 思路： 不断将nums[i]插入map，同时不断检索target[i]- nums[i]是否已在map中
 */
class Solution1 {
    public int[] twoSum(int[] nums, int target) {
        int[] res = new int[2];
        Map<Integer, Integer> map = new HashMap();
        for (int i = 0; i < nums.length; i++) {
            Integer index = map.get(target - nums[i]);
            if (index != null && index != i) {
                res[0] = i;
                res[1] = map.get(target - nums[i]);
                return res;
            }
            map.put(nums[i],i);
        }
        return res;
    }
}