package algorithms.string;

import java.util.Arrays;

/**
 * @author Sean Yu
 */
public class No6 {
    public static void main(String[] args) {
        System.out.println(new Solution6().convert("PAYPALISHIRING", 3));
    }
}

/**
 * 思路： 找规律
 * 通过观察发现：
 * 画"Z"就是2个步骤的往复：
 * （1）从下标为0的字符开始往下数numRows个字符时，反向往右上
 * （2）反向往右上数numRows-2个字符时，再反向往下
 * 其中，一共有个numRows行，所以可以开长度为numRows的字符串数组res[]，每个字符判断其属于哪行，就在res[i]里进行拼接
 * 用一个flag来标记当前处在哪个步骤中
 *
 * 初始时，是往下的，也就是在步骤（1）中的
 */
class Solution6 {
    public String convert(String s, int numRows) {
        int n = s.length();
        String[] res = new String[numRows];
        Arrays.fill(res,"");
        boolean reverse = false;
        int i = 0;
        while(i < n){
            if(!reverse){
                for(int k = 0 ; k < numRows && i < n; k++){
                    res[k] += s.charAt(i);
                    i++;
                }
                reverse = true;
            }else {
                for(int k = 0 ; k < numRows - 2 && i < n ; k++){
                    int index = (numRows - 1) - i % (numRows-1);
                    res[index] += s.charAt(i);
                    i++;
                }
                reverse = false;
            }
        }
        StringBuilder sb = new StringBuilder();
        for (i = 0; i < res.length; i++) {
            sb.append(res[i]);
        }
        return sb.toString();
    }
}
