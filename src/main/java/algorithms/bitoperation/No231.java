package algorithms.bitoperation;

/**
 * 231. 2 的幂
 */
public class No231 {
    public static void main(String[] args) {
        new Solution231().isPowerOfTwo_1(16);
    }
}

/**
 * 思路：
 * 1：可以用位运算（有性质 n & (n-1) == 0），
 *    原理，2的n次方，其二进制中，只包含1个1，且在最高位，其余为0。 n & (n-1) 可以移除n最低位的1， 再判断余数是否能被2整除
 * 2： 由于题目保证n不超过2的31次方-1，所以可以提前算出2的30次方
 */
class Solution231{
    final static int POW_30 = 1 << 30;
    public boolean isPowerOfTwo_1(int n) {
        return n > 0 && (n & (n-1)) == 0;
    }
    public boolean isPowerOfTwo_2(int n) {
        return n > 0 && (POW_30 % n == 0);
    }
}