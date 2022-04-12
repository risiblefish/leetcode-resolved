package algorithms.zuo;

import java.util.Random;

/**
 * 给你一个正整数，返回离它最近的2的某次方
 * @author Sean Yu
 * @create 2022/4/13 7:39 AM
 */
public class Nearest2Pow {
    public static void main(String[] args) {
        System.out.println(f(3));
        Random rand = new Random();
        for (int i = 0; i < 10; i++) {
            int n = rand.nextInt(100);
            System.out.println("n = " + n + ", nearest 2 pow is " + f(n));
        }
    }

    /**
     * 思路：
     * 把n最高位的1右边全部变为1，比如n=14, n-1= 13 = 1101 ， 一通操作后，变为1111，再+1 = 10000 = 16
     * 之所以要n--, 是比如n就是2的某次方的时候，如果不--，得到的将是2n
     * @param n
     * @return
     */
    private static int f(int n){
        n--;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : n + 1;
    }
}
