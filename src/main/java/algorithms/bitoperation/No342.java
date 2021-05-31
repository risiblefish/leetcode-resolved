package algorithms.bitoperation;

/**
 * 342. 4的幂
 */
public class No342 {
    public static void main(String[] args) {
        System.out.println(-3 % 2);
    }
}

/**
 * 思路：
 * <p>
 * 1. 4的幂 二进制中只存在1个1，且存在偶数位上， 构造一个所有奇数位为1，偶数位为0的数（0xaaaaaaaa）mask，如果与它相与，结果为0，则说明n的1出现在偶数位中
 * <p>
 * 2. 4的幂除以3，余数一定为1， 如果n是2的幂，且除以3余数为1，则是4的幂
 */
class Solution342 {
    public boolean isPowerOfFour_1(int n) {
        return (n > 0) && (n & (n - 1)) == 0 && (n & (0xaaaaaaaa)) == 0;
    }

    public boolean isPowerOfFour_2(int n) {
        //若n % 3 == 1，则 n 必然大于 0
        return (n & (n - 1)) == 0 && (n % 3 == 1);
    }
}