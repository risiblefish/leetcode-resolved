package algorithms.dp;

import java.util.List;

/**
 * 3148. 矩阵中的最大得分
 *
 * @author Sean Yu
 * @since 2024/5/13 17:13
 */
public class No3148 {
}

/**
 * 思路：
 *
 * 题目要求，（1）可以从矩阵中任意位置出发 （2）每次只能向下or向右移动 （3）不一定要相邻（4）至少移动1次
 * 需要同时满足上述3个条件的
 *
 * 用题目给的例子来进行分析
 * r\c 0 1 2 3
 * 0  [9,5,7,3]
 * 1  [8,9,6,1]
 * 2  [6,7,14,3]
 * 3  [2,5,3,1]
 *
 * 先只[从上往下]看第1列来寻找规律
 * r0c0位置的9没有产生移动，所以是一个无效点
 * r1c0位置的8，只可能从上面的9移动过来，所以到这个位置最大和是8-9=-1
 * r2c0位置的6，有3种路线，路线1: 9->8->6 和为(8-9)+(6-8)=-3 路线2：9->6 和为6-9=-3 路线3：8->6 和为6-8=-2 所以最大和为-2
 * 其中，对于形如a->b->c的路线，路径和为 b-a + c-b = c-a， 也就是路线2其实等效于路线1
 * 对于路线1的和，可以看看做 过点8的最大和 + 6到8的和， 过点8的最大和可以看做一个子问题
 * 归纳一下就是，上述3条路线，由于2条是等效的，所以变成
 * 经过点6的最大和，max{点6到它相邻点的和， 点6到它相邻点的和 + 经过它相邻点的最大和}
 * 很好理解，因为规定一定经过点6，所以【点6到它相邻点的和】是一定包含的，至于【经过它相邻点的最大和】，就看它的收益为为是否为正，正就加上
 * 公式可以改为 经过点6的最大和 = 经过它相邻点的最大和>0 ? 点6到它相邻点的和 + 经过它相邻点的最大和 : 点6到它相邻点的和
 * 由于r2c0这个6在地1列，所以它没有左边的点，所以不用考虑其左边的情况
 * 同理，对于经过点14的最大和，依然可以用max{点6到它相邻点的和， 点6到它相邻点的和 + 经过它相邻点的最大和}来求
 * 只是14的相邻点变成了2个（左边和上面）
 */
class Solution3148 {
    public int maxScore(List<List<Integer>> grid) {
        int rowLen = grid.size();
        int colLen = grid.get(0).size();
        //令dp[i][j]表示一定经过grid[i][j]的最大和
        int[][] dp = new int[rowLen + 1][colLen + 1];
        //dp[0][0]不存在，dp[0][1]和dp[1][0]
        dp[0][1] = grid.get(0).get(1) - grid.get(0).get(0);
        dp[1][0] = grid.get(1).get(0) - grid.get(0).get(0);
        //初始化最大值
        int ans = Math.max(dp[0][1], dp[1][0]);

        //更新只有1个相邻点的dp（即图的第一列元素，它们只有上面的点）
        for (int i = 2; i < rowLen; i++) {
            int curVal = grid.get(i).get(0);
            int upVal = grid.get(i - 1).get(0);
            dp[i][0] = Math.max(curVal - upVal, curVal - upVal + dp[i - 1][0]);
            //do not forget
            ans = Math.max(ans, dp[i][0]);

        }

        //更新只有1个相邻点的dp（即图的第一行元素，它们只有左边的点）
        for (int j = 2; j < colLen; j++) {
            int curVal = grid.get(0).get(j);
            int leftVal = grid.get(0).get(j - 1);
            dp[0][j] = Math.max(curVal - leftVal, curVal - leftVal + dp[0][j - 1]);
            //do not forget
            ans = Math.max(ans, dp[0][j]);
        }

        //更新有2个相邻点的dp
        for (int i = 1; i < rowLen; i++) {
            for (int j = 1; j < colLen; j++) {
                int curVal = grid.get(i).get(j);
                int upVal = grid.get(i - 1).get(j);
                int leftVal = grid.get(i).get(j - 1);
                int upmax = Math.max(curVal - upVal, curVal - upVal + dp[i - 1][j]);
                int leftmax = Math.max(curVal - leftVal, curVal - leftVal + dp[i][j - 1]);
                dp[i][j] = Math.max(upmax, leftmax);
                ans = Math.max(ans, dp[i][j]);
            }
        }
        return ans;
    }
}
