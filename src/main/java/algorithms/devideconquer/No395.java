package algorithms.devideconquer;

/**
 * @Author: Sean Yu
 * @Date: 2021/2/27 13:39
 */
public class No395 {
    public static void main(String[] args) {
        System.out.println(new Solution395().longestSubstring("aaaaaaaaaaaabcdefghijklmnopqrstuvwzyz", 3));
    }
}

/**
 * 思路：分治
 * 如果某个字符在整个串中出现的次数小于k，那么包含它的字串，也必然不满足条件
 * 比如：
 * aaba，k=2
 * b在整个串中只出现了1次，所以需要看不包含b的子串，是否满足条件，即看aa和a
 *
 * 具体操作：
 * 对于当前的串，先统计每个字母出现的次数
 * 然后从串的第一个字母开始遍历，如果该字母出现次数小于k，则以该字母为切点，递归遍历其左边的串和右边的串，返回值最大的那个长度
 */
class Solution395 {
    public int longestSubstring(String s, int k) {
        if (s == null || s.length() < k) {
            return 0;
        }
        char[] arr = s.toCharArray();
        int len = arr.length;
        int[] count = new int[26];
        for (int i = 0; i < len; i++) {
            count[arr[i] - 'a']++;
        }

        for (int i = 0; i < len; i++) {
            if (count[arr[i] - 'a'] < k) {
                return Math.max(longestSubstring(s.substring(0, i), k), longestSubstring(s.substring(i + 1), k));
            }
        }
        return len;
    }
}
