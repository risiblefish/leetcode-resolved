package algorithms.dp;

import java.util.HashMap;
import java.util.Map;

/**
 * 873. 最长的斐波那契子序列的长度
 *
 * @author Sean Yu
 */
public class No873 {
}

/**
 * 比如 1 2 3 4 4 5 8， 最长子序列为 1 3 4 4 8 或者 1 2 3 5 8
 * 最后一步：
 * 对于最优解中，一定存在最后一个数arr[i]，满足斐波那契式 ： arr[l] + arr[r] = arr[i]，其中，l < r < i
 * 对于arr[r] 而言，存在 arr[k] + arr[l] = arr[r], 其中 k < l < r
 * 对此，可以把一对斐波那契式的数看做一个节点，如[k,l][l,r][r,i]，只有当arr[l] + arr[r] == arr[i]时
 * <p>
 * 子问题：令dp[l][r]表示以arr[l],arr[r]结尾的的最长长度
 * 那么dp[l][r] = max{ arr[k] + arr[l] == arr[r]前提下的dp[k][l] + 1}
 *
 * 所以，只需遍历每个l,r，找到所有满足arr[k] + arr[l] == arr[r]的情况下的最大的arr[l]
 * 其中，arr[k]的k可以通过map来进行查找。
 * 提前将 arr[k]和k 作为k-v存入map， 后续只需要k = map.get(arr[r]-arr[l]), 需要注意 k是 < l 的 ，如果不存在k，还需要提供默认值
 */
class Solution873 {
    public int lenLongestFibSubseq(int[] arr) {
        int[][] dp = new int[arr.length][arr.length];
        final Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < arr.length; i++) {
            map.put(arr[i], i);
        }
        int res = 0;
        for (int r = 1; r < arr.length; r++) {
            for (int l = 0; l < r; l++) {
                int kIndex = map.getOrDefault(arr[r] - arr[l], -1);
                if (kIndex != -1 && kIndex < l) {
                    if(dp[kIndex][l] == 0) {
                        dp[kIndex][l] = 2;
                    }
                    dp[l][r] = Math.max(dp[l][r], dp[kIndex][l] + 1);
                    res = Math.max(res, dp[l][r]);
                }
            }
        }
        return res;
    }
}
