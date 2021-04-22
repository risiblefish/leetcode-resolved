package algorithms.arrayandhash;

/**
 * 209. 长度最小的子数组
 * @author Sean Yu
 */
public class No209 {
    public static void main(String[] args) {
        System.out.println(new Solution209().minSubArrayLen(7, new int[]{2, 3, 1, 2, 4, 3}));
    }
}

/**
 * 思路： 滑动窗口
 */
class Solution209 {
    public int minSubArrayLen(int target, int[] nums) {
        int n = nums.length;
        int l = 0, r = 0;
        int len = n+1;
        int sum = nums[0];
        //题目保证target >= 1
        do {
            if (sum >= target) {
                len = Math.min(len, r - l + 1);
                sum -= nums[l++];
            }else {
                if(r < n-1){
                    sum += nums[++r];
                }else {
                    break;
                }
            }
        } while (l <= r);
        return len <= n ? len : 0;
    }
}
