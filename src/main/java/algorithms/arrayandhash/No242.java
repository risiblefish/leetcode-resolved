package algorithms.arrayandhash;

/**
 * 242. 有效的字母异位词
 */
public class No242 {
}

/**
 * 思路：
 * 利用数组充当哈希表
 */
class Solution242 {
    public boolean isAnagram(String s, String t) {
        if (s == null || t == null) {
            if (s == null && t == null) {
                return true;
            }
            return false;
        }
        int slen = s.length();
        int tlen = t.length();
        if (slen != tlen) {
            return false;
        }
        int[] arr = new int[26];
        for (int i = 0; i < slen; i++) {
            arr[s.charAt(i) - 'a']++;
        }
        for (int i = 0; i < tlen; i++) {
            arr[t.charAt(i) - 'a']--;
        }
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] != 0) {
                return false;
            }
        }
        return true;
    }
}