package algorithms.dp;

/**
 * 338. 比特位计数
 * @author Sean Yu
 */
public class No338 {
}

/**
 * 题目要求时间复杂度O（N）
 * <p>
 * 对于2，4，8，16... （2的n次幂）而言，其二进制都是最高为为1，其余位为0
 * 因此可以利用这个性质来进行DP，
 * <p>
 * 用dp(i)来表示i的二进制中1的个数
 * 比如： 求99的二进制中1的个数，离99最接近的2的非负整数数次幂是64，即99 = 64 + 35
 * 有dp(99) = 1 + dp(35)，而dp(35)就变成了子问题。
 * <p>
 * 那么，如何求离i最近的最大2的非负整数次幂呢？
 * 对于2的n次幂x，有 x &（x-1）= 0 的性质 （这个只有靠记住了）
 * 我们在遍历i由小到大的过程中，通过上述性质，可以不断地更新x。
 * 之所以要从小到大，是因为求dp(i)，需要知道dp(j)，其中j < i
 */
class Solution338 {
    public int[] countBits(int num) {
        int x = 0;
        int[] dp = new int[num + 1];
        for (int i = 1; i <= num; i++) {
            if ((i & (i - 1)) == 0) {
                x = i;
            }
            dp[i] = dp[i - x] + 1;
        }
        return dp;
    }
}
