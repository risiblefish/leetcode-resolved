package algorithms.dp;

/**
 * 10. 正则表达式匹配
 * @author Sean Yu
 * @create 2022/7/7 20:31
 */
public class No10 {
    public static void main(String[] args) {
        String s = "aab";
        String p = "c*a*b";
        System.out.println(new Solution10().isMatch(s, p));
    }
}

class Solution10 {
    char[] sarr, parr;
    int slen, plen;
    int[][] cache;
    public boolean isMatch(String s, String p) {
        sarr = s.toCharArray();
        parr = p.toCharArray();
        slen = sarr.length;
        plen = parr.length;
        if(!isSValid() || !isPValid()){
            return false;
        }
        cache = new int[slen+1][plen+1];
        return f(0,0);
    }

    private boolean f(int si, int pi){
        if(cache[si][pi] != 0){
            return cache[si][pi] == 1;
        }
        boolean ans = false;
        if(pi == plen){
            ans = si == slen;
        }
        //pi是最后一个 或着 pi不是最后一个，但pi+1处不是*
        else if(pi == plen - 1 || parr[pi+1] != '*'){
            ans = si < slen && (sarr[si] == parr[pi] || parr[pi] == '.') && f(si+1, pi+1);
        }
        //pi+1处是*
        else {
            //初始化，当si和pi处 无论是否相等时，都会考虑f(si, pi+2)这种情况
            ans = f(si, pi+2);
            //当si和pi处相等时，额外考虑f(si+1, pi)这种情况
            if(si < slen && (sarr[si] == parr[pi] || parr[pi] == '.')){
                ans |= f(si+1, pi);
            }
        }
        cache[si][pi] = ans ? 1 : -1;
        return ans;
    }

    private boolean isSValid(){
        for(char c : sarr){
            if(c == '*' || c == '.'){
                return false;
            }
        }
        return true;
    }

    private boolean isPValid(){
        if(parr[0] == '*'){
            return false;
        }
        for(int i = 1 ; i < plen ; i++){
            if(parr[i] == '*' && parr[i-1] == '*'){
                return false;
            }
        }
        return true;
    }
}