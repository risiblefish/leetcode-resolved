package algorithms.dualpointer;

/**
 * 33. 搜索旋转排序数组
 * @author Sean Yu
 */
public class No33 {
}

class Solution33 {
    public int search(int[] nums, int target) {
        int l = 0, r = nums.length - 1;
        while (l <= r) {
            int m = l + (r - l) / 2;
            if (nums[m] == target) {
                return m;
            }
            //此时，nums[m]一定不等于target
            //中点比右边大，说明中点到右侧一定存在波谷，说明左侧到中点一定是有序的
            if (nums[m] > nums[r]) {
                //如果target落在有序区间，即左区间
                if (nums[l] <= target && target < nums[m]) {
                    r = m - 1;
                } else {
                    l = m + 1;
                }
            }
            //否则，即nums[m] < nums[r], 中点到右侧一定是有序的
            //不难证明，如果m,r之间有波谷，而r又>m， 那么势必从0 ~ r会再次产生1个m，而题目保证每个数只出现1次
            else {
                //如果target落在有序区间，即右区间
                if (nums[m] < target && target <= nums[r]) {
                    l = m + 1;
                } else {
                    r = m - 1;
                }
            }
        }
        return -1;
    }
}
