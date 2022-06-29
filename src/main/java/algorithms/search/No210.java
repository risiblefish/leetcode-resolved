package algorithms.search;

import java.util.*;

/**
 * 210. 课程表2
 *
 * @author Sean Yu
 * @create 2022/6/29 16:28
 */
public class No210 {
}

/**
 * 思路： 同207题
 */
class Solution210 {
    Map<Integer, Node> map;
    Set<Node> set;

    public int[] findOrder(int numCourses, int[][] prerequisites) {
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
        List<Integer> res = new ArrayList();
        Queue<Node> q = new LinkedList();
        q.addAll(set);
        while (!q.isEmpty()) {
            Node cur = q.poll();
            res.add(cur.val);
            for (Node post : cur.next) {
                post.preCnt--;
                if (post.preCnt == 0) {
                    q.add(post);
                }
            }
        }
        if (res.size() != numCourses) {
            return new int[]{};
        }
        int[] ans = new int[numCourses];
        for (int i = 0; i < res.size(); i++) {
            ans[i] = res.get(i);
        }
        return ans;
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
            val = v;
            preCnt = 0;
            next = new ArrayList();
        }
    }
}