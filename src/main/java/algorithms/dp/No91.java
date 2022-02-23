package algorithms.dp;

import java.util.Arrays;

/**
 * 91. 解码方法
 */
public class No91 {
    public static void main(String[] args) {
        System.out.println(new Solution911().numDecodings("123"));
    }
}

/**
 * 方法1： 写出带缓存的递归
 */
class Solution911 {
    char[] arr;
    int n;
    int[] cache;

    public int numDecodings(String s) {
        arr = s.toCharArray();
        n = arr.length;
        cache = new int[n];
        Arrays.fill(cache, -1);
        return process(0);
    }

    /**
     * 处理到第idx个字符时，解析的方案数
     *
     * @param idx
     * @return
     */
    private int process(int idx) {
        //越界的情况下，之所以返回1，表示idx之前的字符都是有效的
        if (idx >= n) {
            return 1;
        }
        if (cache[idx] != -1) {
            return cache[idx];
        }
        int count = 0;
        if (arr[idx] != '0') {
            count += process(idx + 1);
            if (idx + 1 < n) {
                int num = Integer.parseInt(arr[idx] + "" + arr[idx + 1]);
                if (num <= 26) {
                    count += process(idx + 2);
                }
            }
        }
        //else count = 0; // 这句话可以省略
        cache[idx] = count;
        return count;
    }
}

/**
 * 对前面的加缓存递归进行修改
 * 发现变量只有idx，并且idx的范围在0-n
 * 所以创建1个n+1长度的dp[]数组
 */
class Solotion911_II {
    char[] arr;
    int n;

    public int numDecodings(String s) {
        arr = s.toCharArray();
        n = arr.length;
        arr = s.toCharArray();
        n = arr.length;
        int[] dp = new int[n + 1];
        dp[n] = 1;
        for (int i = n - 1; i >= 0; i--) {
            if (arr[i] != '0') {
                dp[i] = dp[i + 1];
                if (i + 1 < n && Integer.parseInt(arr[i] + "" + arr[i + 1]) <= 26) {
                    dp[i] += dp[i + 2];
                }
            }
        }
        return dp[0];
    }
}
