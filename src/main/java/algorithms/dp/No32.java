package algorithms.dp;

/**
 * 32. 最长有效括号
 *
 * @author Sean Yu
 */
public class No32 {
    public static void main(String[] args) {
        System.out.println(new Solution32().longestValidParentheses("()()))))()()("));
    }
}

/**
 * 1.暴力法思路：
 * 按s长度递减的顺序，用滑动窗口的办法，逐一搜索所有长度为i(len >= i >= 0)的字符，判断其是否是最长有效括号
 * 即先看所有长度为s.length的字符串，再看所有长度为s.length-1的字符串，...
 * <p>
 * <p>
 * 2. DP：
 * 用dp[i]表示以第i个括号结尾的最长有效括号的长度
 * 那么：
 * （1）所有以左括号结尾的都不是有效括号，故为0
 * 如果第i个括号是右括号，则又分2种情况：
 * （2）如果第i-1个括号是左括号，则第i-1和第i个组成一对括号，长度为2，此时，最长有效括号长度为： 以上一个括号结尾最长有效括号的长度 + 2
 * （3）如果第i-1个括号是右括号，则需要判断第i个括号对应的左括号是否存在
 * 比如 ：
 * ?【...】)，第i个括号为) ，这里为了区别，将第i-1个括号用】表示，那么需要知道?处是否存在，如果存在，是否是左括号
 * 因为dp[i-1]是已知的，表示以】结尾的最长有效括号的长度，所以【的位置，就是i-dp[i-1]
 * 0 1 2 3 4 5
 * ( ) ?【 】 )
 * 比如i为5时, 【的索引就是 i - dp[4] = 5-2 = 3
 * 所以需要判断，?处的符号是否为左括号，而?处的索引就是】处的索引-1，即i - dp[i-1] - 1
 * 假设?处存在，且为左括号，以5处结尾的最长有效长度，除了2~5的长度之外，还应当加上以1处结尾的最长有效括号长度，在本例中，dp[1]=2
 */
class Solution32 {
    public int longestValidParentheses(String s) {
        char[] arr = s.toCharArray();
        int len = arr.length;
        int maxLen = 0;
        int[] dp = new int[len];
        for (int i = 1; i < len; i++) {
            if (arr[i] == ')') {
                //leftBracketIndex 表示以第i个括号结尾，且该括号是右括号的前提下，与其对应的左括号的索引
                int leftBracketIndex = i - dp[i - 1] - 1;
                if (arr[i - 1] == '(') {
                    dp[i] = dp[i - 1] + 2;
                    //leftBracketIndex-1 也可以用 i-2来表示
                    if (leftBracketIndex - 1 >= 0) {
                        dp[i] += dp[leftBracketIndex - 1];
                    }
                } else {
                    if (leftBracketIndex >= 0) {
                        char c = arr[leftBracketIndex];
                        if (c == '(') {
                            dp[i] = dp[i - 1] + 2;
                            if (leftBracketIndex - 1 >= 0) {
                                dp[i] += dp[leftBracketIndex - 1];
                            }
                        }
                    }
                }
            }
            maxLen = Math.max(maxLen, dp[i]);
        }
        return maxLen;
    }

    /**
     * 优化版DP
     * 上述（2）（3）情况可以简化为同一个情况，即
     * 当第i个括号为右括号时，求出其对应的左括号的位置
     * 然后依次判断这个位置 是否存在？ 如果存在，是否是左括号？如果是，则统计长度。
     * @param s
     * @return
     */
    public int longestValidParenthesesII(String s) {
        char[] arr = s.toCharArray();
        int len = arr.length;
        int maxLen = 0;
        int[] dp = new int[len];
        for (int i = 1; i < len; i++) {
            if (arr[i] == ')') {
                //leftBracketIndex 表示以第i个括号结尾，且该括号是右括号的前提下，与其对应的左括号的索引
                //这里提前初始化，是假设这个索引存在
                int leftBracketIndex = i - dp[i - 1] - 1;
                //如果索引存在
                if (leftBracketIndex >= 0) {
                    char c = arr[leftBracketIndex];
                    //再判断该索引处是否是左括号
                    if (c == '(') {
                        dp[i] = dp[i - 1] + 2;
                        //还要加上以左括号前面的括号结尾的最长连续括号长度
                        if (leftBracketIndex - 1 >= 0) {
                            dp[i] += dp[leftBracketIndex - 1];
                        }
                    }
                }
            }
            maxLen = Math.max(maxLen, dp[i]);
        }
        return maxLen;
    }
}
