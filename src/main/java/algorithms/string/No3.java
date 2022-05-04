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
 */
class Solution3 {
    public int lengthOfLongestSubstring(String s) {
        Set<Character> set = new HashSet();
        int l = 0 , r = 0;
        int max = 0;
        while(r < s.length()){
            char c = s.charAt(r);
            while(set.contains(c)){
                set.remove(s.charAt(l));
                l++;
            }
            set.add(c);
            r++;
            max = Math.max(max,r-l);
        }
        return max;
    }
}