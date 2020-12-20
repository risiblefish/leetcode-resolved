package algorithms.dp;

/**
 * 188. 买卖股票的最佳时机 IV
 *
 * @author Sean Yu
 */
public class No188 {
    public static void main(String[] args) {
        System.out.println(new Solution188().maxProfit(2, new int[]{2, 1, 4, 5, 2, 9, 7}));
    }
}


/**
 * 1. 确定状态转移方程
 * 回顾只能交易2次（No.123）的时候的找出状态转移方程的过程:
 * <p>
 * （1）首先划分出5个条件
 * <p>
 * 阶段0： 第一次买之前
 * 阶段1： 当天买 到 第一次卖之前 (持有股票)
 * 阶段2： 第一次卖之后 到 第二次买之前
 * 阶段3： 当天买 到 第二次卖之前（持有股票）
 * 阶段4： 第二次卖之后
 * <p>
 * （2）然后把条件加入到状态里，然后推理出状态转移方程
 * <p>
 * 假设dp[i][j]表示前i天阶段j的最大收益，那么有：
 * 令gap = p[i-1] - p[i-2]
 * algorithms.dp[i][0] = algorithms.dp[i-1][0]
 * algorithms.dp[i][1] = max { algorithms.dp[i-1][0] , algorithms.dp[i-1][1] + gap }
 * algorithms.dp[i][2] = max { algorithms.dp[i-1][2] , algorithms.dp[i-1][1] + gap }
 * algorithms.dp[i][3] = max { algorithms.dp[i-1][2] , algorithms.dp[i-1][1] + gap , algorithms.dp[i-1][3] + gap }
 * algorithms.dp[i][4] = max { algorithms.dp[i-1][4] , algorithms.dp[i-1][3] + gap }
 * <p>
 * 当只能交易2次的时候，我们可以划分出5个阶段，推广到k次交易的时候，可以划分出2j + 1个阶段
 * <p>
 * 观察只能交易2次时的5个状态转移方程，注意到，可以进一步划分为，手里没股票（阶段0，1，3）和手里有股票（阶段2，4） 两类
 * <p>
 * <p>
 * 如果把阶段用j表示的话，那么
 * <p>
 * 手里没股票时(j为偶数)，状态转移方程为：
 * algorithms.dp[i][j] = max {algorithms.dp[i-1][j] , algorithms.dp[i-1][j-1] + gap}
 * <p>
 * 手里有股票时(j为奇数)，状态转移方程为：
 * algorithms.dp[i][j] = max { algorithms.dp[i-1][j-1], algorithms.dp[i-1][j-2] + gap, algorithms.dp[i-1][j] + gap}
 * <p>
 * 最后的所有dp[n][j]，其中j为偶数时，最大的那个就是解
 * <p>
 * 2. 初始条件和边界情况
 * 上面2个式子中的max{}里的参数，存在的话再加进去
 * <p>
 * 3. 计算顺序
 * 从小到大
 */
class Solution188 {
    public int maxProfit(int k, int[] prices) {
        //题目保证k非负，prices非空
        int n = prices.length;

        k = Math.min(k, n);
        int periodCount = 2 * k + 1;

        int[][] dp = new int[n + 1][periodCount];

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j < periodCount; j++) {
                //手里有股票
                if (j % 2 != 0) {
                    dp[i][j] = dp[i - 1][j - 1];
                    if (i - 2 >= 0) {
                        int gap = prices[i - 1] - prices[i - 2];
                        dp[i][j] = Math.max(dp[i][j], dp[i - 1][j] + gap);
                        if (j - 2 >= 0) {
                            dp[i][j] = Math.max(dp[i][j], dp[i - 1][j - 2] + gap);
                        }
                    }
                }
                //手里没股票
                else {
                    dp[i][j] = dp[i - 1][j];
                    if (i - 2 >= 0) {
                        int gap = prices[i - 1] - prices[i - 2];
                        dp[i][j] = Math.max(dp[i][j], dp[i - 1][j - 1] + gap);
                    }
                }
            }
        }

        int temp = dp[n][0];
        for (int j = 2; j < periodCount; j += 2) {
            temp = Math.max(temp, dp[n][j]);
        }
        return temp;
    }
}
