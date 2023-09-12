package algorithms.orderdlist;

/**
 * @author Sean Yu
 * @create 2022/4/28 08:38
 */
public class No327 {
    public static void main(String[] args) {
        int[] nums = new int[]{-2, 5, -1};
        int lower = -2;
        int upper = 2;
        System.out.println(new Solution327().countRangeSum(nums, lower, upper));
    }
}

class Solution327 {
    public int countRangeSum(int[] nums, int lower, int upper) {
        int[] pre = new int[nums.length];
        int sum = 0;
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            pre[i] = sum;
        }

        int cnt = 0;
        for (int l = 0; l < nums.length; l++) {
            for (int r = l; r < nums.length; r++) {
                int curSum = 0;
                if (l == 0) {
                    curSum = pre[r];
                } else {
                    curSum = pre[r] - pre[l - 1];
                }
                if (lower <= curSum && curSum <= upper) {
                    cnt++;
                }
            }
        }
        return cnt;
    }
}