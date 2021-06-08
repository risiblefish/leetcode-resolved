package algorithms.hashtable;

import java.util.HashSet;
import java.util.Set;

/**
 * 202. 快乐数
 */
public class No202 {
}

/**
 * 每个数，每位的平方和，组成1个新的数，不断重复该步骤，如果该数最终为1，就是快乐数
 * 思路： 利用set去重
 */
class Solution202 {
    Set<Integer> set = new HashSet();

    public boolean isHappy(int n) {
        int sum = 0;
        while (n > 0) {
            int a = n % 10;
            sum += a * a;
            n = (n - a) / 10;
        }
        if (sum == 1) {
            return true;
        }
        if (set.contains(sum)) {
            return false;
        }
        set.add(sum);
        return isHappy(sum);
    }
}