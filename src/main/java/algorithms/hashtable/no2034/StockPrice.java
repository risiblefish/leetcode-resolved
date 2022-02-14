package algorithms.hashtable.no2034;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * 2034. 股票价格波动
 */
class StockPrice {
    int curr = 0;
    Map<Integer, Integer> map = new HashMap();
    TreeMap<Integer, Integer> ts = new TreeMap();

    public StockPrice() {

    }

    public void update(int timestamp, int price) {
        curr = Math.max(curr, timestamp);
        if(map.containsKey(timestamp)){
            int old = map.get(timestamp);
            int cnt = ts.get(old);
            if(cnt == 1){
                ts.remove(old);
            }else{
                ts.put(old, cnt - 1);
            }
        }
        map.put(timestamp, price);
        ts.put(price, ts.getOrDefault(price,0) + 1);
    }

    public int current() {
        return map.get(curr);
    }

    public int maximum() {
        return ts.lastKey();
    }

    public int minimum() {
        return ts.firstKey();
    }
}