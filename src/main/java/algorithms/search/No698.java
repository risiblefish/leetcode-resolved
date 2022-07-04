package algorithms.search;

import java.util.Arrays;

/**
 * 698. 划分为k个相等的子集
 *
 * @author Sean Yu
 * @create 2022/7/5 06:29
 */
public class No698 {
}

/**
 * 思路： https://leetcode.cn/problems/partition-to-k-equal-sum-subsets/solution/by-lfool-d9o7/
 */
class Solution698 {
    int k, n;
    int[] nums;
    int target;

    public boolean canPartitionKSubsets(int[] nums, int k) {
        int sum = 0;
        for (int i : nums) {
            sum += i;
        }
        //剪枝1： 如果不能均摊到每个组，则直接失败
        if (sum % k != 0) {
            return false;
        }
        target = sum / k;
        n = nums.length;
        //剪枝2： 提高剪枝： if (sum[i] + nums[idx] > target) 命中率
        descSort(nums);
        this.nums = nums;
        this.k = k;
        return f(0, new int[k + 1]);
    }

    // 降序排列
    private void descSort(int[] arr) {
        Arrays.sort(arr);
        int l = 0, r = arr.length - 1;
        while (l < r) {
            int temp = arr[l];
            arr[l] = arr[r];
            arr[r] = temp;
            l++;
            r--;
        }
    }

    private boolean f(int idx, int[] sum) {
        if (idx == n) {
            //剪枝6： 能到达此处，不需要再判断每个元素值是否为target，因为剪枝1和剪枝3能保证
            return true;
        }
        boolean find = false;
        for (int i = 0; i < k; i++) {
            //剪枝3：如果超过target，则一定不能满足
            if (sum[i] + nums[idx] > target) {
                continue;
            }
            //剪枝4：如果当前的和与前一个和相等，把当前的数加到前面sum[i-1]与加到sum[i]效果一样，跳过
            if (i > 0 && sum[i] == sum[i - 1]) {
                continue;
            }
            //剪枝5： 第1个数放到任意一个sum[i]中效果一样，所以这里规定只放到sum[0]
            if (i > 0 && idx == 0) {
                break;
            }
            sum[i] += nums[idx];
            find = f(idx + 1, sum);
            if (find) {
                return true;
            }
            sum[i] -= nums[idx];
        }
        return false;
    }
}
