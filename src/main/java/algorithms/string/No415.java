package algorithms.string;

/**
 * 415. 字符串相加
 * @author Sean Yu
 */
public class No415 {
}

/**
 * 思路: 按照从低位到高位 竖式相加 进行模拟即可
 *
 */
class Solution415 {
    public String addStrings(String num1, String num2) {
        char[] n1 = num1.toCharArray();
        char[] n2 = num2.toCharArray();
        int p1 = n1.length - 1;
        int p2 = n2.length - 1;
        int flag = 0;
        StringBuilder sb = new StringBuilder();
        while(p1 >= 0 || p2 >= 0 || flag > 0){
            int add1 = p1 >= 0 ? n1[p1] - '0' : 0;
            int add2 = p2 >= 0 ? n2[p2] - '0' : 0;
            int sum = add1 + add2 + flag;
            flag = sum / 10;
            sb.append(sum % 10);
            p1--;
            p2--;
        }
        return sb.reverse().toString();
    }
}
