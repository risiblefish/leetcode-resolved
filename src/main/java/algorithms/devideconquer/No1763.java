package algorithms.devideconquer;

import java.util.HashSet;
import java.util.Set;

/**
 * 1763. 最长的美好子字符串
 * @author Sean Yu
 */
public class No1763 {
    public static void main(String[] args) {
        System.out.println(new Solution1763().longestNiceSubstring(
                "jCj"
        ));
    }
}

/**
 * 思路：
 * 分治， 从l -> r (左闭右开) 遍历字符串， 如果到i处的字符不美好，那么从l -> i,  i + 1 -> r 开始搜索
 */
class Solution1763 {
    int start = 0, end = -1;

    public String longestNiceSubstring(String s) {
        char[] arr = s.toCharArray();
        search(arr, 0, arr.length);
        return end - start + 1 > 0 ? s.substring(start, end + 1) : "";
    }

    private void search(char[] arr, int l, int r) {
        if (l >= r) {
            return;
        }
        Set<Character> cache = new HashSet();
        for (int i = l; i < r; i++) {
            cache.add(arr[i]);
        }
        int i = l;
        for (i = l; i < r; i++) {
            char c = arr[i];
            if (cache.contains(Character.toLowerCase(c)) && cache.contains(Character.toUpperCase(c))) {
                continue;
            } else {
                break;
            }
        }
        if (i == r) {
            update(l, i - 1);
        } else {
            search(arr, l, i);
            search(arr, i + 1, r);
        }
    }

    private void update(int l, int r) {
        if (r - l > end - start) {
            start = l;
            end = r;
        }
    }
}
