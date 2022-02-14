package algorithms.dualpointer;

/**
 * 540. 有序数组中的单一元素
 */
public class No540 {
}

/**
 * 思路：
 *
 * 如果每个数都是成对出现，那么有 每相邻的偶数位和奇数位上的数一定相同，可以利用这个特性进行二分
 * 比如
 * 下标：01 23 45
 * 值： 11 22 33
 *
 * 正常情况下
 * 如果下标最长为5， 那么mid = 5 / 2 = 2 为偶数，那么相邻的奇数位为 mid + 1
 * 如果下标最长为4， 0 1 2 3 4 5 6 那么mid = 6 / 2 = 3 为奇数，那么相邻的偶数位为 mid - 1
 *
 * 如果偶数位的数 == 奇数位的数， 那么单身狗就在mid后半段，即包括 0,1,2,,,mid都是成对出现，mid+1,mid+2,....r里存在单身狗 所以 l = mid + 1
 * 如果偶数位的数 != 奇数位的数， 那么单身狗就在mid前半段, 考虑到这种情况，更新r的时候，就不能用r = mid - 1， 而是要用r = mid
 *
 */
class Solution540 {
    public int singleNonDuplicate(int[] nums) {
        int l = 0, r = nums.length - 1;
        while (l < r) {
            int mid = l + (r - l) / 2;
            int odd, even;
            if (mid % 2 == 0) {
                odd = nums[mid + 1];
                even = nums[mid];
            } else {
                odd = nums[mid];
                even = nums[mid - 1];
            }
            if (odd == even) {
                l = mid + 1;
            } else {
                r = mid;
            }
        }
        return nums[r];
    }
}
