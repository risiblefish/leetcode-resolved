package algorithms.greed;

import java.util.PriorityQueue;

/**
 * 502. IPO
 * @author Sean Yu
 * @date 25/1/2022 下午8:57
 */
public class No502 {
    public static void main(String[] args) {
        int k = 2;
        int w = 0;
        int[] profits = new int[]{1,2,3};
        int[] capital = new int[]{0,1,1};
        System.out.println(new Solution502().findMaximizedCapital(k, w, profits, capital));
    }
}


class Solution502 {
    public int findMaximizedCapital(int k, int w, int[] profits, int[] capital) {
        PriorityQueue<Project> minCQ = new PriorityQueue<Project>((p1, p2) -> p1.c - p2.c);
        PriorityQueue<Project> maxPQ = new PriorityQueue<Project>((p1, p2) -> p2.p - p1.p);
        int n = profits.length;
        for (int i = 0; i < n; i++) {
            minCQ.add(new Project(capital[i], profits[i]));
        }
        int cnt = 0;
        while (cnt < k) {
            while (!minCQ.isEmpty() && minCQ.peek().c <= w){
                maxPQ.add(minCQ.poll());
            }
            if(maxPQ.isEmpty()){
                break;
            }else {
                Project pj = maxPQ.poll();
                w += pj.p;
                cnt++;
            }
        }
        return w;
    }

    static class Project {
        public int c;
        public int p;

        public Project(int cap, int pro) {
            c = cap;
            p = pro;
        }
    }
}