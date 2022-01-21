package algorithms.search;

import java.util.*;

/**
 * 1345. 跳跃游戏 IV
 * @author Sean Yu
 * @date 21/1/2022 上午9:19
 */
public class No1345 {
    public static void main(String[] args) {
        int[] arr = new int[]{100, -23, -23, 404, 100, 23, 23, 23, 3, 404};
    }
}

/**
 * 思路：广搜
 */
class Solution1345 {
    public int minJumps(int[] arr) {
        int len = arr.length;
        int[] f = new int[len];
        Arrays.fill(f, Integer.MAX_VALUE);
        Map<Integer, List<Integer>> map = new HashMap();
        for (int i = 0; i < len; i++) {
            List<Integer> list = map.getOrDefault(arr[i], new ArrayList());
            list.add(i);
            map.put(arr[i], list);
        }
        f[0] = 0;
        Queue<Integer> q = new ArrayDeque();
        q.offer(0);
        while (!q.isEmpty()) {
            int i = q.poll();
            if (i == len - 1) {
                return f[i];
            }
            int step = f[i] + 1;
            if (i - 1 >= 0 && f[i - 1] == Integer.MAX_VALUE) {
                f[i - 1] = step;
                q.offer(i - 1);
            }
            if (i + 1 < len && f[i + 1] == Integer.MAX_VALUE) {
                f[i + 1] = step;
                q.offer(i + 1);
            }
            if (map.containsKey(arr[i])) {
                List<Integer> list = map.get(arr[i]);
                for (int nxt : list) {
                    if (f[nxt] == Integer.MAX_VALUE) {
                        f[nxt] = step;
                        q.offer(nxt);
                    }
                }
                map.remove(arr[i]);
            }
        }
        return f[len - 1];
    }
}
