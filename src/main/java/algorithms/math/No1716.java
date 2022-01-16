package algorithms.math;

/**
 * 1716. 计算力扣银行的钱
 * @author Sean Yu
 */
public class No1716 {
    public static void main(String[] args) {
        System.out.println(new Solution1716().totalMoney(20));
    }
}


class Solution1716 {
    public int totalMoney(int n) {
        int sum = 0;
        int curr = 1;
        for(int i = 1 ; i <= n ; i++){
            if(curr > 1 && i % 7 == 1){
                curr -= 6;
            }
            sum += curr++;
        }
        return sum;
    }
}