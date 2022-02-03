package algorithms.greed;

import java.util.ArrayList;
import java.util.List;

/**
 * 1414. 和为 K 的最少斐波那契数字数目
 * @author Sean Yu
 */
public class No1414 {
    public static void main(String[] args) {
        System.out.println(new Solution1414().findMinFibonacciNumbers(7));
    }
}

class Solution1414 {
    public int findMinFibonacciNumbers(int k) {
        List<Integer> list = new ArrayList();
        int n1 = 1, n2 = 1;
        list.add(n1);
        while(n2 <= k){
            list.add(n2);
            int n3 = n1 + n2;
            n1 = n2;
            n2 = n3;
        }
        int i = list.size() - 1;
        int cnt = 0, left = k;
        while(left > 0){
            cnt++;
            left -= list.get(i);
            if(left == 0){
                return cnt;
            }
            while(list.get(i) > left){
                i--;
            }
        }
        return cnt;
    }
}