package algorithms.dp;

/**
 * 42. 接雨水
 *
 * @Author: Sean Yu
 * @Date: 2021/2/28 17:15
 */
public class No42 {
    public static void main(String[] args) {
        System.out.println(new Solution42().trap(new int[]{4, 2, 3}));
    }
}

/**
 * 解题思路：
 * 1.直觉（暴力法）
 * 通过直觉观察可以发现，对于每根柱子，能接住的雨量，等于 min(它左边最高的柱子高度，它右边最高柱子的高度)*1 - 它自己的高度*1
 * 所以暴力法，就是遍历每根柱子，对每根柱子，再找到它左右最高的柱子，进行上述计算，最后每根柱子能接住的雨量之和，就是解
 * 时间 O（n^2）
 * 空间 O（1）
 * <p>
 * 2.DP
 * 提前记录下每根柱子的左右两边最高柱子的高度，然后用暴力法中计算面积的方式进行计算。
 * 这种办法属于牺牲空间换时间。
 * 时间O(N)
 * 空间O(N)
 */
class Solution42 {
    public int trap(int[] height) {
        int n = height.length;
        int res = 0;
        int[] leftMax = new int[n];
        int[] rightMax = new int[n];
        int currMax = 0;
        for (int i = 0; i < n; i++) {
            currMax = Math.max(currMax, height[i]);
            leftMax[i] = currMax;
        }
        currMax = 0;
        for (int i = n - 1; i >= 0; i--) {
            currMax = Math.max(currMax, height[i]);
            rightMax[i] = currMax;
            res += count(height, leftMax, rightMax, i);
        }
        return res;
    }

    private int count(int[] height, int[] leftMax, int[] rightMax, int i) {
        int shorter = Math.min(leftMax[i], rightMax[i]);
        return shorter - height[i];
    }
}

/**
 * 优化：
 * 每个柱子能接的雨量 = min(左边最高的柱子，右边最高的柱子) - 自己的高度，如果为负数，则记0
 * 只需记录每根柱子右边最大的值，在计算出左边最大值之后，其实已经不用再存到数组里，因为这时已经可以求出它能承接的最大雨量了
 */
class Solution42_2 {
    public int trap(int[] height) {
        if (height == null || height.length == 0) {
            return 0;
        }
        int n = height.length;
        int[] rmax = new int[n];
        int max = 0;
        for (int i = n - 1; i >= 0; i--) {
            max = Math.max(height[i], max);
            rmax[i] = max;
        }
        max = 0;
        int sum = 0;
        for (int i = 0; i < n; i++) {
            max = Math.max(height[i], max);
            int count = Math.min(max, rmax[i]) - height[i];
            sum = count <= 0 ? sum : sum + count;
        }
        return sum;
    }
}
