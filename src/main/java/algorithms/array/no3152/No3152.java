package algorithms.array.no3152;

/**
 * 3152. 特殊数组 II
 *
 * @author Sean Yu
 * @since 2024/5/20 14:38
 */

public class No3152 {
    public static void main(String[] args) {

    }
}

/**
 * 思路：
 * 利用前缀和来“找爸爸”
 * 比如{4,3,1,6,5,2,7}
 * start[i]表示到i为止，最长连续满足奇偶性的起点下标
 * start[i] = nums[i]%2 != nums[i-1]%2 ? start[i-1] : i
 * 题目保证数组至少包含1个数，如果数组只有1个数，它仍然满足奇偶性，start[0]=0
 * 那么对于{4,3,1,6}，对应地
 * 4前面没数了，起点就是它自己
 * 3前面是4，满足条件，所以3的起点就是前一个点4的起点，
 * 1前面是3，不满足，所以3的起点是它自己
 * 6的前面是3，满足，所以6的起点就是前一个3的起点
 * 同理，
 * start={0,0,2,2,2,2,2}
 * 对于每个from,to来说，如果它们是连续满足的，那么它们的起点一定相同，比如from=6，to=7，如果6到7连续满足，那么start[7]一定等于start[6]
 */
class Solution3152 {
    public boolean[] isArraySpecial(int[] nums, int[][] queries) {
        int[] start = new int[nums.length];
        start[0] = 0;
        for (int i = 1; i < start.length; i++) {
            start[i] = nums[i] % 2 != nums[i - 1] % 2 ? start[i - 1] : i;
        }
        boolean[] ans = new boolean[queries.length];
        for (int i = 0; i < queries.length; i++) {
            int from = queries[i][0];
            int to = queries[i][1];
            ans[i] = start[from] == start[to];
        }
        return ans;
    }
}
