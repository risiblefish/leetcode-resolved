package algorithms.dualpointer;

/**
 * 80. 删除有序数组中的重复项 II
 * @Author: Sean Yu
 * @Date: 2021/4/6 21:39
 */
public class No80 {
    public static void main(String[] args) {
        System.out.println(new Solution80().removeDuplicates(new int[]{0, 0, 1, 1, 1, 1, 2, 3, 3}));
    }
}

/**
 * 思路： 快慢指针
 * <p>
 * 对于长度小于等于2的数组而言，无需校验，直接返回。
 * <p>
 * slow表示待填的数的下标
 * fast表示当前遍历到的数的下标
 * <p>
 * 对于待填的slow处而言，只有2种情况，
 * 要么 它的上上个数 和 fast 相同 ： 这意味着重复超过了2次，所以只移动快指针
 * 要么 它的上上个数 和 fast 不同 ： 这意味着找到了新的数，将slow处赋值后，将2个指针均往后移动一格
 */
class Solution80 {
    public int removeDuplicates(int[] nums) {
        int len = nums.length;
        if (len <= 2) {
            return len;
        }
        int slow = 2, fast = 2;
        while (fast < len) {
            //如果上上一个和当前fast指向的一样，则重复超过了2次（即nums[slow] == nums[slow-1] == nums[slow-2]）
            //这种情况，待填的slow不变，继续移动fast
            if (nums[slow - 2] == nums[fast]) {
                fast++;
            }
            //nums[slow-2] != nums[fast],
            else {
                nums[slow] = nums[fast];
                slow++;
                fast++;
            }
        }
        return slow;
    }
}
