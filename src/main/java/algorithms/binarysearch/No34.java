package algorithms.binarysearch;

import java.util.Arrays;

/**
 * 34. 在排序数组中查找元素的第一个和最后一个位置
 *
 * @author Sean Yu
 */
public class No34 {
    public static void main(String[] args) {
        /**
         * 输入：nums = [5,7,7,8,8,10], target = 8
         * 输出：[3,4]
         */
//        int[] nums = new int[]{5, 7, 7, 8, 8, 10};
//        int target = 8;
        int[] nums = new int[]{1};
        int target = 1;
        System.out.println(Arrays.toString(new Solution34().searchRange(nums, target)));
    }
}

class Solution34 {
    int[] arr;
    int t;

    public int[] searchRange(int[] nums, int target) {
        arr = nums;
        t = target;
        return new int[]{findFirst(), findLast()};
    }

    private int findFirst() {
        int l = 0, r = arr.length - 1;
        while (l <= r) {
            int m = l + (r - l) / 2;
            int cur = arr[m];
            if (cur > t) {
                r = m - 1;
            } else if (cur < t) {
                l = m + 1;
            } else {
                if (m == 0 || m > 0 && arr[m - 1] < t) {
                    return m;
                } else {
                    r = m - 1;
                }
            }
        }
        return -1;
    }

    private int findLast() {
        int l = 0, r = arr.length - 1;
        while (l <= r) {
            int m = l + (r - l) / 2;
            int cur = arr[m];
            if (cur > t) {
                r = m - 1;
            } else if (cur < t) {
                l = m + 1;
            } else {
                if (m == arr.length - 1 || m + 1 < arr.length && arr[m + 1] > t) {
                    return m;
                } else {
                    l = m + 1;
                }
            }
        }
        return -1;
    }
}


