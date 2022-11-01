package algorithms.greed;

/**
 * 2457. 美丽整数的最小增量
 * @author Sean Yu
 */
public class No2457 {
    public static void main(String[] args) {
        System.out.println(new Solution2457().makeIntegerBeautiful(6068060761L, 3));
    }
}

/**
 * 思路： 贪心
 * 如果原数n的位数和已经<=target，则直接返回0
 * 否则，需要让n的位数和变小，而要让位数和变小，只有让原来的数产生进位
 * 以n = 467, target = 1为例
 * 467位数和为17，进位后变为470，和降为11，继续进位为500，和降为5， 继续进位为1000，和降为1，满足
 * 所以要做的就是不断让n产生进位，然后判断位数和是否满足条件，同时需要记录产生进位需要加的数是多少
 * 具体的说，
 * 不断的让n      去模10，100，1000，10000,...
 * 对应地，加上的数就是10-n%10, 100- n%100, 1000-n%1000, ...
 */
class Solution2457 {
    public long makeIntegerBeautiful(long n, int target) {
        if (add(n) <= target) return 0;
        long mask = 10;
        //题目保证答案一定存在，因为target最小为1，所以这里能够用while true
        while (true) {
            long cur = mask - n % mask;
            if (add(n + cur) <= target) {
                return cur;
            }
            mask *= 10;
        }
    }

    private int add(long n) {
        int sum = 0;
        while (n != 0) {
            sum += n % 10;
            n /= 10;
        }
        return sum;
    }
}