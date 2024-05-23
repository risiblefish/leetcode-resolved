package algorithms.string.no12;

/**
 * 12. 整数转罗马数字
 * @author Sean Yu
 * @since 2024/5/23 13:57
 */

public class No12 {
}

/**
 * I	1
 * V	5
 * X	10
 * L	50
 * C	100
 * D	500
 * M	1000
 */
class Solution12_I {
    public String intToRoman(int num) {
        StringBuilder sb = new StringBuilder();
        //题目保证num < 3999
        //分情况讨论
        //>1000
        if (num >= 1000) {
            int mCnt = num / 1000;
            for (int i = 0; i < mCnt; i++) {
                sb.append("M");
            }
            num = num % 1000;
        }
        //num in [900, 1000]
        if (num >= 900) {
            sb.append("CM");
            num -= 900;
        }
        //num in [500, 900)
        if (num >= 500) {
            sb.append("D");
            num -= 500;
            int cCnt = num / 100;
            for (int i = 0; i < cCnt; i++) {
                sb.append("C");
                num -= 100;
            }
        }
        //num in [400, 500)
        if (num >= 400) {
            sb.append("CD");
            num -= 400;
        }
        //num in [100,400)
        if (num >= 100) {
            int cCnt = num / 100;
            for (int i = 0; i < cCnt; i++) {
                sb.append("C");
                num -= 100;
            }
        }
        //num in [90, 100)
        if (num >= 90) {
            sb.append("XC");
            num -= 90;
        }
        //num in [50,90)
        if (num >= 50) {
            sb.append("L");
            num -= 50;
            int xCnt = num / 10;
            for (int i = 0; i < xCnt; i++) {
                sb.append("X");
                num -= 10;
            }
        }
        //num in [40,50)
        if (num >= 40) {
            sb.append("XL");
            num -= 40;
        }
        //num in [10,40)
        if (num >= 10) {
            int xCnt = num / 10;
            for (int i = 0; i < xCnt; i++) {
                sb.append("X");
                num -= 10;
            }
        }
        //num is 9
        if (num == 9) {
            sb.append("IX");
            num -= 9;
        }
        //num in [5,8]
        if (num >= 5) {
            sb.append("V");
            num -= 5;
            int iCnt = num;
            for (int i = 0; i < iCnt; i++) {
                sb.append("I");
                num--;
            }
        }
        //num is 4
        if (num == 4) {
            sb.append("IV");
            num -= 4;
        }
        //num in [1,3]
        if (num >= 1) {
            int iCnt = num;
            for (int i = 0; i < iCnt; i++) {
                sb.append("I");
                num--;
            }
        }
        return sb.toString();
    }
}

/**
 * 通过上面的写法应该能体会到，就是
 * （1）【根据当前的数num】，选【能选的】最大罗马数，将它添加到解
 * （2）然后将num更新为 num减去这个罗马数对应的数，再重复(1)，直到num变为0
 */
class Solution12_II {
    StringBuilder sb = new StringBuilder();
    int[] numLookup = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
    String[] romanLookup = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};

    public String intToRoman(int num) {
        int n = numLookup.length;
        int i = 0;
        while (i < n) {
            while (num >= numLookup[i]) {
                sb.append(romanLookup[i]);
                num -= numLookup[i];
            }
            i++;
        }
        return sb.toString();
    }
}
