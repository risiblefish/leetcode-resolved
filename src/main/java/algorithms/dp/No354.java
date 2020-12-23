package algorithms.dp;

import java.util.Arrays;
import java.util.Comparator;

/**
 * 354. 俄罗斯套娃信封问题
 *
 * @author Sean Yu
 */
public class No354 {
    public static void main(String[] args) {
        System.out.println(new Solution354().maxEnvelopes(new int[][]{{5, 4}, {6, 4}, {6, 7}, {2, 3}}));
    }
}

/**
 * 首先注意题目的前置条件： 不允许旋转信封，这意味着每个信封的长和宽不能进行身份转换
 * <p>
 * 思路:
 * <p>
 * 降维，先将长或宽选一个排序，比如先按长度排序，然后找宽度的最长上升子序列的长度
 * <p>
 * 需要注意的一个细节是，长度可能存在相等的情况
 * 比如 (2,5),(2,6),(3，10)
 * 如果我们只看宽度的最长上升序列，那最优解就是5，6，10
 * 而最优解是 (2,5) (3,10) 或者 (2,6)(3,10)，这是因为长度相等的话就不能进行套娃操作
 * 这里一个处理方法是，我们在对长度排序的时候，设置排序规则，当长度相等时，宽度大的排在前面
 * 如此一来，排序后的数组就是 （2，6）（2，5）（3，10），就可以避免前面的问题
 * <p>
 * 求最长上升子序列的长度：
 * dp(i)表示包含第i个信封的最长上升子序列长度，那么
 * dp(i)初始化为1，因为至少包含它本身
 * 当有 第i-1个的信封的宽度 > 第k-1个信封宽度 时，
 * dp(i) = max{1, dp(k)+ 1 && 0 <= k < i}
 * 最后 max{dp(i) | 0 <= i < n} 就是最终的解
 */
class Solution354 {
    public int maxEnvelopes(int[][] envelopes) {
        if (envelopes == null || envelopes.length == 0 || envelopes[0].length == 0) {
            return 0;
        }
        //对数组进行长度升序排列
        Arrays.sort(envelopes, new Comparator<int[]>() {
            public int compare(int[] o1, int[] o2) {
                if (o1[0] == o2[0]) {
                    //如果长度一样，再按宽度降序
                    return o2[1] - o1[1];
                }
                //长度升序
                return o1[0] - o2[0];
            }
        });

        int n = envelopes.length;
        int[] dp = new int[n];
        int temp = 1;
        for (int i = 0; i < n; i++) {
            dp[i] = 1;
            for (int k = 0; k < i; k++) {
                //只有当第i个信封的宽比第k个信封的宽大时，才能把第k个信封塞进第i个信封
                if (envelopes[i][1] > envelopes[k][1]) {
                    dp[i] = Math.max(dp[i], dp[k] + 1);
                }
            }
            temp = Math.max(dp[i], temp);
        }
        return temp;
    }
}

