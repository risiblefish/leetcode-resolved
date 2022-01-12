package algorithms.math;

/**
 * 390. 消除游戏
 * @author Sean Yu
 * @date 2/1/2022 下午6:37
 */
public class No390 {
}

/**
 * 思路：https://leetcode-cn.com/problems/elimination-game/solution/wo-hua-yi-bian-jiu-kan-dong-de-ti-jie-ni-k2uj/
 */
class Solution390 {
    public int lastRemaining(int n) {
        int head = 1, step =1;
        boolean fromL = true;

        while(n > 1){
            if(fromL || n % 2 == 1){
                head += step;
            }
            step = step + step;
            n /= 2;
            fromL = !fromL;
        }

        return head;
    }
}
