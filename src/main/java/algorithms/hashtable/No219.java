package algorithms.hashtable;

import java.util.HashMap;
import java.util.Map;

/**
 * 219. 存在重复元素 II
 * @author Sean Yu
 * @date 19/1/2022 下午8:45
 */
public class No219 {
}

class Solution219 {
    public boolean containsNearbyDuplicate(int[] nums, int k) {
        Map<Integer,Integer> map = new HashMap();
        for(int i = 0 ; i < nums.length ; i++){
            if(map.containsKey(nums[i])){
                int pre = map.get(nums[i]);
                if(Math.abs(pre - i) <= k){
                    return true;
                }
            }
            map.put(nums[i],i);
        }
        return false;
    }
}
