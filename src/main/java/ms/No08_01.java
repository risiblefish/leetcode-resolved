package ms;

/**
 * @author Sean Yu
 */
public class No08_01 {
    public static void main(String[] args) {
        System.out.println(new Solution08_01().waysToStep(76));
    }
}


/**
 * 1. 分析最后一步
 * n个阶梯，每次可走1，2，3步，那么走完阶梯最后一定是要么走1步，要么走2步，要么走3步
 * 其中，1步走完的前提是走完了前面n-1步，2步走完的前提是走完了前面n-2步，3步走完的前提是走完了前面n-3步
 * <p>
 * 2. 确定状态转移方程
 * 令dp[n] = dp[n-1] + dp[n-2] + dp[n-3]
 * <p>
 * 3. 确定初始状态
 * dp[0] = 0
 * dp[1] = 1
 * dp[2] = 2 // 2次1步或者1次2步
 * dp[3] = 4 //dp[1] + dp[2] + 直接3步走出去
 * <p>
 * 4. 计算顺序
 * 从小到大避免重复计算
 */
class Solution08_01 {
    public int waysToStep(int n) {
        if (n == 0) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }
        if (n == 2) {
            return 2;
        }
        if (n == 3) {
            return 4;
        }

        //通过滚动数组来节省空间
        long s = 1, m = 2, l = 4;
        long curr = 0;

        for (int i = 4; i <= n; i++) {
            //由于数比较大，所以每次算完就取模来减少数量级
            curr = (s + m + l) % 1000000007;
            s = m;
            m = l;
            l = curr;
        }
        return (int) curr;
    }
}