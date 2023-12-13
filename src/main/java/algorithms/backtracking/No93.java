package algorithms.backtracking;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sean Yu
 */
public class No93 {
    public static void main(String[] args) {
        String s  = "25525511135";
        System.out.println(new Solution93().restoreIpAddresses(s));
    }
}

class Solution93 {
    List<String> res;
    String s;

    public List<String> restoreIpAddresses(String s) {
        res = new ArrayList();
        this.s = s;
        dfs("", 0, 0);
        return res;
    }


    private void dfs(String temp, int times, int startIdx){
        if(times == 4){
            if(startIdx == s.length()){
                res.add(temp.substring(0, temp.length() - 1));
            }
            else {
                return;
            }
        }

        for(int step = 0 ; step <= 2 ; step++){
            if(isLegal(startIdx, startIdx + step)){
                dfs(temp  + s.substring(startIdx, startIdx + step + 1) + ".", times + 1, startIdx + step + 1);
            }
        }
    }

    private boolean isLegal(int start, int end){
        //越界情况
        if(start >= s.length() || end >= s.length()) {
            return false;
        }
        int len  = end - start + 1;
        if(len > 3) {
            return false;
        }
        //长度大于1的时候不能有前导0
        if(len > 1 && s.charAt(start) == '0'){
            return false;
        }
        //长度为3的时候，不能大于255
        if(len == 3 && Integer.parseInt(s.substring(start, end + 1)) > 255){
            return false;
        }
        return true;
    }
}
