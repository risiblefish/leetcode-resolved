package algorithms.dualpointer;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class No838 {
    public static void main(String[] args) {
        new Solution838().pushDominoes(".L.R...LR..L..");
    }
}

/**
 * 思路1 ： 模拟
 */
class Solution838 {
    public String pushDominoes(String dominoes) {
        char[] arr = dominoes.toCharArray();
        List<Integer> list = new ArrayList();
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == 'L' || arr[i] == 'R') {
                list.add(i);
            }
        }
        for (int i = 0; i < list.size(); i++) {
            int bound = list.get(i);
            if (arr[bound] == 'L') {
                if (i - 1 >= 0) {
                    int preIndex = list.get(i - 1);
                    char pre = arr[preIndex];
                    if (pre == 'L') {
                        int curr = preIndex;
                        while (curr < bound) {
                            arr[curr++] = 'L';
                        }
                    }
                    //pre == 'R'
                    else {
                        int curr = preIndex;
                        while (curr < bound) {
                            arr[curr++] = 'R';
                            arr[bound--] = 'L';
                        }
                    }
                } else {
                    while (bound >= 0) {
                        arr[bound--] = 'L';
                    }
                }
            }
            //arr[bound] == R
            else {
                if (i + 1 < list.size()) {
                    int nextIndex = list.get(i + 1);
                    char next = arr[nextIndex];
                    if (next == 'R') {
                        while (bound < nextIndex) {
                            arr[bound++] = 'R';
                        }
                    } else {
                        int curr = nextIndex;
                        while (bound < curr) {
                            arr[bound++] = 'R';
                            arr[curr--] = 'L';
                        }
                    }
                }
                //bound之后全部改为R
                else {
                    while (bound < arr.length) {
                        arr[bound++] = 'R';
                    }
                }
            }
        }
        return new String(arr);
    }
}

/**
 * 思路2 ： BFS
 */
class Solution838_II {
    public String pushDominoes(String dominoes) {
        char[] arr = dominoes.toCharArray();
        int n = arr.length;
        int[] t = new int[n];
        Deque<int[]> q = new ArrayDeque();
        for (int i = 0; i < n; i++) {
            if (arr[i] != '.') {
                int dir = arr[i] == 'L' ? -1 : 1;
                q.offer(new int[]{i, 1, dir});
                t[i] = 1;
            }
        }
        while (!q.isEmpty()) {
            int[] info = q.poll();
            int idx = info[0], time = info[1], dir = info[2];
            int nextIdx = idx + dir;
            if (nextIdx < 0 || nextIdx >= n) {
                continue;
            }
            //如果相邻的木板是第一次受力
            if (t[nextIdx] == 0) {
                q.offer(new int[]{nextIdx, time + 1, dir});
                //记录第1次受力时间
                t[nextIdx] = time + 1;
                arr[nextIdx] = dir == -1 ? 'L' : 'R';
            }
            //如果不是第一次受力
            else {
                //如果上次受力时间也是time+1，表示该木板将在time+1时间同时受到2个力，只能是来自左右的力
                if (t[nextIdx] == time + 1) {
                    arr[nextIdx] = '.';
                }
            }
        }
        return new String(arr);
    }
}
