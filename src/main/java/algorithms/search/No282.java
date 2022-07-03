package algorithms.search;

import java.util.ArrayList;
import java.util.List;

/**
 * 282. 给表达式添加运算符
 * @author Sean Yu
 * @create 2022/7/3 10:04
 */
public class No282 {
}

class Solution282 {
    List<String> res;
    String num;
    int n, target;

    public List<String> addOperators(String num, int target) {
        this.target = target;
        this.num = num;
        n = num.length();
        res = new ArrayList();
        for (int step = 1; step <= n; step++) {
            String exp = num.substring(0, step);
            IntWrapper wrapper = getInt(exp);
            if (wrapper.isValid) {
                f(step, wrapper.val, wrapper.val, exp);
            }
        }
        return res;
    }

    /**
     *
     * @param start 当前搜索的起始位置
     * @param sum   之前的和
     * @param pre   前一个操作数
     * @param exp   当前已构造出的表达式
     */
    private void f(int start, long sum, long pre, String exp) {
        if (start == n) {
            if (sum == target) {
                res.add(exp);
            }
            return;
        }
        for (int step = 1; start + step <= n; step++) {
            IntWrapper wrapper = getInt(num.substring(start, start + step));
            if (wrapper.isValid) {
                int p = wrapper.val;
                f(start + step, sum + p, p, exp + "+" + p);
                //注意，减法的话，pre = -p
                f(start + step, sum - p, -p, exp + "-" + p);
                //注意，乘法的话，pre = pre * p
                f(start + step, sum - pre + pre * p, pre * p, exp + "*" + p);
            }
        }
    }

    /*
     * 判断整数溢出和前导0的情况
     */
    private IntWrapper getInt(String s) {
        IntWrapper wrapper = new IntWrapper();
        //如果以0开头且长度大于1，则无效
        if (s.charAt(0) == '0' && s.length() > 1) {
            return wrapper;
        }
        //如果转整数异常，则无效
        try {
            wrapper.val = Integer.parseInt(s);
        } catch (RuntimeException e) {
            return wrapper;
        }
        wrapper.isValid = true;
        return wrapper;
    }

    class IntWrapper {
        int val;
        boolean isValid;
    }
}
