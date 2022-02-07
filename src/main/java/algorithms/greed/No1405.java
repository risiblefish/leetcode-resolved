package algorithms.greed;

import java.util.PriorityQueue;

/**
 * 1405. 最长快乐字符串
 * @author Sean Yu
 */
public class No1405 {
    public static void main(String[] args) {
        System.out.println(new Solution1405().longestDiverseString(7, 1, 0));
    }
}

/**
 * 思路： 贪心 + 优先队列
 *
 * 每次尽量选数量最多的字符
 */
class Solution1405 {
    public String longestDiverseString(int a, int b, int c) {
        PriorityQueue<MC> q = new PriorityQueue<MC>((mc1, mc2) -> {
            return mc2.count - mc1.count;
        });

        if (a > 0) {
            q.add(new MC('a', a));
        }
        if (b > 0) {
            q.add(new MC('b', b));
        }
        if (c > 0) {
            q.add(new MC('c', c));
        }
        StringBuilder sb = new StringBuilder();

        MC prev = null;
        MC pprev = null;
        while (q.size() > 0) {
            MC curr = q.poll();
            //如果连续3个一样，就再弹一个出栈
            if (curr == prev && prev == pprev) {
                if (q.size() == 0) {
                    break;
                }
                curr = q.poll();
                q.add(prev);
            }
            sb.append(curr.c);
            curr.count--;

            if (curr.count > 0) {
                q.add(curr);
            }
            pprev = prev;
            prev = curr;
        }

        return sb.toString();
    }

    static class MC {
        char c;
        int count;

        public MC(char cc, int cnt) {
            c = cc;
            count = cnt;
        }
    }
}

