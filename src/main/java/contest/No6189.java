package contest;

/**
 * @author Sean Yu
 */
public class No6189 {
    public static void main(String[] args) {
        int[] nums = new int[]{1,2,3,3,2,2};
        System.out.println(new Solution6189().longestSubarray(nums));
    }
}

class Solution6189 {
    public int longestSubarray(int[] nums) {
        int[] dp = new int[nums.length];
        dp[0] = 1;
        int ans = 1;
        for(int i = 1 ; i < nums.length ; i++){
            int cur = nums[i-1] & nums[i];
            dp[i] = cur >= nums[i] ? dp[i-1] + 1 : 1;
            ans = Math.max(ans, dp[i]);
        }
        return ans;
    }
}
