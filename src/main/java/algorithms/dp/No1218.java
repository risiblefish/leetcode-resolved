package algorithms.dp;

import java.util.HashMap;
import java.util.Map;

/**
 * 1218. 最长定差子序列
 *
 * @author Sean Yu
 */
public class No1218 {
    public static void main(String[] args) {
        System.out.println(new Solution1218().longestSubsequenceUsingMap(new int[]{1, 2, 3, 4}, 1));
    }
}

/**
 * 1.分析最后一步
 * 对于长度超过1的序列，它的最后一个元素a[i]，一定有前一个元素a[k]，且a[i] - a[k] = difference
 * <p>
 * 2.确定状态
 * 令dp(i)表示包含第i个元素的最长等差子序列的长度
 * 所以，枚举k ∈ [0,i-1]，
 * （1）如果a[i] - a[k] = diff, dp(i) = max{dp(k)} + 1
 * （2）否则，dp(i) = 1
 * <p>
 * 3.初始条件和边界情况
 * dp(1) = 1
 */
class Solution1218 {
    //常规搜索，超时
    public int longestSubsequence(int[] arr, int difference) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int n = arr.length;
        int[] dp = new int[n];
        int temp = 1;
        dp[0] = 1;
        for (int i = 1; i < n; i++) {
            dp[i] = 1;
            for (int k = 0; k < i; k++) {
                if (arr[i] - arr[k] == difference) {
                    dp[i] = dp[k] + 1;
                }
            }
            temp = Math.max(dp[i], temp);
        }
        return temp;
    }


    //常规利用map记录
    public int longestSubsequenceUsingMap(int[] arr, int difference) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int n = arr.length;
        int[] dp = new int[n];
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        int temp = 0;
        for (int i = 0; i < n; i++) {
            dp[i] = 1;
            int arrElement = arr[i] - difference;
            if (map.containsKey(arrElement)) {
                dp[i] = map.get(arrElement) + 1;
            }
            map.put(arr[i], dp[i]);
            temp = Math.max(dp[i], temp);
        }
        return temp;
    }
}