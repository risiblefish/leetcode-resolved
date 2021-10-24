package algorithms.string;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author Sean Yu
 */
public class No28 {
    public static void main(String[] args) {
//        System.out.println(new Solution28().strStr("ababcaababcaabc", "ababcaabc"));
//        System.out.println(new Solution28().strStr("mississippi", "issip"));
//        System.out.println(new Solution28().strStr("aabaabaaf", "aabaaf"));
//        System.out.println(new Solution28().strStr("hello", "ll"));
//        System.out.println(new Solution28().strStr("ababcaababcaabc", "ababcaabc"));
        System.out.println(new Solution28().strStr("hello", "ll"));

    }
}

class Solution28 {
    public int strStr(String haystack, String needle) {
        if(needle.length() == 0){
            return 0;
        }
        //初始化部分匹配值表
        int[] pmt = getTable(needle);
        int len = haystack.length();
        int j = 0;
        for (int i = 0; i < len; i++) {
            while (j > 0 && haystack.charAt(i) != needle.charAt(j)) {
                j = pmt[j - 1];
            }
            if (haystack.charAt(i) == needle.charAt(j)) {
                j++;
            }
            if (j == needle.length()) {
                return i - j + 1;
            }
        }
        return -1;
    }

    private int[] getTable(String s) {
        char[] arr = s.toCharArray();
        int n = arr.length;
        int[] pmt = new int[n];
        pmt[0] = 0;
        int j = 0;
        for (int i = 1; i < n; i++) {
            while (j > 0 && arr[i] != arr[j]) {
                j = pmt[j - 1];
            }
            if (arr[i] == arr[j]) {
                j++;
            }
            pmt[i] = j;
        }
        return pmt;
    }

}