package algorithms.bitoperation;

/**
 * 461. 汉明距离
 * @author Sean Yu
 */
public class No461 {
    public static void main(String[] args) {
        System.out.println(new Solution461().hammingDistance(7, 11));
    }
}

/**
 *  思路：
 *  用无符号右移统计最右一位，利用A&1=A的性质
 *  整数最多32位，所以至多只需比较32次。
 */
class Solution461 {
    public int hammingDistance(int x, int y) {
        int rx, ry;
        int count = 0;
        for(int i = 0 ; i < 32 ; i++){
            rx = x >> i & 1;
            ry = y >> i & 1;
            if(rx != ry) {
                count++;
            }
        }
        return count;
    }
}
