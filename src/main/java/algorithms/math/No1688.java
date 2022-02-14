package algorithms.math;

/**
 * 1688. 比赛中的配对次数
 * @author Sean Yu
 * @date 25/1/2022 下午12:46
 */
public class No1688 {
}

class Solution1688 {
    public int numberOfMatches(int n) {
        int res = 0;
        while(n > 1){
            int cnt = n / 2;
            res += cnt;
            if(n % 2 != 0){
                n = cnt + 1;
            }else {
                n = cnt;
            }
        }
        return res;
    }
}
