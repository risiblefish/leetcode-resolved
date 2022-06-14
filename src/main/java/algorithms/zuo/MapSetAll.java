package algorithms.zuo;

import java.util.HashMap;
import java.util.Map;

/**
 * 要求，在不影响map原有功能的情况下，给map增加一个O(1)的方法setAll(T val),
 *
 * @author Sean Yu
 * @create 2022/6/14 10:26
 */
public class MapSetAll {
    public static void main(String[] args) {
        MyMap<Integer, Integer> map = new MyMap<>();
        map.put(1, 1);
        map.put(2, 2);
        map.setAll(3);
        System.out.println(map.get(1));
        map.remove(2);
        System.out.println(map.get(2));
    }
}

class MyMap<K, V> {
    private long setAllTime;
    private V allValue;
    private long curTime;
    Map<K, Long> timeMap = new HashMap<>();
    Map<K, V> map = new HashMap<>();

    public MyMap() {
        this.setAllTime = Long.MIN_VALUE;
    }

    public void put(K key, V value) {
        map.put(key, value);
        timeMap.put(key, ++curTime);
    }

    public V get(K key) {
        if (map.containsKey(key)) {
            return timeMap.get(key) > setAllTime ? map.get(key) : allValue;
        }
        return null;
    }

    public void setAll(V value) {
        setAllTime = ++curTime;
        allValue = value;
    }

    public void remove(K key){
        map.remove(key);
    }
}
