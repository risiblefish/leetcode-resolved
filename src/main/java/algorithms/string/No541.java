package algorithms.string;

/**
 * 541. 反转字符串 II
 */
public class No541 {
}

/**
 * 思路： 先实现翻转字符串，然后再按索引翻转
 * 细节： 题目指出，当剩余字符数量（remain） < k时，将剩余的全部翻转
 */
class Solution541 {
    public String reverseStr(String s, int k) {
        char[] arr = s.toCharArray();
        int n = s.length();
        for (int i = 0; i < n; i += k + k) {
            reverse(i, k, arr);
        }
        return new String(arr);
    }

    private void reverse(int start, int k, char[] arr) {
        int l = start;
        int remain = arr.length - start;
        int r;
        if (remain < k) {
            r = arr.length - 1;
        } else {
            r = start + k - 1;
        }
        while (l < r) {
            char temp = arr[l];
            arr[l] = arr[r];
            arr[r] = temp;
            l++;
            r--;
        }
    }
}