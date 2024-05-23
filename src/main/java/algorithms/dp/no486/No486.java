package algorithms.dp.no486;

import java.util.Arrays;

/**
 * 486. 预测赢家
 * @author Sean Yu
 * @since 2024/5/22 14:39
 */

public class No486 {
    public static void main(String[] args) {
        var test = new Solution486_II();
        int[] nums = new int[]{1, 5, 2};
        System.out.println(test.predictTheWinner(nums));
    }
}


/**
 *
 */
class Solution486_I {
    int[] nums;

    public boolean predictTheWinner(int[] nums) {
        this.nums = nums;
        return f(0, nums.length - 1, true, 0, 0);
    }

    private boolean f(int l, int r, boolean isP1First, int p1Total, int p2total) {
        if (l > r) {
            return p1Total >= p2total;
        }
        if (isP1First) {
            boolean sim1 = f(l + 1, r, false, p1Total + nums[l], p2total);
            boolean sim2 = f(l, r - 1, false, p1Total + nums[r], p2total);
            return sim1 || sim2;
        } else {
            boolean sim3 = f(l + 1, r, true, p1Total, p2total + nums[l]);
            boolean sim4 = f(l, r - 1, true, p1Total, p2total + nums[r]);
            return sim3 && sim4;
        }
    }
}

/**
 * 思路：见解法II中的注释
 * 难点在于，【固定p1先手】，然后多做一层模拟
 */
class Solution486_II {
    int[] nums;

    public boolean predictTheWinner(int[] nums) {
        this.nums = nums;
        int total = Arrays.stream(nums).sum();
        int p1max = f(0, nums.length - 1);
        return p1max >= total - p1max;
    }

    /**
     * 返回在[l,r]上，【当p1先手时】，p1能拿到的最大分数
     */
    private int f(int l, int r) {
        if (l == r) {
            //因为p1先手，所以p1拿最后这个数
            return nums[l];
        }
        if (l + 1 == r) {
            //因为p1先手，p1拿更大的这个数
            return Math.max(nums[l], nums[r]);
        }
        //[l,r]上大于2个数
        //分情况讨论
        /*情况1： p1固定拿nums[l]
         * p2就在[l+1, r]上取数，具体地说，
         *
         * 1.1 p2要么取 nums[l+1], 即p1继续在[l+2, r]上取， 即f(l+2, r)
         *
         * 1.2 p2要么取 nums[r]，即p1继续在[l+1,r-1]上取，即f(l+1,r-1)
         *
         * 由于是0和博弈，p2很聪明，他会选择p1拿到最小值的情况，从而让自己收益最大化，即 min{ f(l+2,r), f(l+1,r-1) }
         * 所以对于情况1， p1的收益就是 nums[l] + min{ f(l+2,r), f(l+1,r-1) }
         *
         * 情况2： p1固定拿nums[r]
         * 即p2在[l,r-1]上取数，具体的说
         *
         * 2.1 p2要么取 nums[l],即p1继续在[l+1,r-1]上取，即f(l+1,r-1)
         *
         * 2.2 p2要么取 nums[r-1]，即p1继续在[l,r-2]上取，即f(l,r-2)
         * 由于是0和博弈，p2很聪明，他会选择p1拿到最小值的情况，从而让自己收益最大化，即 min{ f(l+1,r-1), f(l,r-2) }
         * 所以对于情况2，p1的收益就是 nums[r] + min{ f(l+1,r-1), f(l,r-2) }
         *
         * p1的最优解就是max{情况1， 情况2}
         */
        int p1 = nums[l] + Math.min(f(l + 2, r), f(l + 1, r - 1));
        int p2 = nums[r] + Math.min(f(l + 1, r - 1), f(l, r - 2));
        return Math.max(p1, p2);
    }
}

/**
 * 在II的基础上加缓存
 */
class Solution486_III {
    int[] nums;
    int[][] cache;

    public boolean predictTheWinner(int[] nums) {
        this.nums = nums;
        int n = nums.length;
        cache = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                //由于题目保证nums[i]>=0，所以可以初始化为-1表示没缓存到
                cache[i][j] = -1;
            }
        }
        int total = Arrays.stream(nums).sum();
        int p1max = f(0, nums.length - 1);
        return p1max >= total - p1max;
    }


    private int f(int l, int r) {
        if (cache[l][r] != -1) return cache[l][r];
        if (l == r) {
            cache[l][r] = nums[l];
            return cache[l][r];
        }
        if (l + 1 == r) {
            cache[l][r] = Math.max(nums[l], nums[r]);
            return cache[l][r];
        }
        int p1 = nums[l] + Math.min(f(l + 2, r), f(l + 1, r - 1));
        int p2 = nums[r] + Math.min(f(l + 1, r - 1), f(l, r - 2));
        cache[l][r] = Math.max(p1, p2);
        return cache[l][r];
    }
}

/**
 * DP版本
 * 从
 * int p1 = nums[l] + Math.min(f(l + 2, r), f(l + 1, r - 1));
 * int p2 = nums[r] + Math.min(f(l + 1, r - 1), f(l, r - 2));
 * 可以观察出，总是取决于更大的l，和更小的r
 * 所以尝试 l从大到小，r从小到大的方式来填充这个缓存表
 * 初始状态，即可以立即确认的值，l==r, l+1=r这2种情况
 */
class Solution486_IV {
    public boolean predictTheWinner(int[] nums) {
        int n = nums.length;
        int[][] dp = new int[n][n];
        //初始化r=l, r=l+1的情况
        for (int i = 0; i < n - 1; i++) {
            dp[i][i] = nums[i];
            dp[i][i + 1] = Math.max(nums[i], nums[i + 1]);
        }
        //上述for没有遍历到i=n-1
        dp[n - 1][n - 1] = nums[n - 1];

        //由于讨论的是区间[l,r]上，所以r>l才有意义，
        //前面r=l, r=l+1，已经初始化过了，所以下面从r=l+2开始讨论
        //l=n-1的时候，只有r=l=n-1这一种情况有意义，l=n-2的时候，r只有=l和l+1这2种情况才有意义，前述这些情况都已经初始化过了，所以l从n-3开始
        for (int l = n - 3; l >= 0; l--) {
            for (int r = l + 2; r < n; r++) {
                int p1 = nums[l] + Math.min(dp[l + 2][r], dp[l + 1][r - 1]);
                int p2 = nums[r] + Math.min(dp[l + 1][r - 1], dp[l][r - 2]);
                dp[l][r] = Math.max(p1, p2);
            }
        }
        int total = Arrays.stream(nums).sum();
        return dp[0][n - 1] >= total - dp[0][n - 1];
    }
}