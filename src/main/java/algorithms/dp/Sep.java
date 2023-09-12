package algorithms.dp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Sean Yu
 */
public class Sep {
    public static void main(String[] args) {
        List<String> wordDict = Arrays.asList("car", "ca", "rs");
        String s  = "cars";
        System.out.println(new SolutionSep().wordBreak(s, wordDict));
        List<Integer> list = new ArrayList<>();

    }
}

class SolutionSep {
    String s;
    List<String> wordDict;
    int totalLen;
    int[] cache;
    public boolean wordBreak(String s, List<String> wordDict) {
        this.s = s;
        this.wordDict = wordDict;
        totalLen = s.length();
        cache = new int[totalLen];
        return f(0);
    }

    private boolean f(int i){
        if(i > totalLen){
            return false;
        }
        if(i == totalLen){
            return true;
        }
        if(cache[i] != 0){
            return cache[i] == 1;
        }
        for(String word : wordDict){
            int len = word.length();
            if(i + len <= totalLen && s.substring(i, i+len).equals(word)){
                if(f(i + len)){
                    cache[i] = 1;
                    return true;
                }
            }
        }
        cache[i] = -1;
        return false;
    }
}
