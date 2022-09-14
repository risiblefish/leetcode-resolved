package algorithms.array;

/**
 * 327. 区间和的个数
 * @author Sean Yu
 */
public class No327 {
}

/**
 * 思路：
 * 这道题要在理解暴力法的情况下再去看归并排序的解法，归并排序可以看做是暴力解法的优化版本
 * 所以要先理解思路
 */

/**
 * 暴力法
 * 题意转换说人话就是：求arr有多少个子数组，满足子数组的和在[lower,upper]上
 * 子数组一定是以arr[i]结尾的，所以可以用前缀和来避免重复计算
 * 比如
 * 0,1,2,3 -> 前缀和数组： 0,1,3,6
 * 我们想知道子数组[2,3]的和，只需要用(0...3)的和减去(0..1)的和， 即sum(i,j) = sum(j) - sum(i-1) = 6-1 = 5
 * 这样，我们就能在o(n^2)的时间内求解
 */
class Solution327_Violent {
    long[] sum;
    public int countRangeSum(int[] nums, int lower, int upper) {
        sum = new long[nums.length];
        sum[0] = nums[0];
        for(int i = 1 ; i < nums.length ; i++){
            sum[i] = sum[i-1] + nums[i];
        }
        int cnt = 0;
        for(int l = 0 ; l < nums.length ; l++){
            for(int r = l ; r < nums.length ; r++){
                long intervalLR = l - 1 < 0 ? sum[r] : sum[r] - sum[l - 1];
                if(lower <= intervalLR && intervalLR <= upper){
                    cnt++;
                }
            }
        }
        return cnt;
    }
}

/**
 * 归并排序：
 * 第1步仍然是求前缀和数组arr
 * arr[k]表示原数组0-i的和
 * 那么[i,j]的区间和 = arr[j] - arr[i-1]
 * 如果我们希望这个区间和在[lower,upper]内，即  lower <= arr[j] - arr[i-1] <= upper
 * 如果arr[j]的值已知是x,  即 lower <= x - arr[i-1] <= upper
 * 那么 arr[i-1] 使得上述不等式成立的条件就是  x-upper <= arr[i-1] <= x - lower
 * 寻找满足这个条件的arr[i-1]，就是我们在merge的时候要做的事
 * 对于2个区间L  R， 我们需要对每个R中的x， 找到对应的L中满足 L[k]在[x-upper, x-lower]的k的数量
 * 又由于R是升序的，[x-upper, x-lower]的x会不断增大，整个区间也会不断增大
 * 我们用一个窗口（winL, winR）来表示落在这个区间内的数，由前述可知，winL, winR均在不断增大，不会回退，所以这是一个向右滑动的，时间复杂度为O(N)的遍历
 * 举个具体的例子：
 * 下标           0 1 2 3 4   5       6 7 8 9 10 11
 * 原数组         2 3 3 1 2   4      -9 1 1 1 1  1
 * 前缀和数组L =  [2,5,8,9,11,15] R = [6,7,8,9,10,11]  [lower,upper] = [-1,2]
 * winL【, winR 】 一开始指向2  [【2】,5,8,9,11,15]
 * 对于R中的每个x而言，
 * 对于6，目标区间为 [6-2, 6-(-1)] 即 [4,7], winL, winR均右移到合适的位置，[2,【5】,8,9,11,15] L中只有5在这个窗口中，找到1个
 * 5表示下标0~1的和，6表示0~6的和，所以6-5表示 下标2~6的和 = 6-5 = 1 落在了[-1, 2]区间
 * 对于7，同理，目标区间为[5,8]，    窗口调整后为：[2,【5,8】,9,11,15], 找到2个
 * 对于8，同理，目标区间为[6,9],     窗口调整后为：[2,5,【8,9】,11,15], 找到2个
 * 对于9，同理，目标区间为[7,10],    窗口调整后为：[2,5,【8,9】,11,15]，找到2个
 * 对于10，同理，目标区间为[8,11],   窗口调整后为：[2,5,【8,9,11】,15]， 找到3个
 * 对于11，同理，目标区间为[9,12],   窗口调整后为：[2,5,8,【9,11】,15]，找到2个
 *
 * 这道题的难点在于需要理解为什么可以用归并排序
 * 结合代码98-103行来看，归并排序厉害之处在于，每个数至多和其他数比较1次，这就意味着在归并过程中不会产生重复的结果
 * 而我们用的是前缀和数组进行归并排序，这意味着每2个 [0,,,i] 和 [0,,,,j]前缀和，至多产生1次比较
 *
 */
class Solution327 {
    long[] preSum, tmp;
    int lower, upper;

    public int countRangeSum(int[] nums, int lower, int upper) {
        preSum = new long[nums.length];
        preSum[0] = nums[0];
        for (int i = 1; i < nums.length; i++) {
            preSum[i] = preSum[i - 1] + nums[i];
        }
        tmp = new long[nums.length];
        this.lower = lower;
        this.upper = upper;
        return count(0, preSum.length - 1);
    }

    private int count(int l, int r) {
        if (l == r) {
            return lower <= preSum[l] && preSum[l] <= upper ? 1 : 0;
        }
        int mid = l + (r - l) / 2;
               //左半部分
        return count(l, mid)
                //右半部分
                + count(mid + 1, r)
                //左右合并产生的部分
                + merge(l, mid, r);
    }

    private int merge(int l, int m, int r) {
        int ans = 0;
        int winL = l, winR = l;
        //对右组的每个数，求左组满足条件的数
        for (int cur = m + 1; cur <= r; cur++) {
            long leftBound = preSum[cur] - upper;
            long rightBound = preSum[cur] - lower;
            while (winL <= m && preSum[winL] < leftBound) {
                winL++;
            }
            while (winR <= m && preSum[winR] <= rightBound) {
                winR++;
            }
            ans += winR - winL;
        }
        //merge排序
        int i = 0, p1 = l, p2 = m + 1;
        while (p1 <= m && p2 <= r) {
            tmp[i++] = preSum[p1] <= preSum[p2] ? preSum[p1++] : preSum[p2++];
        }
        while (p1 <= m) {
            tmp[i++] = preSum[p1++];
        }
        while (p2 <= r) {
            tmp[i++] = preSum[p2++];
        }
        for (int cur = l; cur <= r; cur++) {
            preSum[cur] = tmp[cur - l];
        }
        return ans;
    }
}

