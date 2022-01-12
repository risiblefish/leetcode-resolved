package algorithms.bitoperation;

import java.util.Random;

/**
 * @author Sean Yu
 * @date 2021/7/8 23:32
 */
public class NonEqRandToRand {

    public static void main(String[] args) {
        printTest();
    }

    public static int f() {
        return new Random().nextDouble() < 0.86 ? 0 : 1;
    }

    public static int y() {
        for (int i = f(); ; i = f()) {
            int j = f();
            if (j != i) {
                return j;
            }
        }
    }

    public static void printTest() {
        int[] res = new int[8];
        for (int i = 0; i < 1000000; i++) {
            res[y()]++;
        }
        for (int i = 0; i < res.length; i++) {
            System.out.println(String.format("%s 出现了 %s 次", i, res[i]));
        }
    }
}
