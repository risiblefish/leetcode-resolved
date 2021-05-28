package algorithms.arrayandhash;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 554. 砖墙
 * @author Sean Yu
 */
public class No554 {
    /**
     * [1,2,2,1],[3,1,2],[1,3,2]
     *
     * @param args
     */
    public static void main(String[] args) {
        List<Integer> r1 = Arrays.asList(1, 2, 2, 1);
        List<Integer> r2 = Arrays.asList(3, 1, 2);
        List<Integer> r3 = Arrays.asList(1, 3, 2);
        List<Integer> r4 = Arrays.asList(2, 4);
        List<Integer> r5 = Arrays.asList(1, 3, 1, 1);
        new Solution554().leastBricks(Arrays.asList(r1, r2, r3, r4, r5));
    }
}

/**
 * 思路：利用hash表记录每行间隙出现的次数
 * https://leetcode-cn.com/problems/brick-wall/solution/gong-shui-san-xie-zheng-nan-ze-fan-shi-y-gsri/
 * 比如有2行砖，每行的砖块长度分别为1，2，2，1 和 1，3，2
 * 那么，
 * 第1行砖（不算左右两端）的间隙处就是 1，3，5
 * 第2行砖的间隙处就是1，4
 * 可以发现，每一行在长度为1的地方出现间隙，那么从这里垂直下来，就是通畅的，即不会遇到砖
 * 所以思路就是：
 * （1）记录每一行出现的间隙
 * （2）每个间隙在每行至多只会出现1次
 * （3）找出出现最多次数的间隙，然后用砖的行数 - 最多次数，就是穿过最少的砖块数
 */
class Solution554 {
    public int leastBricks(List<List<Integer>> wall) {
        Map<Integer, Integer> map = new HashMap();
        int max = 0;
        for (List<Integer> row : wall) {
            int sum = 0;
            //i < row.size()-1是为了不计算墙右边的间隙
            for (int i = 0; i < row.size() - 1; i++) {
                sum += row.get(i);
                int count = map.getOrDefault(sum, 0) + 1;
                map.put(sum, count);
                max = Math.max(count, max);
            }
        }
        return wall.size() - max;
    }
}