package algorithms.hashtable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 30. 串联所有单词的子串
 * @author Sean Yu
 */
public class No30 {
    /**
     * "wordgoodgoodgoodbestword"
     * ["word","good","best","good"]
     *
     * @param args
     */
    public static void main(String[] args) {
        System.out.println(new Solution30().findSubstring("wordgoodgoodgoodbestword", new String[]{"word", "good", "best", "good"}));
        System.out.println();
    }
}

/**
 * 思路：
 * 仔细读题，每个单词长度相同
 */
class Solution30 {
    public List<Integer> findSubstring(String s, String[] words) {
        Map<String, Integer> map = new HashMap();
        List<Integer> res = new ArrayList<>();
        int oneLen = words[0].length();
        int allLen = 0;
        for (String word : words) {
            allLen += word.length();
            map.put(word, map.getOrDefault(word, 0) + 1);
        }
        for (int i = 0; i + allLen <= s.length(); i++) {
            Map<String, Integer> temp = new HashMap<>();
            for (int j = i; j + oneLen <= i + allLen; j += oneLen) {
                String word = s.substring(j, j + oneLen);
                if (map.containsKey(word)) {
                    temp.put(word, temp.getOrDefault(word, 0) + 1);
                } else {
                    break;
                }
            }
            if (temp.equals(map)) {
                res.add(i);
            }
        }
        return res;
    }
}