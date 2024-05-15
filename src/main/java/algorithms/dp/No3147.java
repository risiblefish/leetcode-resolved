package algorithms.dp;

/**
 * 3147. 从魔法师身上吸取的最大能量
 *
 * @author Sean Yu
 * @since 2024/5/15 14:03
 */

public class No3147 {
}

class Solution3147 {
    public int maximumEnergy(int[] energy, int k) {
        //dp[i] 表示以i处结尾的最大能量 = max{ e[i],  e[i] + dp[i-k] }
        int len = energy.length;
        int[] dp = new int[len];
        int ans = Integer.MIN_VALUE;
        for (int i = 0; i < len; i++) {
            dp[i] = energy[i];
            if (i - k >= 0) {
                if (dp[i - k] > 0) dp[i] += dp[i - k];
            }
            //并非所有位置都能作为结尾
            if (i + k >= len) {
                //legal end
                ans = Math.max(ans, dp[i]);
            }
        }
        return ans;
    }
}
