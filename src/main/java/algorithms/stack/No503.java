package algorithms.stack;

import java.util.Arrays;
import java.util.Stack;

/**
 * 503. 下一个更大元素 II
 * @author Sean Yu
 */
public class No503 {
    public static void main(String[] args) {
        System.out.println(new Solution503().nextGreaterElements(new int[]{1, 2, 1}));
    }
}

/**
 * 暴力法：
 * 对每个元素，注意查找下一个最大值
 *
 * 单调栈：
 * 注意到，如果某个子序列递减，那么它们的下一个最大值都是子序列中最大的数的下一个最大数
 * 比如6，5，4,8,2的中，存在递减子序列6，5，4，对于5和4而言，它们的下一个最大数，与6的下一个最大数相同，都是8
 * 栈中存放的，是【还未找到解的数的下标】
 * 当前遍历到的数 ：
 * （1）如果不比栈顶大（即小于等于栈顶元素），则入栈（如此一来，栈中的数从栈底到栈顶依次递减的，且栈顶元素是当前遍历过的数中最小的）
 * （2）如果大于栈顶元素，则对于栈顶元素而言，它的解就找到了，就是当前遍历的这个数。所以我们依次弹出栈中的元素，直到 栈空 或 当前遍历的数 再次小于等于 栈顶的数
 *
 * 那么一共要遍历多少次呢？
 * 至多2n-1次。 前n次遍历中，将每个数都至少入栈了一次，第一次遍历结束后，栈中至少还剩1个元素（完全递增序列），至多还剩n个元素（完全递减序列）
 * 至多只需再遍历n-1次就可找到所有解。
 */
class Solution503 {
    public int[] nextGreaterElements(int[] nums) {
        if (nums == null || nums.length == 0) {
            return nums;
        }
        int len = nums.length;
        int[] res = new int[len];
        Arrays.fill(res, -1);
        Stack<Integer> stack = new Stack();
        for (int i = 0; i < len + len; i++) {
            int idx = i % len;
            while (!stack.isEmpty() && nums[idx] > nums[stack.peek()]) {
                res[stack.pop()] = nums[idx];
            }
            stack.push(idx);
        }
        return res;
    }
}
