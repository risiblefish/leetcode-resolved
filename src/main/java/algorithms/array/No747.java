package algorithms.array;

/**
 * 747. 至少是其他数字两倍的最大数
 * @author Sean Yu
 */
public class No747 {
    public static void main(String[] args) {
        System.out.println(new Solution747().dominantIndex(new int[]{1, 2, 3, 4}));
    }
}

/**
 * 思路： 遍历1次，找到最大的和第2大的数，如果最大的数是第2大的数的2倍及以上，那么就满足条件
 */
class Solution747 {
    public int dominantIndex(int[] nums) {
        if (nums.length == 1) {
            return 0;
        }
        int first = 0;
        int second = 0;
        if (nums[0] >= nums[1]) {
            first = 0;
            second = 1;
        } else {
            first = 1;
            second = 0;
        }
        for (int i = 2; i < nums.length; i++) {
            if (nums[i] <= nums[second]) {
                continue;
            } else {
                //nums[i] > nums[second]
                if (nums[i] > nums[first]) {
                    second = first;
                    first = i;
                } else if (nums[i] > nums[second]) {
                    second = i;
                }
            }
        }
        return nums[first] >= nums[second] * 2 ? first : -1;
    }
}
