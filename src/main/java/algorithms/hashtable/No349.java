package algorithms.hashtable;

import java.util.HashSet;
import java.util.Set;

/**
 * 349. 两个数组的交集
 */
public class No349 {
}

/**
 * 思路： 2个set
 */
class Solution349 {
    public int[] intersection(int[] nums1, int[] nums2) {
        Set<Integer> set = new HashSet();
        for (int i = 0; i < nums1.length; i++) {
            if (!set.contains(nums1[i])) {
                set.add(nums1[i]);
            }
        }
        Set<Integer> res = new HashSet();
        for (int i = 0; i < nums2.length; i++) {
            if (set.contains(nums2[i]) && !res.contains(nums2[i])) {
                res.add(nums2[i]);
            }
        }
        Object[] arr = res.toArray();
        int[] resArr = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            resArr[i] = (int) arr[i];
        }
        return resArr;
    }
}