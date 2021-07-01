package algorithms.string;

import java.util.*;

/**
 * @author Sean Yu
 */
public class No76 {
    public static void main(String[] args) {
        System.out.println(new Solution76().minWindow("A", "B"));
    }
}

class Solution76 {
    public String minWindow(String s, String t) {
        int tlen = t.length();
        int slen = s.length();
        if (tlen > slen) {
            return "";
        }
        Map<Character, Integer> tm = new HashMap();
        Map<Character, Integer> cm = new HashMap();
        int start = 0, end = slen;

        for (int i = 0; i < tlen; i++) {
            char c = t.charAt(i);
            tm.put(c, tm.getOrDefault(c, 0) + 1);
        }

        int count = 0;
        int l = 0, r = 0;
        //找到第一个包含t的子串
        while(r < slen){
            char c = s.charAt(r);
            if(tm.containsKey(c)){
                count++;
                cm.put(c,cm.getOrDefault(c,0) + 1);
                if(count >= tlen && isEqual(tm,cm)){
                    end = r;
                    break;
                }
            }
            r++;
        }

        if(end - start + 1 > slen){
            return "";
        }

        while(r < slen){
            if(count >= tlen && isEqual(tm,cm)){
                if(r - l < end - start){
                    start = l;
                    end = r;
                }
                char left = s.charAt(l);
                if(tm.containsKey(left)){
                    cm.put(left,cm.get(left) - 1);
                    count--;
                }
                l++;
            }
            else {
                r++;
                if(r == slen){
                    break;
                }
                char c = s.charAt(r);
                if(tm.containsKey(c)){
                    count++;
                    cm.put(c,cm.get(c) + 1);
                }
            }
        }

        return s.substring(start, end + 1);
    }

    private boolean isEqual(Map<Character,Integer> tm, Map<Character,Integer> cm){
        for (Map.Entry<Character, Integer> entry : tm.entrySet()) {
            if(entry.getValue() - cm.getOrDefault(entry.getKey(),0) > 0){
                return false;
            }
        }
        return true;
    }
}
