package algorithms.dp;

import java.util.List;

/**
 * 139. 单词拆分
 *
 * @author Sean Yu
 */
public class No139 {
}

/**
 * 直观解法：回溯
 * 将字典里的单词进行全排列，记录长度与s相同的所有排列，然后进行判断。
 * 数据量大的时候，会超时。
 * <p>
 * dp解法：
 * <p>
 * （1）分析最后一步：
 * 如果最后几个字母[k,i]在字典里，那么只要前k个字母能被拆分即可
 * <p>
 * （2）确定状态转移方程
 * dp(i)表示前i个字母能否被拆分
 * dp(i) = dp(k) && s.substring(i-wordLen,i) in wordDict
 * 重点：当发现dp(i)为true的时候，就可以跳出字典遍历。
 * 求dp(s.length())
 * （3）计算顺序
 * 从小到大
 * （4）边界条件和初始情况
 * dp(0) = 0
 */
class Solution139 {
    public boolean wordBreak(String s, List<String> wordDict) {
        if (s.length() == 0) {
            return true;
        }
        boolean[] dp = new boolean[s.length() + 1];
        dp[0] = true;
        for (int i = 1; i <= s.length(); i++) {
            for (String word : wordDict) {
                int wordLen = word.length();
                if (i - wordLen >= 0) {
                    String currLetters = s.substring(i - wordLen, i);
                    if (word.equals(currLetters)) {
                        dp[i] = dp[i - wordLen];
                        //只有dp[i]为真的时候，才可以跳过判断
                        if (dp[i]) {
                            break;
                        }
                    }
                }
            }
        }
        return dp[s.length()];
    }
}