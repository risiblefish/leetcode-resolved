package algorithms.dp;

/**
 * No72 编辑距离
 * @author Sean Yu
 */
public class No72{
    public static void main(String[] args) {
        // b =1.456 + 1.948x + 0.528x^2 + 0.036x^3
    }


}

/**
 *  原题是阉割版，这里对拓展版本进行说明
 *  对于1个单词，在只做1步操作的情况下，共有4种情况，假设每种操作都有个对应的"代价"
 *  1. 不做任何操作 代价为0
 *  2. 删除1个字符  代价为delete 简称d
 *  3. 添加1个字符  代价为add 简称a
 *  4. 替换1个字符  代价为replace 简称r
 *
 *  问题：将字符串s1变成字符串s2花费的最小代价是多少？
 *  首先2个字符串要变成一样，那么先要长度变成相等，所以容易想到从长度入手进行分析
 *  然后我们考虑最简单的一些情况，
 *  假如s1为空串，s2非空，那么编辑距离就是s2的长度，即s1进行s2.length次添加，每次代价为a
 *  加入s1非空，s2为空串，那么编辑距离就是s1的长度，即s1进行s1.length次删除, 每次代价为d
 *
 *  如果 我们用dp[i][j]来表示 s1前i个字符 变成 s2前j个字符 的编辑距离，
 *  那么显然有
 *  dp[0][j] = j * a
 *  dp[i][0] = i * d
 *  原问题就变成： 求dp[s1.length][s2.length]
 *
 *  然后来分析普遍状态下的dp[i][j]
 *  此时对应的s1具体为s1.charArray[0,1.,,,i-1], 因为i表示长度，所以下标从0到i-1
 *  此时对应的s2具体为s2.charArray[0,1.,,,j-1]  同理，下标从0到j-1
 *
 *  现在希望求s1[0,i-1]变成s2[0,j-1]的最小代价
 *  （1）如果已经知道s1[0,i-2]变成s2[0,j-1]的最小代价dp[i-1][j]，那么只需要在这个代价的基础上，再对s1执行1次删除就可以了，总代价为dp[i-1][j] + d
 *  通俗的话就是：让s1除开最后一个字符的部分变成s2，然后再删掉除开的那个字符
 *
 *  （2）如果已经知道s1[0][i-1]变成s2[0,j-2]的最小代价dp[i][j-1]，那么只需在此基础上，再对s1执行1次添加即可，总代价为dp[i][j-1]+a
 *  通俗的话就是：让s1变成 除开最后一个字符的s2，然后再加上这个字符
 *
 *  （3）如果s1的最后一个字符和s2最后一个字符相同，那么代价为dp[i-1][j-1]
 *
 *  （4）如果最后一个字符不同，dp[i-1][j-1] + r
 *
 *   那么，dp[i][j]就是上面4种情况里最小的那种，由于总是依赖前一行或者前一列的值，我们可以从小到大进行计算
 */
class Solution72 {
    public int minDistance(String word1, String word2) {
        return minDistance(word1, word2, 1,1, 1);
    }
    public int minDistance(String word1, String word2, int a, int d, int r) {
        int len1 = word1.length();
        int len2 = word2.length();
        int[][] dp = new int[len1 + 1][len2 + 1];
        for(int i = 1 ; i <= len1; i++){
            dp[i][0] = i * a;
        }
        for(int j = 1 ; j<= len2 ; j++){
            dp[0][j] = j * d;
        }
        for(int i = 1 ; i <= len1 ; i++){
            for(int j = 1; j <= len2 ; j++){
                int p1 = dp[i-1][j] + d;
                int p2 = dp[i][j-1] + a;
                int p3 = word1.charAt(i-1) == word2.charAt(j-1) ? dp[i-1][j-1] : dp[i-1][j-1] + r;
                dp[i][j] = Math.min(p1,p2);
                dp[i][j] = Math.min(dp[i][j], p3);
            }
        }
        return dp[len1][len2];
    }
}
