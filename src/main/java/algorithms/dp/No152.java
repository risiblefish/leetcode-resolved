package algorithms.dp;

/**
 * 152. 乘积最大子数组
 * @author Sean Yu
 */
public class No152 {
    public static void main(String[] args) {
    }
}


/**
 * 思路： dp
 * 因为有负数，比如-2,3,-4这种，而负负得正，所以，可以维护2个值，以下标i结尾的最大值，和最小值
 */
class Solution152_I {
    public int maxProduct(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int len = nums.length;
        int[][] dp = new int[len][2];
        dp[0][0] = nums[0];
        dp[0][1] = nums[0];
        int res = dp[0][0];
        for (int i = 1; i < len; i++) {
            int res0 = nums[i] * dp[i - 1][0];
            int res1 = nums[i] * dp[i - 1][1];
            dp[i][0] = Math.max(Math.max(res0, res1), nums[i]);
            dp[i][1] = Math.min(Math.min(res0, res1), nums[i]);
            res = Math.max(dp[i][0], res);
        }
        return res;
    }
}

/**
 * 空间优化：
 * 因为第i个值，只取决于第i-1个值，且当前最大值可以通过遍历过程中更新得到
 * 故考虑滚动数组来优化空间
 */
class Solution152_II {
    public int maxProduct(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int len = nums.length;
        int res0 = nums[0], res1 = nums[0], result = nums[0];
        for (int i = 1; i < len; i++) {
            int newRes0 = nums[i] * res0;
            int newRes1 = nums[i] * res1;
            res0 = Math.max(Math.max(newRes0, newRes1), nums[i]);
            res1 = Math.min(Math.min(newRes0, newRes1), nums[i]);
            result = Math.max(result, res0);
        }
        return result;
    }
}
