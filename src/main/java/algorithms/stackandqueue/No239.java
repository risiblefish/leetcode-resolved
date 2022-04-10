package algorithms.stackandqueue;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * No.239. 滑动窗口最大值
 * @author Sean Yu
 * @create 2022/4/8 10:21 AM
 */
public class No239 {
    public static void main(String[] args) {
        Solution239 test = new Solution239();
        int[] arr = new int[]{-7, -8, 7, 5, 7, 1, 6, 0};
        int k = 4;
        System.out.println(Arrays.toString(test.maxSlidingWindow(arr, k)));
    }
}

/**
 * 思路： 用一个双端队列，存储数的下标，队列内满足从头到尾降序，插入的新数x的方式是 一直弹出小于等于x的数，直到没有，再存入x的下标
 */
class Solution239 {
    LinkedList<Integer> q;
    int len;
    //res存储下标，而不是值，因为下标具有唯一性，值不具有唯一性
    int[] res;
    int l, r;
    int[] arr;

    public int[] maxSlidingWindow(int[] nums, int k) {
        arr = nums;
        q = new LinkedList();
        len = k;
        l = r = 0;
        res = new int[nums.length - k + 1];
        for (int i = 0; i < k - 1; i++) {
            add(i);
        }
        for (int r = k - 1; r < nums.length; r++) {
            add(r);
            res[r - (k - 1)] = arr[q.peek()];
        }
        return res;
    }

    private void add(int idx) {
        int cnt = r - l + 1;
        if (cnt > len) {
            if (l == q.getFirst()) {
                q.removeFirst();
            }
            l++;
        }
        while (!q.isEmpty() && arr[q.getLast()] <= arr[idx]) {
            q.removeLast();
        }
        q.addLast(idx);
        r++;
    }
}
