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

    public int myAtoi(String s) {
        return getNum(getNumStr(s));
    }

    /**
     * 先将原s提取为 符号+数字 的格式
     */
    private StringBuilder getNumStr(String s) {
        //如果为空，直接返回
        if (s == null || s.length() == 0) {
            return null;
        }
        int n = s.length();
        char[] arr = s.toCharArray();
        //根据题意，不以空格，+，-，数字 开头一律返回0
        boolean isValid = (arr[0] == ' ' || arr[0] == '+' || arr[0] == '-' || Character.isDigit(arr[0]));
        if (!isValid) {
            return null;
        }
        int cur = 0;
        //处理字符开头的空格，注意越界
        while (cur < n && arr[cur] == ' ') {
            cur++;
        }
        StringBuilder sb = new StringBuilder();
        //处理完空格之后，处理符号位，注意越界
        if (cur < n && (arr[cur] == '-' || arr[cur] == '+')) {
            sb.append(arr[cur++]);
        } else {
            sb.append('+');
        }
        //处理完符号位之后，处理前导零，e.g. +000000123 -> +123
        while (cur < n && arr[cur] == '0') {
            cur++;
        }
        //处理完前导零之后，将[连续的数字]加入 e.g. 123hello456 -> 123, 后面的456要抛弃，因为算法要求遇到第一个非数字字符就要停止读取
        while (cur < n && Character.isDigit(arr[cur])) {
            sb.append(arr[cur]);
            cur++;
        }
        return sb;
    }

    private int getNum(StringBuilder sb) {
        if (sb == null || sb.length() == 0) {
            return 0;
        }
        boolean isNeg = sb.charAt(0) == '-';
        //int最大长度是10，加上符号位就是11位，所以超过这个长度的数，直接返回最大或者最小值
        if (sb.length() > 11) {
            return isNeg ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        }
        int cur = 1;
        long sum = 0;
        while (cur < sb.length()) {
            sum = sum * 10 + (sb.charAt(cur) - '0');
            cur++;
        }
        if (sum > Integer.MAX_VALUE) {
            return isNeg ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        }
        return isNeg ? (int) -sum : (int) sum;
    }
}
