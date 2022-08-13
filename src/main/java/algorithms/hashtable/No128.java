package algorithms.hashtable;

import java.util.HashSet;
import java.util.Set;

/**
 * 128. 最长连续序列
 * @author Sean Yu
 */
public class No128 {
}


/**
 * 思路：
 *
 * 暴力方法是对每个nums[]中的x = nums[i]，判断x+1, x+2, ... 是否存在，然后更新最大长度
 * 所以先建一个set用于去重和查找
 * 剪枝 ： 对于任意一个数y， 如果y-1也在set中，那么说明y必然不是连续序列的起点，所以可以跳过，也就是我们只需要从那些可能作为序列起点的数开始向后查找
 */
class Solution128 {
    public int longestConsecutive(int[] nums) {
        Set<Integer> set = new HashSet();
        for (int i : nums) {
            set.add(i);
        }
        int ans = 0;
        //外层循环n个数
        for (int i : set) {
            if (set.contains(i - 1)) {
                continue;
            }
            int len = 0;
            int cur = i;
            //每个数只会进入1次内层循环，所以整体复杂度是O（N）
            while (set.contains(cur)) {
                len++;
                cur++;
            }
            ans = Math.max(ans, len);
        }
        return ans;
    }
}
