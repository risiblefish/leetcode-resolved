package algorithms.math;

import java.util.ArrayList;
import java.util.List;

/**
 * 1447. 最简分数
 * @author Sean Yu
 */
public class No1447 {
    public static void main(String[] args) {
    }
}

/**
 * 暴力法模拟
 */
class Solution1447_I {
    public List<String> simplifiedFractions(int n) {
        List<String> res = new ArrayList();
        for (int i = 2; i <= n; i++) {
            for (int j = 1; j < i; j++) {
                if (isSimplified(i, j)) {
                    res.add(j + "/" + i);
                }
            }
        }
        return res;
    }

    private boolean isSimplified(int i, int j) {
        if (j == 1 && i != 1) {
            return true;
        }
        for (int k = 2; k <= j; k++) {
            if (j % k == 0 && i % k == 0) {
                return false;
            }
        }
        return true;
    }
}

/**
 * gcd法
 */
class Solution_II {
    public List<String> simplifiedFractions(int n) {
        List<String> res = new ArrayList();
        for (int i = 2; i <= n; i++) {
            for (int j = 1; j < i; j++) {
                if (gcd(i, j) == 1) {
                    res.add(j + "/" + i);
                }
            }
        }
        return res;
    }

    //辗转相除求最大公约数
    private int gcd(int i, int j) {
        int k = i % j;
        return k == 0 ? j : gcd(j, k);
    }
}
