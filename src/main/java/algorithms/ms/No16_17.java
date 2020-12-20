package algorithms.ms;

/**
 * @author Sean Yu
 */
public class No16_17 {
}

/**
 * 1.最后一步
 * 若数组为a，长度为n， 若最大连续和存在，则必然包含a[i]
 * 如果a[i-1]存在，且a[i-1] <= 0， 则这个数列就是a[i]
 * 如果a[i-1]存在，且a[i-1] > 0， 则这个数列必然也包含a[i-1]
 *
 * 2.确定状态转移方程
 * 令dp[i] 表示包 含a[i]的 最大连续和，则最后的解就是众多dp[i]里最大的那个
 * algorithms.dp[i]满足：
 * 如果dp[i-1] > 0， algorithms.dp[i] = algorithms.dp[i-1] + a[i]
 * 如果dp[i-1] < 0,  algorithms.dp[i] = a[i]
 *
 * 3.确定初始条件和边界情况
 * algorithms.dp[0] = a[0]
 *
 * 4.确定计算顺序
 * 有小到大
 */
class Solution16_17 {
    public int maxSubArray(int[] nums) {
        if(nums == null || nums.length == 0) {
            return 0;
        }
        int n = nums.length;
        int[] dp = new int[n];
        dp[0] = nums[0];
        int temp = dp[0];
        for(int i = 1 ; i < n ; i++) {
            dp[i] = nums[i];
            if(dp[i-1] > 0) {
                dp[i] += dp[i-1];
            }
            temp = Math.max(temp,dp[i]);
        }
        return temp;
    }
}
