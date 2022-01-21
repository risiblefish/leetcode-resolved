package algorithms.string;

/**
 * 306. 累加数
 * @author Sean Yu
 */
public class No306 {
    public static void main(String[] args) {
        System.out.println(new Solution306().isAdditiveNumber("12012122436"));
    }
}

/**
 * 思路： 模拟
 */
class Solution306 {
    public boolean isAdditiveNumber(String num) {
        int n = num.length();
        int maxLen = n / 2;
        for (int len1 = 1; len1 <= maxLen; len1++) {
            for (int len2 = 1; len2 <= maxLen; len2++) {
                boolean flag = isValid(num, 0, len1, len1, len1 + len2);
                if (flag) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isValid(String num, int start1, int end1, int start2, int end2) {
        int maxLen = num.length();
        int len1 = end1 - start1;
        int len2 = end2 - start2;
        if (num.charAt(start1) == '0' && len1 > 1 || num.charAt(start2) == '0' && len2 > 1) {
            return false;
        }
        String num1 = num.substring(start1, end1);
        String num2 = num.substring(start2, end2);
        String num3 = Long.parseLong(num1) + Long.parseLong(num2) + "";
        int len3 = num3.length();
        int leftLen = maxLen - end2;
        //字符串剩余部分的长度大于num3的长度
        if (leftLen >= len3 && num3.equals(num.substring(end2, end2 + len3))) {
            //如果字符串剩余部分的长度刚好等于num3的长度，且num3匹配到了
            if (leftLen == len3) {
                return true;
            }
            return isValid(num, start2, end2, end2, end2 + len3);
        } else {
            return false;
        }
    }
}
