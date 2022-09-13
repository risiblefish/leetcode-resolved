package algorithms.array;

import java.util.ArrayList;
import java.util.List;

/**
 * 315. 计算右侧小于当前元素的个数
 * @author Sean Yu
 */
public class No315 {
    public static void main(String[] args) {
        int[] nums = new int[]{0, 1, 2};
        System.out.println(new Solution315().countSmaller(nums));
    }
}

/**
 * 思路： 计算逆序对的扩展
 * 这道题麻烦的地方在于，要对每个数比它小的数进行统计
 * 比如
 * 合并[25][13]这2个区间时，如何进行统计？
 * 方法是：在合并左边的数a的时候，看看右侧已经有多少个数被合并，这些已经被合并的数，都能对a作出贡献
 * 比如 ：
 * （1）初始时， p1指向2， p2指向1， 辅助数组tmp = []
 * （2）1 < 2，所以把右侧的数合并进tmp， 此时p1指向2， p2指向3， tmp = [1]
 *  (3)2 < 3, 所以把左侧的2合并进tmp， 此时tmp里有右侧合并来的1，这个1能在2右侧，能对2作出贡献，所以2的贡献+1， 此时p1指向5， p2指向3， tmp = [1,2]
 *  (4)3 < 5, 所以把右侧的3合并进tmp, 此时p1指向5， p2越界， 此时tmp = [1,2,3]
 *  (5)左侧还剩5，合并到tmp里，此时tmp里有右侧合并来的1,3，都能对5做贡献，所以5的贡献+2， 此时p1越界，p2越界， tmp = [1,2,3,5]
 *
 *  上面有2个问题，
 *  1. 如何计算已经合并的数里，有多少个右侧合并来的数？
 *  2. 将贡献值记录到哪里去？
 *
 *  解决1的方法是，用当前右侧的指针p2,减去初始p2的位置即m+1，就能得到当前已合并的数量 = p2 - (m + 1)
 *  解决2的方法是，如果能知道当前的数在原数组的下标i，然后有一个统计数组ans[]，ans[i]就能用来记录贡献值，也就是答案
 *  这里，我用一个自定义结构Node, 来记录nums[]中每个数的值和下标
 */
class Solution315 {
    int[] ans;
    Node[] arr, tmp;

    public List<Integer> countSmaller(int[] nums) {
        arr = new Node[nums.length];
        for (int i = 0; i < nums.length; i++) {
            arr[i] = new Node(nums[i], i);
        }
        ans = new int[arr.length];
        tmp = new Node[arr.length];
        sortAndCount(0, arr.length - 1);
        List<Integer> list = new ArrayList();
        for (int i = 0; i < ans.length; i++) {
            list.add(ans[i]);
        }
        return list;
    }

    private void sortAndCount(int l, int r) {
        if (l == r) return;
        int m = l + (r - l) / 2;
        sortAndCount(l, m);
        sortAndCount(m + 1, r);
        merge(l, m, r);
    }

    private void merge(int l, int m, int r) {
        int i = 0, p1 = l, p2 = m + 1;
        while (p1 <= m && p2 <= r) {
            if (arr[p1].val <= arr[p2].val) {
                ans[arr[p1].idx] += p2 - m - 1;
                tmp[i++] = arr[p1++];
            } else {
                tmp[i++] = arr[p2++];
            }
        }
        while (p1 <= m) {
            ans[arr[p1].idx] += p2 - m - 1;
            tmp[i++] = arr[p1++];
        }
        while (p2 <= r) {
            tmp[i++] = arr[p2++];
        }
        for (int cur = l; cur <= r; cur++) {
            arr[cur] = tmp[cur - l];
        }
    }

    class Node {
        int val;
        int idx;

        public Node(int v, int i) {
            val = v;
            idx = i;
        }
    }
}
