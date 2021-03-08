package algorithms.dp;

/**
 * 132. 分割回文串 II
 *
 * @author Sean Yu
 * @date 2020/12/21 10:52
 */
public class No132 {
    public static void main(String[] args) {
        System.out.println(new Solution132II().minCut("leet"));
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
 * dp(i)表示前i个字符可以分割成最少的回文串的个数，那么有：
 * 如果s[0~i-1]是回文，那么dp(i) = 0
 * 如果s[0~i-1]不是回文，如果存在k，使得s[k ~ i-1]是回文，那么dp(i) = min{ dp(k) } + 1 ， 1 <= k <= i-1
 */
class Solution132 {
    public int minCut(String s) {
        if (s == null || s.length() <= 1) {
            return 0;
        }

        int n = s.length();
        int[] dp = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            //长度为i的字符串，至多分为i-1个回文子串，即每个字母为一个回文
            dp[i] = i - 1;
            if (isPalindrome(s.substring(0, i))) {
                dp[i] = 0;
            } else {
                //k从1开始，是因为已经判断过，并知道0-i不是回文串了
                for (int k = 1; k <= i - 1; k++) {
                    //如果k到i-1是回文串
                    if (isPalindrome(s.substring(k, i))) {
                        dp[i] = Math.min(dp[i], dp[k] + 1);
                    }
                }
            }
        }
        return dp[n];
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
 * 优化版本： 牺牲空间换时间，提前计算出s[i][j]是否是回文串（利用中心扩散）
 */
class Solution132II {
    public int minCut(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        int n = s.length();
        char[] arr = s.toCharArray();
        boolean[][] isPalindrome = new boolean[n][n];
        int[] dp = new int[n + 1];
        init(isPalindrome, arr);

        for (int len = 1; len <= n; len++) {
            dp[len] = len - 1;
            if (isPalindrome[0][len - 1]) {
                dp[len] = 0;
            } else {
                for (int k = 1; k < len; k++) {
                    if (isPalindrome[k][len - 1]) {
                        dp[len] = Math.min(dp[len], dp[k] + 1);
                    }
                }
            }
        }
        return dp[n];
    }

    //初始化，预先找出所有的回文子串
    private void init(boolean[][] isPalindrome, char[] arr) {
        int l, r;
        for (int i = 0; i < arr.length; i++) {
            //找到以i为中心，所有奇数长度的回文
            l = i;
            r = i;
            while (l >= 0 && r < arr.length) {
                if (arr[l] != arr[r]) {
                    break;
                }
                isPalindrome[l][r] = true;
                l--;
                r++;
            }
            //找到所有以i和i+1的中线为中心，所有偶数长度的回文
            l = i;
            r = i + 1;
            while (l >= 0 && r < arr.length) {
                if (arr[l] != arr[r]) {
                    break;
                }
                isPalindrome[l][r] = true;
                l--;
                r++;
            }
        }
    }
}
