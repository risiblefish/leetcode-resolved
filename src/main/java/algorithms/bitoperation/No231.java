package algorithms.bitoperation;

/**
 * 231. 2 的幂
 */
public class No231 {
    public static void main(String[] args) {
        new Solution231().isPowerOfTwo(16);
    }
}

/**
 * 思路：
 * 1：可以用位运算，由于记不住，这里不去深究。
 * 2： 由于题目保证n不超过2的31次方-1，所以可以提前算出2的30次方
 */
class Solution231{
    final static int POW_30 = 1 << 30;
    public boolean isPowerOfTwo(int n) {
        return n > 0 && (POW_30 % n == 0);
    }
}