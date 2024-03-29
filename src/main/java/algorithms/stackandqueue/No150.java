package algorithms.stackandqueue;

import java.util.Stack;

/**
 * 150. 逆波兰表达式求值
 *
 * @Author: Sean Yu
 * @Date: 2021/3/20 17:37
 */
public class No150 {
    public static void main(String[] args) {
        System.out.println(new Solution150().evalRPN(new String[]{"4", "13", "5", "/", "+"}));
    }
}

/**
 * 思路： 首先理解逆波兰表达式， a,b，操作符的时候， 是a操作符b 这样运算的，有先后顺序
 * 解决思路： 利用栈。
 * 细节坑点： 负数的处理，以及超过1位数字的处理。
 * （其实仔细审题后可以发现，由于题目保证有效，所以除了操作符，其他的都可以当成数字来处理，这样直接用Integer.parseInt就可以处理掉包含负数的情况）
 * todo : 由于没有完全理解逆波兰表达式，想多了，认为表达式中可以嵌套表达式，比如，1,2,12+3-,4 这种，其实不是逆波兰表达式，二刷的时候可以更新题解
 */
class Solution150 {
    public int evalRPN(String[] tokens) {
        Stack<Integer> stack = new Stack();
        for (int i = 0; i < tokens.length; i++) {
            String s = tokens[i];
            if (isOperator(s)) {
                int num2 = stack.pop();
                int num1 = stack.pop();
                stack.push(op(num1, num2, s));
            } else {
                stack.push(Integer.parseInt(s));
            }
        }
        return stack.pop();
    }

    private boolean isOperator(String s) {
        return s.equals("+") || s.equals("-") || s.equals("*") || s.equals("/");
    }

    private int op(int num1, int num2, String op) {
        switch (op) {
            case "+":
                return num1 + num2;
            case "-":
                return num1 - num2;
            case "*":
                return num1 * num2;
            default:
                return num1 / num2;
        }
    }
}
