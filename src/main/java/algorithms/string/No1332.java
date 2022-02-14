package algorithms.string;

/**
 * 1332. 删除回文子序列
 * @author Sean Yu
 * @date 22/1/2022 上午8:47
 */
public class No1332 {
}


class Solution1332 {
    public int removePalindromeSub(String s) {
        int l = 0, r = s.length() - 1;
        while(l < r){
            if(s.charAt(l) != s.charAt(r)){
                return 2;
            }
            l++;
            r--;
        }
        return 1;
    }
}