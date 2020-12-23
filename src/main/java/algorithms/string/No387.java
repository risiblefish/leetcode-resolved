package algorithms.string;

import java.util.HashSet;
import java.util.Set;

/**
 * 387. 字符串中的第一个唯一字符
 *
 * @author Sean Yu
 */
public class No387 {
    public static void main(String[] args) {
        System.out.println(new Solution387().firstUniqChar("loveleetcode"));
    }
}

/**
 * 思路： 夹逼 + set判重
 * 从左到右遍历每个字符，用一个set来记录已经重复出现的字符：
 * 如果该字符在set里，则跳过该字符，遍历下一个
 *
 * 如果该字符不在set里，则从右边最后一个字符开始向该字符靠近
 * 如果右边字符在set里 or 右边字符 和 当前字符 不一样，则继续夹逼
 * 否则（右边字符不在set里且与当前字符一样），说明该字符是一个新的重复字符，于是把它加入set，然后跳出夹逼
 *
 * 如果夹逼结束后，i == r，说明夹逼过程中没有遇到相同字符，则arr[i]就是第一个不重复的字符，返回i即可
 * 遍历完所有字符都没有i==r，说明每个字符都重复，返回-1
 */
class Solution387 {
    public int firstUniqChar(String s) {
        if (s == null || s.length() == 0) {
            return -1;
        }
        char[] arr = s.toCharArray();
        int n = arr.length;
        Set<Character> set = new HashSet<Character>();
        for (int i = 0; i < n; i++) {
            if (set.contains(arr[i])) {
                continue;
            }
            int r = n - 1;
            while (i < r) {
                if (set.contains(arr[r]) || arr[i] != arr[r]) {
                    r--;
                } else {
                    set.add(arr[i]);
                    break;
                }
            }
            if (r == i) {
                return i;
            }
        }
        return -1;
    }
}