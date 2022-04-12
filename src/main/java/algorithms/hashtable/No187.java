package algorithms.hashtable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * No. 187. 重复的DNA序列
 * @author Sean Yu
 * @create 2022/4/12 9:27 AM
 */
public class No187 {
}

class Solution187 {
    public List<String> findRepeatedDnaSequences(String s) {
        List<String> res = new ArrayList();
        Map<String,Integer> map = new HashMap();
        for(int r = 10 ; r <= s.length() ; r++){
            String sub = s.substring(r-10, r);
            int cnt = map.getOrDefault(sub, 0)+1;
            if(cnt == 2){
                res.add(sub);
            }
            map.put(sub, cnt);
        }
        return res;
    }
}
