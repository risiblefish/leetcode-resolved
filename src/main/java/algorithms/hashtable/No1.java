package algorithms.hashtable;

import java.util.HashMap;
import java.util.Map;

public class No1 {
    public static void main(String[] args) {
        System.out.println(new Solution1().twoSum(new int[]{3, 2, 4}, 6));
    }
}

/**
 * 思路： 利用map
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
            map.put(nums[i], i);
        }
        return res;
    }
}