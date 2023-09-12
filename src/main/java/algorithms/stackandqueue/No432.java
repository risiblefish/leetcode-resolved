//package algorithms.stackandqueue;
//
//import java.util.HashMap;
//import java.util.Map;
//import java.util.PriorityQueue;
//
///**
// * @author Sean Yu
// * @create 2022/3/16 10:29 PM
// */
//public class No432 {
//}
//
//class AllOne {
//    Map<String, Node> map;
//    Node head, tail;
//
//    public AllOne() {
//        map = new HashMap<>();
//        head = new Node();
//        tail = new Node();
//    }
//
//    public void inc(String key) {
//        if(map.containsKey(key)){
//            Node node = map.get(key);
//            node.count ++;
//        }
//        else {
//            Node node = new Node();
//            node.key = key;
//            node.count = 1;
//            map.put(key,node);
//        }
//    }
//
//    public void dec(String key) {
//
//    }
//
//    public String getMaxKey() {
//
//    }
//
//    public String getMinKey() {
//
//    }
//
//
//    class Node{
//        String key;
//        Integer count;
//    }
//}
//
///**
// * Your AllOne object will be instantiated and called as such:
// * AllOne obj = new AllOne();
// * obj.inc(key);
// * obj.dec(key);
// * String param_3 = obj.getMaxKey();
// * String param_4 = obj.getMinKey();
// */