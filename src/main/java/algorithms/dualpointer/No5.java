package algorithms.dualpointer;

/**
 * 5. 最长回文子串
 */
public class No5 {
}


/**
 * 思路：中心扩展法
 * 考虑回文奇数和偶数长度
 * 比如 1 2 3 4
 *     c b c d
 * 比如2这个位置的b， 我们能以b为中心开始向两边扩散， 也能以bc为中心向两边扩散
 */
class Solution5 {
    int L, max;
    public String longestPalindrome(String s) {
        char[] arr = s.toCharArray();
        max = 1;
        for(int i = 0 ; i < arr.length - 1 ; i++){
            findOdd(arr,i);
            findEven(arr,i);
        }
        return s.substring(L, L + max);
    }

    private void findOdd(char[] arr, int i){
        int l = i-1, r = i+1;
        int cnt = 1;
        while(l >= 0 && r < arr.length && arr[l] == arr[r]){
            cnt+=2;
            if(cnt > max){
                max = cnt;
                L = l;
            }
            l--;
            r++;
        }
    }

    private void findEven(char[] arr, int i){
        int l = i, r= i+1;
        int cnt = 0;
        while(l >= 0 && r < arr.length && arr[l] == arr[r]){
            cnt+=2;
            if(cnt > max){
                max = cnt;
                L = l;
            }
            l--;
            r++;
        }
    }
}