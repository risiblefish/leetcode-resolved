package algorithms.arrayandhash;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * @author Sean Yu
 */
public class No406 {
}

class Solution {
    public int[][] reconstructQueue(int[][] people) {
        Arrays.sort(people, (p1, p2) -> {
            if (p1[0] != p2[0]) {
                return p1[0] - p2[0];
            }
            return p1[1] - p2[1];
        });
        LinkedList<int[]> list = new LinkedList<>();
        for (int[] i : people) {
            list.add(i[1], i);
        }
        return list.toArray(new int[list.size()][2]);
    }
}
