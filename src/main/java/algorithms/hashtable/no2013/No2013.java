package algorithms.hashtable.no2013;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Sean Yu
 * @date 26/1/2022 下午12:50
 */
public class No2013 {
    public static void main(String[] args) {
        DetectSquares sq = new DetectSquares();
        sq.add(new int[]{3, 10});
        sq.add(new int[]{11, 2});
        sq.add(new int[]{3, 2});
        sq.count(new int[]{11, 10});
        sq.count(new int[]{14, 8});
        sq.add(new int[]{11, 2});
        System.out.println(sq.count(new int[]{11, 10}));
    }
}

class DetectSquares {
    Map<Integer, Map<Integer, Integer>> map = new HashMap();

    public DetectSquares() {

    }

    public void add(int[] point) {
        int x = point[0];
        int y = point[1];
        Map<Integer, Integer> colMap;
        if (map.containsKey(x)) {
            colMap = map.get(x);
        } else {
            colMap = new HashMap();
        }
        colMap.put(y, colMap.getOrDefault(y, 0) + 1);
        map.put(x, colMap);
    }

    public int count(int[] point) {
        int x = point[0];
        int y = point[1];
        int count = 0;
        if (map.containsKey(x)) {
            Map<Integer, Integer> colMap = map.get(x);
            for (Map.Entry<Integer, Integer> e : colMap.entrySet()) {
                int y1 = e.getKey();
                if(y1 == y) continue; //重要，横坐标相同的2个点，纵坐标不能相同
                int cnt = e.getValue();
                int d = y1 - y;
                count += cnt * contains(x - d, y) * contains(x - d, y1);
                count += cnt * contains(x + d, y) * contains(x + d, y1);
            }
        }
        return count;
    }


    private int contains(int x, int y) {
        if (map.containsKey(x)) {
            return map.get(x).getOrDefault(y, 0);
        }
        return 0;
    }
}
