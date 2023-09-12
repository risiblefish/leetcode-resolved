package contest;

/**
 * @author Sean Yu
 */
public class No6909 {
    public static void main(String[] args) {
        Solution6909 test = new Solution6909();
        int[] arr = new int[]{4, 10,3};
        System.out.println(test.longestAlternatingSubarray(arr, 10));
    }
}

/**
 * nums[l] % 2 == 0
 * 对于范围 [l, r - 1] 内的所有下标 i ，nums[i] % 2 != nums[i + 1] % 2
 * 对于范围 [l, r] 内的所有下标 i ，nums[i] <= threshold
 */
class Solution6909 {
    public int longestAlternatingSubarray(int[] nums, int threshold) {
        int max = 0;
        for(int i  = 0 ; i < nums.length ; i++){
            int[] res = f(i, nums, threshold);
            max = Math.max(res[0], max);
            i = res[1];
        }
        return max;
    }

    private int[] f(int l, int[] nums, int threshold){
        int len = 0;
        while(l < nums.length && nums[l] % 2 == 0 && nums[l] <= threshold){
            len++;
            l++;
            if(l< nums.length && nums[l] % 2 == 1 && nums[l] <= threshold){
                len++;
                l++;
            }else {
                return new int[]{len, l};
            }
        }
        return new int[]{len, l};
    }

}
