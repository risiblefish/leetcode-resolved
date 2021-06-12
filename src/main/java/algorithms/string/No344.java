package algorithms.string;

/**
 * 344. 反转字符串
 */
public class No344 {
}

/**
 * 思路： 双指针
 */
class Solution344 {
    public void reverseString(char[] s) {
        if(s == null || s.length <= 1) {
            return;
        }
        int l = 0 , r = s.length - 1;
        while(l < r){
            char temp = s[l];
            s[l] = s[r];
            s[r] = temp;
            l++;
            r--;
        }
    }
}