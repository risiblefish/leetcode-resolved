package algorithms.string;

/**
 * @author Sean Yu
 */
public class No8 {
    public static void main(String[] args) {
        String s = "-2147483648";
        System.out.println(new Solution8().myAtoi(s));
    }
}

/**
 * 思路，题目给出了算法，只需按算法模拟，然后处理好边界即可
 *
 * 算法如下：
 *
 * （1）读入字符串并丢弃无用的前导空格
 * （2）检查下一个字符（假设还未到字符末尾）为正还是负号，读取该字符（如果有）。 确定最终结果是负数还是正数。 如果两者都不存在，则假定结果为正。
 * （3）读入下一个字符，直到到达下一个非数字字符或到达输入的结尾。字符串的其余部分将被忽略。
 * （4）将前面步骤读入的这些数字转换为整数（即，"123" -> 123， "0032" -> 32）。如果没有读入数字，则整数为 0 。必要时更改符号（从步骤 2 开始）。
 * （5）如果整数数超过 32 位有符号整数范围 ，需要截断这个整数，使其保持在这个范围内。具体来说，小于 −2^31 的整数应该被固定为 −2^31 ，大于 2^31− 1 的整数应该被固定为 2^31− 1 。
 * （6）返回整数作为最终结果。
 *
 * 提示：
 * 0 <= s.length <= 200
 * s 由英文字母（大写和小写）、数字（0-9）、' '、'+'、'-' 和 '.' 组成
 */
class Solution8 {

    private boolean isNeg;
    StringBuilder builder;
    String s;
    int curIndex;

    public int myAtoi(String s) {
        init(s);
        //step 1 : left trim
        curIndex = leftTrim();
        if(curIndex == s.length()) return 0;
        //step 2 : check pos or neg
        isNeg = isNegative();
        //step 3 : build num string
        buildNumString();
        if(builder.length() == 0) return 0;
        //step 4 : parse int
        return parseInt();
    }

    private int parseInt(){
        int len = builder.length();
        //这里需要注意: 比如一个很长的数字字符串，连long类型都装不下，需要提前判定，int最大是10位，带符号位是11位
        if(len > 11){
            return isNeg ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        }
        long sum = 0;
        for(int i = 0 ; i < builder.length() ; i++){
            sum = sum * 10 + (builder.charAt(i) - '0');
        }
        if(isNeg){
            sum = -sum;
        }
        //这里之所以需要再判断一次，是考虑10位的情况下，是否有溢出
        if(sum > Integer.MAX_VALUE){
            return Integer.MAX_VALUE;
        }
        if(sum < Integer.MIN_VALUE){
            return Integer.MIN_VALUE;
        }
        return (int)sum;
    }

    private void buildNumString(){
        //ignore pre-0
        while(curIndex < s.length() && s.charAt(curIndex) == '0'){
            curIndex++;
        }

        while(curIndex < s.length()){
            char ch = s.charAt(curIndex);
            if(ch >= '0' && ch <= '9'){
                builder.append(ch);
                curIndex++;
            }else{
                break;
            }
        }
    }

    private boolean isNegative(){
        char ch = s.charAt(curIndex);
        if(ch == '-'){
            curIndex++;
            return true;
        }
        else if(ch == '+'){
            curIndex++;
            return false;
        }
        return false;
    }

    private int leftTrim(){
        int i;
        for(i = 0 ; i < s.length() ; i++){
            if(s.charAt(i) != ' '){
                break;
            }
        }
        return i;
    }

    private void init(String input){
        this.s = input;
        isNeg = false;
        builder = new StringBuilder();
    }
}
