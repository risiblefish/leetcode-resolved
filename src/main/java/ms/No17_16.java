package ms;

/**
 * @author Sean Yu
 */
public class No17_16 {
    public static void main(String[] args) {
        System.out.println(new Solution17_16().massage(new int[]{1, 2, 3, 1}));
    }
}

/**
 * 一个有名的按摩师会收到源源不断的预约请求，每个预约都可以选择接或不接。在每次预约服务之间要有休息时间，因此她不能接受相邻的预约。
 * 给定一个预约请求序列，替按摩师找到最优的预约集合（总预约时间最长），返回总的分钟数。
 * <p>
 * 输入： [1,2,3,1]
 * 输出： 4
 * 解释： 选择 1 号预约和 3 号预约，总时长 = 1 + 3 = 4。
 * <p>
 * 1. 最后一步
 * 假设数组叫a，长度为n
 * 假设最优解存在，那么一定存在a[i]， i ∈ [0,n）
 * 对于a[i]而言，除了a[i-1]，可以选择其他任意的时间作为它的相邻点
 * <p>
 * 2. 确认状态转移方程
 * 令dp[i]表示包含下标为i的最优集合，那么有dp[i] = a[i] + max{dp[0],dp[1],...dp[i-2]}
 * 最后就是找出所有dp[i]中最大的那个
 * <p>
 * 3. 初始状态
 * dp[0] = a[0]
 * <p>
 * 4.计算顺序
 * 从小到大，保存计算结果，避免重复计算
 */
class Solution17_16 {
    public int massage(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int n = nums.length;
        int[] dp = new int[n];
        dp[0] = nums[0];
        int temp = dp[0];

        for (int i = 1; i < n; i++) {
            dp[i] = nums[i];
            //如果i前面有数，找到最大的那个然后相加
            if(i - 2 >= 0) {
                int maxBefore = dp[0];
                for (int k = 1; k <= i - 2; k++) {
                    maxBefore = Math.max(maxBefore, dp[k]);
                }
                dp[i] = nums[i] + maxBefore;
            }
            temp = Math.max(temp, dp[i]);
        }
        return temp;
    }
}
