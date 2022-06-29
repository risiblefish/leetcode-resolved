package algorithms.greed;

import java.util.HashSet;
import java.util.Set;

/**
 * 1647. 字符频次唯一的最小删除次数
 * @author Sean Yu
 * @create 2022/6/29 16:22
 */
public class No1647 {
    public static void main(String[] args) {
        System.out.println(new Solution1647().minDeletions("aaabbbcc"));
    }
}

class Solution1647 {
    public int minDeletions(String s) {
        int[] cnt = new int[26];
        char[] arr = s.toCharArray();
        for (char c : arr) {
            cnt[c - 'a']++;
        }
        Set<Integer> set = new HashSet();
        int res = 0;
        for (int count : cnt) {
            int c = count;
            if (!set.contains(c)) {
                set.add(c);
            } else {
                while (set.contains(c) && c > 0) {
                    c--;
                    res++;
                }
                set.add(c);
            }
        }
        return res;
    }
}