package dp;

/**
 * 983. 最低票价
 * @author Sean Yu
 */
public class No983 {
    public static void main(String[] args) {
        System.out.println(new Solution983().mincostTickets(new int[]{1, 4, 6, 7, 8, 20}, new int[]{2, 7, 15}));
    }
}

/**
 * 令dp(i)表示从第1天到第i天的最小花费，那么有：
 * （1）如果第i天不出行，则 ：dp(i) = dp(i-1)
 * （2）如果第i天出行，则：dp(i) = min { dp(i-1) + costOne , dp(i-7) + costSeven , dp(i-30) + costThirty }
 *
 *  dp(最后的出行日)就是要求的解
 */
class Solution983 {
    public int mincostTickets(int[] days, int[] costs) {
        if (days == null || days.length == 0) {
            return 0;
        }
        int n = days.length;
        //记录出行的最后一天
        int last = days[n - 1];
        //用于遍历days数组
        int index = 0;
        int[] dp = new int[last + 1];

        //从第1天遍历到出行的最后一天
        for (int i = 1; i <= last; i++) {
            //首先判断第i天是否要出行
            if(i == days[index]) {
                //初始化
                dp[i] = dp[i - 1] + costs[0];

                //如果不足7天，就按7天算，比如连续3天不足7天，但也可以使用7天的票
                dp[i] = i - 7 >= 0 ? Math.min(dp[i], dp[i - 7] + costs[1]) : Math.min(dp[i], costs[1]);

                //同理，不足30天，也可使用30天的票
                dp[i] = i - 30 >= 0 ? Math.min(dp[i], dp[i - 30] + costs[2]) : Math.min(dp[i], costs[2]);

                index++;
            }else {
                dp[i] = dp[i-1];
            }
        }
        return dp[last];
    }
}
