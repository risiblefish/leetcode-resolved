package algorithms.search;

import java.util.*;

/**
 * 207. 课程表
 * @author Sean Yu
 * @create 2022/6/29 16:26
 */
public class No207 {
}

/**
 * 思路： 拓扑排序，当消除一个节点后，所有后继节点的计数-1
 */
class Solution207 {
    Map<Integer, Node> map;

    //存储preCnt为0的所有节点
    Set<Node> set;

    public boolean canFinish(int numCourses, int[][] prerequisites) {
        init(numCourses);
        for (int[] req : prerequisites) {
            int s = req[0];
            int l = req[1];
            Node sn = map.get(s);
            Node ln = map.get(l);
            ln.next.add(sn);
            sn.preCnt++;
            set.remove(sn);
        }
        //该队列只能放入preCnt为0的节点
        Queue<Node> q = new LinkedList();
        q.addAll(set);
        List<Integer> res = new ArrayList();
        while (!q.isEmpty()) {
            Node cur = q.poll();
            if (map.containsKey(cur.val)) {
                res.add(cur.val);
                update(q, cur.next);
            }
        }
        return res.size() == numCourses;
    }

    private void update(Queue<Node> q, List<Node> list) {
        for (Node node : list) {
            node.preCnt--;
            if (node.preCnt == 0) {
                q.add(node);
            }
        }
    }

    private void init(int n) {
        map = new HashMap();
        set = new HashSet();
        for (int i = 0; i < n; i++) {
            Node node = new Node(i);
            map.put(i, node);
            set.add(node);
        }
    }

    class Node {
        int val;
        int preCnt;
        List<Node> next;

        public Node(int v) {
            preCnt = 0;
            next = new ArrayList();
            val = v;
        }
    }
}