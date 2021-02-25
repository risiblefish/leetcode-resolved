package algorithms.dp;

/**
 * @author Sean Yu
 */
public class No1641 {
    public static void main(String[] args) {
        System.out.println(new Solution1641().countVowelStrings(33));
    }
}


/**
 * dp[0][i]，dp[1][i], dp[2][i], dp[3][i], dp[4][i] 分别代表以a,e,i,o,u开头的长度为i的串的数量
 * <p>
 * 则dp[k][i] = dp[k][i-1] + dp[k+1][i-1] + ... + dp[4][i-1], 其中, k = 0,1,2,3,4
 * <p>
 * 最后的解就是dp[0][n] + dp[1][n] + dp[2][n] + dp[3][n] + dp[4][n] //即以a,e,i,o,u开头的长度为n的串的数量的总和
 */
class Solution1641 {
    public int countVowelStrings(int n) {
        int[][] dp = new int[5][n + 1];
        for (int k = 0; k <= 4; k++) {
            dp[k][1] = 1;
        }
        for (int k = 0; k <= 4; k++) {
            for (int i = 2; i <= n; i++) {
                dp[k][i] = sum(k, i - 1, dp);
            }
        }
        int res = 0;
        for (int k = 0; k <= 4; k++) {
            res += dp[k][n];
        }
        return res;
    }

    private int sum(int k, int i, int[][] dp) {
        int sum = 0;
        while (k >= 0) {
            sum += dp[k][i];
            k--;
        }
        return sum;
    }
}
