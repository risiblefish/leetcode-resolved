package algorithms.hashtable;

import java.util.HashMap;
import java.util.Map;

/**
 * LRU
 * @author Sean Yu
 * @create 2022/3/17 9:41 AM
 */
public class No146 {
    public static void main(String[] args) {
        System.out.println("yodn".compareTo("ewqz"));
//        /**
//         * ["LRUCache","get", "put",  "get",   "put",     "put",   "get"   ,"get"]
//         * [[2]        ,[2]  ,[2,6]  ,[1],      [1,5]    ,[1,2]    ,[1]    ,[2]]
//         */
//        LRUCache lRUCache = new LRUCache(2);
//        System.out.println(lRUCache.get(2));
//        lRUCache.put(2, 6);
//        System.out.println(lRUCache.get(1));
//        lRUCache.put(1, 5);
//        lRUCache.put(1, 2);
//        System.out.println(lRUCache.get(1));
//        System.out.println(lRUCache.get(2));
    }
}

class LRUCache {
    Node head, tail;
    Map<Integer,Node> map;
    int cap;
    public LRUCache(int capacity) {
        map = new HashMap();
        head = new Node();
        tail = new Node();
        head.next = tail;
        tail.prev = head;
        cap = capacity;
    }

    public int get(int key) {
        if(map.containsKey(key)){
            Node node = map.get(key);
            Node p = node.prev;
            Node n = node.next;
            //让node称为孤点，让node前后节点相连
            p.next = n;
            n.prev = p;
            //让node和head相连
            Node nodeNext = head.next;
            head.next = node;
            node.prev = head;
            node.next = nodeNext;
            nodeNext.prev = node;
            return node.val;
        }
        else {
            return -1;
        }
    }

    public void put(int key, int value) {
        int val = get(key);
        //如果node存在，因为get()会自动调整其位置到head之后，所以这里只用调整value
        if(val != -1){
            Node node = map.get(key);
            node.val = value;
        }
        //否则，新建一个并插入到head之后
        else {
            //如果满了，删除最久没用的node
            if(map.size() == cap){
                Node last = tail.prev;
                map.remove(last.key);
                Node p = last.prev;
                p.next = tail;
                tail.prev = p;
            }
            Node node = new Node();
            node.key = key;
            node.val = value;
            map.put(key, node);

            //将node插入head之后
            Node n = head.next;
            head.next = node;
            node.prev = head;
            node.next = n;
            n.prev = node;
        }
    }

    class Node{
        int key;
        int val;
        Node prev;
        Node next;
    }
}

/**
 * Your LRUCache object will be instantiated and called as such:
 * LRUCache obj = new LRUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */
