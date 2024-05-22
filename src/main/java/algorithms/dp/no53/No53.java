package algorithms.dp.no53;

/**
 * 53. 最大子数组和
 * @author Sean Yu
 */
public class No53 {
    public static void main(String[] args) {
    }
}

/**
 * 思路：
 * 令dp[i] = 必定包含nums[i]的最大和，值 = max{nums[i], nums[i]+dp[i-1]}
 * 由于每个dp[i]只和dp[i-1]相关，所以把dp[i-1]简化为一个变量pre
 */
class Solution53 {
    public int maxSubArray(int[] nums) {
        int pre = nums[0];
        int ans = pre;
        for(int i = 1 ; i < nums.length ; i++){
            int cur = nums[i];
            if(pre >= 0){
                cur += pre;
            }
            ans = Math.max(ans, cur);
            pre = cur;
        }
        return ans;
    }
}