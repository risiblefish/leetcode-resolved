package algorithms.stack;

import java.util.Stack;

/**
 * 224. 基本计算器
 * @author Sean Yu
 */
public class No224 {
    public static void main(String[] args) {
        System.out.println(new Solution224().calculate("1+2-3"));
    }
}


/**
 * 思路，利用栈。 细节比较多
 * 当前字符为c
 * <p>
 * c == ' ' 继续
 * c == '(' 入op栈
 * c == ')' 计算结果并入num栈，直到op栈顶是(，弹出该(
 * c == '+'或'-'，先把栈内结果尽可能的计算，每次结果入num栈，直到op栈顶是(或者op栈为空
 * c == 数字串， 找到从c开始的连续的数字(要注意i<s.length())，并入num栈
 */
class Solution224 {
    public int calculate(String s) {
        //题目保证s是个有效的表达式
        Stack<Integer> numStack = new Stack();
        numStack.push(0);
        Stack<Character> opStack = new Stack();
        int i = 0;
        while (i < s.length()) {
            char c = s.charAt(i);
            if (c == ' ') {
                i++;
                continue;
            } else if (c == '(') {
                opStack.push(c);
                i++;
            } else if (c == ')') {
                while (opStack.peek() != '(') {
                    int num2 = numStack.pop();
                    int num1 = numStack.pop();
                    char op = opStack.pop();
                    numStack.push(calculate(num1, num2, op));
                }
                opStack.pop();
                i++;
            } else if (c == '+' || c == '-') {
                while (!opStack.isEmpty()) {
                    if (opStack.peek() == '(') {
                        break;
                    }
                    int num2 = numStack.pop();
                    int num1 = numStack.pop();
                    char op = opStack.pop();
                    numStack.push(calculate(num1, num2, op));
                }
                opStack.push(c);
                i++;
            } else {
                StringBuilder sb = new StringBuilder();
                while (i < s.length() && Character.isDigit(s.charAt(i))) {
                    sb.append(s.charAt(i));
                    i++;
                }
                numStack.push(Integer.parseInt(sb.toString()));
            }
        }
        while (!opStack.isEmpty()) {
            int num2 = numStack.pop();
            int num1 = numStack.pop();
            char op = opStack.pop();
            numStack.push(calculate(num1, num2, op));
        }
        return numStack.pop();
    }

    private int calculate(int num1, int num2, char op) {
        if (op == '-') {
            return num1 - num2;
        }
        return num1 + num2;
    }
}
