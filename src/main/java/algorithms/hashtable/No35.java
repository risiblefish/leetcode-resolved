package algorithms.hashtable;

/**
 * 35. 搜索插入位置
 * @author Sean Yu
 */
public class No35 {
}


class Solution35 {
    public int searchInsertI(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        //定义l,r所在的区间 [0,len-1]
        int l = 0, r = nums.length - 1;
        while (l <= r) {
            int m = l + (r - l) / 2;
            //第1种情况： target存在，返回索引
            if (nums[m] == target) {
                return m;
            } else if (nums[m] < target) {
                l = m + 1;
            } else {
                r = m - 1;
            }
        }
        //其他3种特殊情况 - target不存在，返回插入位置：
        //target在数组最前面
        //target在数组中某2个数之间
        //target在数组最后面
        return r + 1;
    }

    public int searchInsertII(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        //定义l,r所在的区间 [0,len）
        int l = 0, r = nums.length;
        while (l < r) {
            int m = l + (r - l) / 2;
            //第1种情况： target存在，返回索引
            if (nums[m] == target) {
                return m;
            } else if (nums[m] < target) {
                l = m + 1;
            } else {
                r = m;
            }
        }
        //其他3种特殊情况 - target不存在，返回插入位置：
        //target在数组最前面
        //target在数组中某2个数之间
        //target在数组最后面
        return r;
    }
}
