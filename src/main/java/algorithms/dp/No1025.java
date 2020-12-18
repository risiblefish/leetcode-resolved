package algorithms.dp;

/**
 * @author Sean Yu
 */
public class No1025 {
    public static void main(String[] args) {
        System.out.println(new Solution1025_DP().divisorGame(4));
    }
}

/**
 * 解法1 ： 找规律
 * <p>
 * 对于博弈类的问题，如果没有头绪，可以写几个出来，先找找规律，然后大胆猜想，小心验证
 * I. 选x ∈ (0,N) 且 N % x = 0
 * II. N = N-x
 * III. 如果不能选了，则败
 * <p>
 * （1）如果N = 1 , A先手，但无数可选，所以败, 即N=1时，先手必败
 * (2) 如果N = 2 , A先手选1，N -> 1，变成情况（1), B败, 即N=2时，先手必胜
 * (3) 如果N = 3 , A先手不能选2是因为3 %2 ！= 0, 所以A只能选1，N->2，根据（2）知B必胜，即A败， 即N=3时，先手必败
 * (4) 如果N = 4, A先手可选1或2，如果A选1，N->3变成情况（3）知B必败，即A胜， 如果A选2，N->2变成情况（2）知B必胜，即A败，但题目的意思是A,B均选最有策略，即A会先手选1，故N=4时，先手必胜
 * 此时发现，看起来貌似N为奇数必败，N为偶数必胜，但我们不确定，可以再写2个看看
 * (5) 如果N = 5， A先手只能选1，因为234都不能整除5，变成情况（4）知B必胜，即N=5时，先手必败
 * (6) 如果N = 6， A先手可选1，2，3，对应变成情况(5)(4)(3)，其中（5）和（3）-> B必败，故N=6时，先手必胜
 * <p>
 * 证明过程（摘自leeccode）
 * <p>
 * N = 1N=1 和 N = 2N=2 时结论成立。
 * <p>
 * N > 2N>2 时，假设 N \leq kN≤k 时该结论成立，则 N = k + 1N=k+1 时：
 * <p>
 * 如果 kk 为偶数，则 k + 1k+1 为奇数，xx 是 k + 1k+1 的因数，只可能是奇数，而奇数减去奇数等于偶数，且 k + 1 - x \leq kk+1−x≤k，故轮到 Bob 的时候都是偶数。
 * 而根据我们的猜想假设 N\le kN≤k 的时候偶数的时候先手必胜，故此时无论 Alice 拿走什么，Bob 都会处于必胜态，所以 Alice 处于必败态。
 * <p>
 * 如果 kk 为奇数，则 k + 1k+1 为偶数，xx 可以是奇数也可以是偶数，若 Alice 减去一个奇数，那么 k + 1 - xk+1−x 是一个小于等于 kk 的奇数，此时 Bob 占有它，处于必败态，则 Alice 处于必胜态。
 * <p>
 * 作者：LeetCode-Solution
 * 链接：https://leetcode-cn.com/problems/divisor-game/solution/chu-shu-bo-yi-by-leetcode-solution/
 * 来源：力扣（LeetCode）
 * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
 */
class Solution1025_1 {
    public boolean divisorGame(int N) {
        return N % 2 == 0;
    }
}

/**
 * 解法2 ： DP
 * <p>
 * 1.分析最后一步归纳子问题
 * A先手取数k后，留给B的是 N-k , 有 ： N-k必然小于N
 * <p>
 * 子问题就变成了： 求N-k情况下，先手是否能获胜
 * <p>
 * 2. 确定状态转移方程
 * 假设dp(i) = true/false 表示在i范围内A先手是否获胜
 * 如果A能先手获胜，那么表示A能找到一种策略，先手取k，令B在i-k的范围内先手必然失败，其中k的范围是 [1,i-1] 且满足 i % k = 0
 * 假设(0,i)内能整除i的数是k1,k2,...kj
 * 那么有 algorithms.dp(i) = ![ algorithms.dp(i-k1) && algorithms.dp(i-k2) && .. && algorithms.dp(i-kj) ] //即只要有1个(i-k)满足先手必败，那么i就能先手必胜
 * <p>
 * 3. 初始条件和边界情况
 * algorithms.dp(1) = false
 * algorithms.dp(2) = true
 * <p>
 * 4. 计算顺序
 * 从小到大以保证计算结果
 */
class Solution1025_DP {
    public boolean divisorGame(int N) {
        if (N < 1) {
            return false;
        }
        if (N == 1) {
            return false;
        }
        boolean[] dp = new boolean[N + 1];
        dp[1] = false;
        dp[2] = true;
        for (int i = 3; i <= N; i++) {
            for (int k = 1; k < i; k++) {
                dp[i] = false;
                //只要有一个i-k满足先手必败，则i就能先手必胜
                if (i % k == 0 && !dp[i - k]) {
                    dp[i] = true;
                    //跳出循环，优化性能
                    break;
                }
            }
        }
        return dp[N];
    }
}

