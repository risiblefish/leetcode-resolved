package algorithms.string;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Sean Yu
 * @create 2022/5/4 18:46
 */
public class No3 {
    public static void main(String[] args) {
        Solution3 t = new Solution3();
        System.out.println(t.lengthOfLongestSubstring("pwwkew"));
    }
}

/**
 * 思路： 滑动窗口
 * 移动L,remove  移动r add
 * <p>
 * 是否有必要 在while(set.contains(c)) 检查 l < r？
 * 答：
 * 不是必要
 * <p>
 * 这是因为，如果 `set` 中包含字符 `c`，那么一定存在某个索引 `i`，使得 `l <= i < r`，并且 `s.charAt(i) == c`。因此，`l` 一定小于 `r`。
 */
class Solution3 {
    public int lengthOfLongestSubstring(String s) {
        Set<Character> set = new HashSet();
        int l = 0, r = 0;
        int max = 0;
        while (r < s.length()) {
            char c = s.charAt(r);
            while (set.contains(c)) {
                set.remove(s.charAt(l));
                l++;
            }
            set.add(c);
            r++;
            max = Math.max(max, r - l);
        }
        return max;
    }
}