package algorithms.dp;

/**
 * @author Sean Yu
 * @date 2020/11/24 23:07
 */
public class No91 {
    public static void main(String[] args) {
        new Solution91().numDecodings("10011");
    }
}

/**
 * 1. 分析最后一步，确定子问题
 * 主要是看最后一个字母，假设字符串长度为n，最后2个字符串是12，那么可能的分法是：
 * 分法（1） -> 把12解密为字母L， 假设这种情况下，前n-2个字符串共有x中解法
 * 分法（2） -> 只解密最后那个1 -> A， 假设这种情况下，前n-1个字符串共有y种解法
 * 所以，对于最后2个字符串，共有x+y种解法
 * <p>
 * 子问题就变成了： 求n-1个字符和n-2个字符的解密数
 * <p>
 * 2. 状态转移方程
 * 假设 algorithms.dp(n)表示长度为n的字符串的解密总数，那么dp(n) = algorithms.dp(n-1) + algorithms.dp(n-2)//如果这2个字符能转成字母的话，否则不加
 * <p>
 * 3. 初始状态和边界条件
 * algorithms.dp(0) = 1 //这是一个便捷的写法，因为dp(0)只会求dp(1)或者dp(2)的时候用到，如果1个或者2个字符能被解码，那就是1种解码方式
 * algorithms.dp(1) = 如果字符为0则为0，否则为1
 * 对于长度为2的字符串，当且仅当这个串在[10,26]的时候，才能够被解密，比如00，09这种，都不能解密。
 * <p>
 * 4. 计算顺序
 * 由于要求dp(n)，但是dp(n）依赖于dp(n-1)等，所以n从小到大计算来避免重复计算
 */
class Solution91 {
    public int numDecodings(String s) {
        if(s == null || s.length() == 0) {
            return 0;
        }
        int n = s.length();
        int[] dp = new int[n + 1];
        dp[0] = 1;
        dp[1] = s.charAt(0) == '0' ? 0 : 1;
        for (int i = 2; i <= n; i++) {
            dp[i] = 0;
            if (s.charAt(i - 1) != '0') {
                dp[i] += dp[i - 1];
            }
            int num2 = Integer.parseInt(s.substring(i - 2, i));
            if (num2 >= 10 && num2 <= 26) {
                dp[i] += dp[i - 2];
            }
//            //优化：如果这2个字母不能被上面任意一种方式解密，说明这个串都无法解密，可以直接返回0
//            if(algorithms.dp[i] == 0) {
//                return 0;
//            }
        }
        return dp[n];
    }
}
