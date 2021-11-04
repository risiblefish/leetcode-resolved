package algorithms.bitoperation;

/**
 * 不使用四则运算符号，实现2个整数的四则运算
 *
 * @author Sean Yu
 */
public class BitAddMinusMultiDiv {
    public static void main(String[] args) {
        System.out.println(add(1, 2));
        System.out.println(minus(2, 3));
        System.out.println(multi(5, 6));
    }

    /**
     * 思路：
     * a + b = a和b的无进位相加之和 + 进位信息， 重复次过程，当进位信息为0时，无进位相加之和就是解
     */
    public static int add(int a, int b) {
        int sum = a;
        while (b != 0) {
            sum = a ^ b; //获得无进位相加之和
            b = (a & b) << 1; //获得进位信息
            a = sum;
        }
        return sum;
    }

    /**
     * 思路：
     * a - b = a + (-b), 其中 -b = 将b取反 + 1
     */
    public static int minus(int a, int b) {
        return add(a, ~b + 1);
    }

    /**
     * 思路： 模拟手动计算乘法的方式，先将a和b转成2进制，然后通过看b的每位上是否为0，来进行竖式计算
     * 可以手动模拟一下
     * 写法1
     */
    public static int multi(int a, int b) {
        int res = 0;
        for (int i = 0; i <= 31 && b != 0; i++) {
            if ((b & 1) == 1) {
                res = add(res, a << i);
            }
            b = b >>> 1;
        }
        return res;
    }

    /**
     * 写法2
     */
    public static int multi2(int a, int b) {
        int res = 0;
        while (b != 0) {
            if ((b & 1) == 1) {
                res = add(res, a);
            }
            b >>>= 1;
            a <<= 1;
        }
        return res;
    }

    public static int divide(int a, int b) {
        int res = 0;
        int x = isNegative(a) ? getNeg(a) : a;
        int y = isNegative(b) ? getNeg(b) : b;
        for (int i = 30; i >= 0; i = minus(i, 1)) {
            if ((x >> i) >= y) {
                res = res | (1 << i);
                x = minus(x, y << i);
            }
        }
        return isNegative(a) != isNegative(b) ? getNeg(res) : res;
    }

    private static boolean isNegative(int a) {
        return a < 0;
    }

    //取相反数
    private static int getNeg(int a) {
        if (a >= 0) {
            return minus(0, a);
        }
        return ~a + 1;
    }
}


