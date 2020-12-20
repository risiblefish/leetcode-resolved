package algorithms.dp;

/**
 * No.213 打家劫舍II
 *
 * @author Sean Yu
 */
public class No213 {
}


/**
 * 1. 分析最后1步
 * 只要不为空，那么最优解中一定包含最后一个nums[i]
 * 要求到第i个数的最大和，那么这个和要么是nums[i]+前i-2个数的最大和，要么是前i-1个数的最大和
 *
 *
 * 2. 确定状态转移方程
 * 由于首尾连续，所以这里要把数组分成2个子数组来求解，[0，n-2]和[1,n-1]
 *
 * 令dp(i)表示长度为i的数组，则dp(i) = max{ nums[i]+algorithms.dp(i-2), algorithms.dp(i-1)}
 *
 * 3. 确定边界条件和初始情况
 * algorithms.dp(0) = 0
 *
 * 4. 计算顺序
 * 由小到大
 */
class Solution213 {
    public int rob(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        if (nums.length == 1) {
            return nums[0];
        }

        int n = nums.length;
        return Math.max(rangeRob(nums, 0, n - 2), rangeRob(nums, 1, n - 1));
    }

    /**
     * 求下标start到下标end的最大和
     * 用滚动数组思想来节省空间
     * @param nums
     * @param start
     * @param end
     * @return
     */
    private int rangeRob(int[] nums, int start, int end) {
        int pre1 = 0;
        int pre2 = 0;
        int curr = 0;
        for (int i = start; i <= end; i++) {
            curr = Math.max(pre1, nums[i] + pre2);
            pre2 = pre1;
            pre1 = curr;
        }
        return curr;
    }
}
