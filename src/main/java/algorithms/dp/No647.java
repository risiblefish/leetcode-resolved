package algorithms.dp;

/**
 * 647. 回文子串
 *
 * @author Sean Yu
 */
public class No647 {
    public static void main(String[] args) {
        System.out.println(new Solution647().countSubstrings("abc"));
    }
}

/**
 * 思路：
 * 用中心扩散的方法，找到所有回文子串
 * 回文串分为奇数长度和偶数长度
 * 对于奇数长度的回文，比如a|b|a，中轴就是b
 * 对于偶数长度的回文，如a|a，中轴就是aa之间
 * 所以，
 * 找到所有奇数长度的子串方法： 遍历每一个字符，以该字符为中轴，向两边扩散
 * 找到所有偶数长度的子串方法： 遍历每一个字符，以该字符为中轴左边的第一个字符，向两边扩散
 */
class Solution647 {
    public int countSubstrings(String s) {
        if (s == null) {
            return 0;
        }
        int n = s.length();
        char[] arr = s.toCharArray();
        int count = 0;
        for (int i = 0; i < n; i++) {
            //find all odd-len palindrome
            count++;
            int l = i - 1, r = i + 1;
            while (l >= 0 && r < n && l < r) {
                if (arr[l] == arr[r]) {
                    count++;
                    l--;
                    r++;
                } else {
                    break;
                }
            }
            //find all even-len palindrome
            l = i;
            r = i + 1;
            while (l >= 0 && r < n && l < r) {
                if(arr[l] == arr[r]){
                    count++;
                    l--;
                    r++;
                } else {
                    break;
                }
            }
        }
        return count;
    }
}