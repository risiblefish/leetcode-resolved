package algorithms.dp;

/**
 * 132. 分割回文串 II
 *
 * @author Sean Yu
 * @date 2020/12/21 10:52
 */
public class No132 {
    public static void main(String[] args) {
        System.out.println(new Solution132_2().minCut("aab"));
    }
}

/**
 * 1. 分析最后一步
 * 因为是分割，而不是重组，所以每个字符在原串中的位置是不会变的
 * <p>
 * 假设在次数最少的分割中，最后那个回文子串是s[k]到s[n-1]，其中0 <= k <= n-1
 * 那么s[0]到s[k-1]的串，分割次数一定是最少
 * <p>
 * 2. 确定状态
 * dp(i)表示前i个字符可以分割成最少的回文串的个数，那么有
 * 如果存在k，使得s[k ~ i-1]是回文，那么dp(i) = min{ dp(k) } + 1 ， 0 <= k <= i-1
 */
class Solution132 {
    public int minCut(String s) {
        if (s == null || s.length() <= 1) {
            return 0;
        }

        int n = s.length();
        int[] dp = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            dp[i] = i;
            for (int k = 0; k <= i - 1; k++) {
                //如果k到i-1是回文串
                if (isPalindrome(s.substring(k, i))) {
                    dp[i] = Math.min(dp[i], dp[k] + 1);
                }
            }
        }
        return dp[n] - 1;
    }

    private boolean isPalindrome(String s) {
        int l = 0, r = s.length() - 1;
        while (l < r) {
            if (s.charAt(l) != s.charAt(r)) {
                return false;
            }
            l++;
            r--;
        }
        return true;
    }
}

/**
 * 优化版本： 牺牲空间换时间，提前计算出s[i][j]是否是回文串
 */
class Solution132_2 {
    public int minCut(String s) {
        if (s == null || s.length() <= 1) {
            return 0;
        }
        int n = s.length();
        int[] dp = new int[n + 1];
        boolean[][] isPalindrome = new boolean[n][n];
        init(s, isPalindrome);
        for (int i = 1; i <= n; i++) {
            dp[i] = i;
            for (int k = 0; k <= i - 1; k++) {
                //如果k到i-1是回文串
                if (isPalindrome[k][i - 1]) {
                    dp[i] = Math.min(dp[i], dp[k] + 1);
                }
            }
        }
        return dp[n] - 1;
    }

    private void init(String s, boolean[][] isPalindrome) {
        int n = s.length();
        //找到所有奇数长度的回文串, 遍历每个字符，以每个字符为中心向两边扩散
        for (int i = 0; i < n; i++) {
            //长度为1的串必定是回文串
            isPalindrome[i][i] = true;
            int l = i - 1, r = i + 1;
            while (l >= 0 && r < n) {
                if (s.charAt(l) == s.charAt(r)) {
                    isPalindrome[l][r] = true;
                    l--;
                    r++;
                } else {
                    break;
                }
            }
        }

        //找到所有偶数长度的回文串，遍历前n-1个字符，以该字符为对称轴左侧中心，向两边扩散
        for (int i = 0; i < n - 1; i++) {
            int l = i, r = i + 1;
            while (l >= 0 && r < n) {
                if (s.charAt(l) == s.charAt(r)) {
                    isPalindrome[l][r] = true;
                    l--;
                    r++;
                } else {
                    break;
                }
            }
        }
    }
}
