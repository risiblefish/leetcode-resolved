package algorithms.dp;

/**
 * no.361 轰炸敌人 bomb enemy
 *
 * @author Sean Yu
 * @date 2020/11/30 16:46
 */
public class No361 {
    public static void main(String[] args) {
        System.out.println(new Solution361().maxKilledEnemies(new char[][]{{'0', '0', '0'}, {'E', 'E', 'E'}}));
    }
}

/**
 * 1. 分析最后一步
 * 因为炸弹爆炸后波及4个方向，可以先分析其中一个方向，然后举一反三。
 * <p>
 * 假设分析炸弹上方的情况，现在某个点（i,j）放炸弹，有
 * （1）如果该点是空格，那么上方能炸到的敌人数就是点(i-1,j)放炸弹时能炸到的敌人数 //前提是点(i-1,j)存在
 * （2）如果该点是敌人，那么上方能炸到的敌人数就是情况（1）+1
 * （3）如果该点是墙，那么上方能炸到的敌人数就是0
 * <p>
 * 子问题：由求g(i,j)上方能炸到的敌人数，变成了由点(i-1,j)上方能炸到的敌人数
 * <p>
 * 2. 确定状态
 * 令up(i,j)表示点[i,j]的上方能炸到的敌人数，那么
 * (1) 如果g[i][j] == 0, 则up(i,j) = up(i-1,j)
 * (2) 如果g[i][j] == e, 则up(i,j) = up(i-1,j) + 1
 * (3) 如果g[i][j] == w, 则up(i,j) = 0
 * <p>
 * 问： 假设有敌人的地方可以放炸弹，即情况（2），这种条件是如何想到的？
 * 答： 因为dp的核心是状态的转移/传递，如果我们令有敌人的地方，炸死人数为0的话，那么就无法统计和传递了，其实有敌人的地方不能放炸弹这个条件只是在初始情况下不能放。
 * 或者可以换个语义，理解为炸弹经过的地方，有没有敌人，有就+1。
 * <p>
 * 3. 确定初始状态和边界情况
 * 如果g[0][j] = 0或w, up(0,j) = 0
 * 如果g[0][j] = e, up(0,j) = 1
 * <p>
 * 4. 计算顺序
 * 从上到下，逐行计算
 * <p>
 * 5. 现在推广到4个方向，假设down(i,j),left(i,j),right(i,j)分别表示下，左，右能炸死的敌人数
 * 注意的是，计算顺序要小心，
 * 计算Up的时候，因为计算当前行的结果依赖于上一行的结果，所以是从上到下
 * 计算down的时候，因为计算当前行的结果依赖于下一行的结果，所以是从下到上
 * 计算left的时候，因为计算当前列的结果依赖于左边列的结果，所以是从左到右
 * 计算right的时候，因为计算当前列的结果依赖于右边列的结果，所以是从右到左
 */
class Solution361 {
    public int maxKilledEnemies(char[][] grid) {
        if (grid == null || grid.length == 0 || grid[0].length == 0) {
            return 0;
        }

        int rowNum = grid.length;
        int colNum = grid[0].length;

        int[][] dp = new int[rowNum][colNum];
        int[][] res = new int[rowNum][colNum];

        //分别计算grid[i][j]四个方向的炸到敌人数量
        for (int i = 0; i < rowNum; i++) {
            for (int j = 0; j < colNum; j++) {
                res[i][j] = 0;
            }
        }
        //上方，计算顺序：从上到下
        for (int i = 0; i < rowNum; i++) {
            for (int j = 0; j < colNum; j++) {
                if (grid[i][j] == 'W') {
                    dp[i][j] = 0;
                } else {
                    dp[i][j] = 0;
                    if (grid[i][j] == 'E') {
                        dp[i][j] = 1;
                    }
                    //如果有上面一行
                    if (i - 1 >= 0) {
                        dp[i][j] += dp[i - 1][j];
                    }
                }
                res[i][j] += dp[i][j];
            }
        }

        //下方, 计算顺序：从下到上
        for (int i = rowNum - 1; i >= 0; i--) {
            for (int j = 0; j < colNum; j++) {
                if (grid[i][j] == 'W') {
                    dp[i][j] = 0;
                } else {
                    dp[i][j] = 0;
                    if (grid[i][j] == 'E') {
                        dp[i][j] = 1;
                    }
                    //如果有下面一行
                    if (i + 1 < rowNum) {
                        dp[i][j] += dp[i + 1][j];
                    }
                }
                res[i][j] += dp[i][j];
            }
        }

        //左方， 计算顺序：从左到右
        for (int i = 0; i < rowNum; i++) {
            for (int j = 0; j < colNum; j++) {
                if (grid[i][j] == 'W') {
                    dp[i][j] = 0;
                } else {
                    dp[i][j] = 0;
                    if (grid[i][j] == 'E') {
                        dp[i][j] = 1;
                    }
                    //如果有左边一列
                    if (j - 1 >= 0) {
                        dp[i][j] += dp[i][j - 1];
                    }
                }
                res[i][j] += dp[i][j];
            }
        }

        //右方， 计算顺序：从右到左
        for (int i = 0; i < rowNum; i++) {
            for (int j = colNum - 1; j >= 0; j--) {
                if (grid[i][j] == 'W') {
                    dp[i][j] = 0;
                } else {
                    dp[i][j] = 0;
                    if (grid[i][j] == 'E') {
                        dp[i][j] = 1;
                    }
                    //如果有右边一列
                    if (j + 1 < colNum) {
                        dp[i][j] += dp[i][j + 1];
                    }
                }
                res[i][j] += dp[i][j];
            }
        }

        //计算完结果之后，再根据每个点是否是空格来初始化,因为只有空格的地方才能放炸弹，同时更新当前最大值
        int temp = 0;
        for (int i = 0; i < rowNum; i++) {
            for (int j = 0; j < colNum; j++) {
                if (grid[i][j] == '0') {
                    temp = Math.max(temp, res[i][j]);
                }
            }
        }
        return temp;
    }
}

