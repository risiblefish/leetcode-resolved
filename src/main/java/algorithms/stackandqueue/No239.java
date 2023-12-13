package algorithms.stackandqueue;

import java.util.LinkedList;
import java.util.PriorityQueue;

/**
 * No.239. 滑动窗口最大值
 *
 * @author Sean Yu
 * @create 2022/4/8 10:21 AM
 */
public class No239 {
    public static void main(String[] args) {
    }
}

/**
 * 思路1 ： 优先队列
 */
class Solution239_I {
    public int[] maxSlidingWindow(int[] nums, int k) {
        PriorityQueue<Info> q = new PriorityQueue<>((i1, i2) -> {
            if (i1.val == i2.val) {
                return i2.index - i1.index;
            }
            return i2.val - i1.val;
        });
        int[] res = new int[nums.length - k + 1];
        //初始化前k个窗口
        for (int i = 0; i < k; i++) {
            q.add(new Info(i, nums[i]));
        }
        //将第一个结果加入，此时窗口为[0,k-1]
        res[0] = q.peek().val;
        int l = 0, r = k;
        //窗口右边界从k开始
        while (r < nums.length) {
            //每次将右边界的值加入
            q.add(new Info(r, nums[r]));
            //算出对应的左边界
            l = r - k + 1;
            //不停将index小于左边界的值丢掉
            while (q.peek().index < l) {
                q.poll();
            }
            //此时index已经大于等于左边界，记录最大值
            res[l] = q.peek().val;
            //右边窗口右移
            r++;
        }
        return res;
    }

    class Info {
        int index;
        int val;

        public Info(int i, int v) {
            index = i;
            val = v;
        }
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
