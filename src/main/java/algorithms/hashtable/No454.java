package algorithms.hashtable;

import java.util.HashMap;
import java.util.Map;

/**
 * 454. 四数相加 II
 */
public class No454 {
}

/**
 * 思路： 利用哈希表存储 降低复杂度
 */
class Solution454 {
    public int fourSumCount(int[] nums1, int[] nums2, int[] nums3, int[] nums4) {
        Map<Integer, Integer> map = new HashMap();
        for (int i = 0; i < nums1.length; i++) {
            for (int j = 0; j < nums2.length; j++) {
                int sum = nums1[i] + nums2[j];
                map.put(sum, map.getOrDefault(sum, 0) + 1);
            }
        }
        int count = 0;
        for (int i = 0; i < nums3.length; i++) {
            for (int j = 0; j < nums4.length; j++) {
                int sum = -nums3[i] - nums4[j];
                count += map.getOrDefault(sum, 0);
            }
        }
        return count;
    }
}