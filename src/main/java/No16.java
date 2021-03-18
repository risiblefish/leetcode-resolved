import java.util.Arrays;

/**
 * 16. 最接近的三数之和
 * 
 * @author Sean Yu
 */
public class No16 {
    public static void main(String[] args) {
        new Solution16().threeSumClosest(new int[]{0, 0, 0}, 1);
    }
}

/**
 * 思路： 排序+双指针
 * 和三数之和类似。最接近即保存一个和target当前最接近的和，并在后续遍历中不断更新。
 * 如果有 a + b + c == target
 * 如果c是已知的，只需要求a + b = target - c 即可
 * 令c = nums[i], i = 0 ~ n-1
 * 令l和r是c之后的最左端和最右端，夹逼法来逼近target
 *
 * 为什么l和r在c之后？
 * 这是因为c之前的已经遍历过，假如此时c是nums[1]，那么如果l在c左边，就是nums[0]，这种情况在c = nums[0]时已经遍历过
 *
 * 时间复杂度：N^2
 */
class Solution16 {
    public int threeSumClosest(int[] nums, int target) {
        Arrays.sort(nums);
        int n = nums.length;
        int res = nums[0] + nums[1] + nums[2];
        //因为i后面至少有2个数
        for (int i = 0; i < n - 2; i++) {
            int l = i + 1, r = n - 1;
            int t = target - nums[i];
            while (l < r) {
                int sum = nums[l] + nums[r];
                if (sum == t) {
                    return target;
                } else if (sum < t) {
                    res = Math.abs(res - target) < Math.abs(sum - t) ? res : nums[i] + sum;
                    l++;
                } else {
                    res = Math.abs(res - target) < Math.abs(t - sum) ? res : nums[i] + sum;
                    r--;
                }
            }
        }
        return res;
    }
}
