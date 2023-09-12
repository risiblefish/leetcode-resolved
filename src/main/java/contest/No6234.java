package contest;

/**
 * @author Sean Yu
 */
public class No6234 {
    public static void main(String[] args) {
        int[] nums = {2, 1, 5, 5};
        int k = 1;
        System.out.println(new Solution6234().subarrayLCM(nums, k));
    }
}

class Solution6234 {
    public int subarrayLCM(int[] nums, int k) {
        //连续最小公倍数为K的长度
        int h1 = k % nums[0] == 0 ? 1 : 0;
        //以当前下标结尾的满足条件的数量
        int h2 = nums[0] == k ? 1 : 0;
        int ans = h2;
        for (int i = 1; i < nums.length; i++) {
            if (k % nums[i] == 0) {
                h1++;
                if (nums[i] == k) {
                    h2 = h1;
                }
            } else {
                h1 = 0;
                h2 = 0;
            }
            ans += h2;
        }
        return ans;
    }
}
