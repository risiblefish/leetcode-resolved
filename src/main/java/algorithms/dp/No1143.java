package algorithms.dp;

/**
 * No.1143 1143. 最长公共子序列
 *
 * @author Sean Yu
 */
public class No1143 {
}

class Solution1143 {
    public int longestCommonSubsequence(String text1, String text2) {
        //dp[i][j]表示t1前i个和t2前j个的最长子序列长度
        //有dp[i][0] = 0  dp[0][j] = 0
        //可能性1: 当 t1[i-1] != t2[j-1], dp[i][j] = max(dp[i-1][j], dp[i][j-1]);
        //可能性2: 当 t1[i-1] == t2[j-1], dp[i][j] = dp[i-1][j-1] + 1
        int len1 = text1.length();
        int len2 = text2.length();
        int[][] dp = new int[len1 + 1][len2 + 1];
        for (int i = 0; i <= len1; i++) {
            dp[i][0] = 0;
        }
        for (int j = 0; j <= len2; j++) {
            dp[0][j] = 0;
        }
        for (int i = 1; i <= len1; i++) {
            for (int j = 1; j <= len2; j++) {
                if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        return dp[len1][len2];
    }
}
