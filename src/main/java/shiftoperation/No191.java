package shiftoperation;

/**
 * 191. 位1的个数
 * @Author: Sean Yu
 * @Date: 2021/3/22 11:37
 */
public class No191 {
}

/**
 * 思路： 无符号右移，高位补0，每位与1相与，如果是1，则计数器+1
 * 复杂度： int至多32位，所以时间复杂度为常数
 */
class Solution191 {
    // you need to treat n as an unsigned value
    public int hammingWeight(int n) {
        int count = 0;
        while(n != 0){
            count += (n & 1);
            n >>>= 1;
        }
        return count;
    }
}
