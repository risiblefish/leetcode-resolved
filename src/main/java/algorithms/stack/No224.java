package algorithms.stack;

import java.util.Stack;

/**
 * 224. 基本计算器
 *
 * @author Sean Yu
 */
public class No224 {
    public static void main(String[] args) {
        System.out.println(new Solution224II().calculate("(7)-(0)+(4)"));
    }
}


/**
 * 思路1，利用2个栈，1个存数，1个存符号。 细节比较多
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

/**
 * 思路II：
 * 利用1个栈。
 * 因为只有加减法，每个数要么被加，要么被减。所以可以利用将括号展开（即去掉括号）的思路。
 * 有：
 * 如果只有1个括号的情况下，
 * 如果括号前面是+号，则括号里的数仍然是做加法
 * 如果括号前面是-号，则括号里的数则要做与符号相反的操作，比如0-(1+2)，括号里的1+2在去掉括号后会变成-1-2
 * <p>
 * 对于运算表达式里的每一个数，如果我们能确定这个数最靠近它的左括号前的符号是正还是负，那么结果就很好计算了。
 * <p>
 * 求每个数前面的符号的思路：
 * 把每个数前面的符号用sign表示，初始化为1,用表达式来表示，就是+(s)， +()不会影响计算结果
 * 如果没有括号，则直接用当前的sign进行运算
 * 如果碰到左括号，则把sign入栈，这样做的意义是，在碰到下一个左括号时，我们保存了上一个左括号时的符号，可以根据这个来进行计算
 * 如果碰到右括号，则说明一对括号内的所有数已经运算结束，弹出一个栈顶的运算符
 */
class Solution224II {
    public int calculate(String s) {
        int sign = 1;
        Stack<Integer> opStack = new Stack<>();
        opStack.push(1);
        int res = 0;
        int i = 0;
        while (i < s.length()) {
            char c = s.charAt(i);
            if (c == ' ') {
                i++;
                continue;
            } else if (c == '+') {
                sign = opStack.peek();
                i++;

            } else if (c == '-') {
                sign = -opStack.peek();
                i++;
            } else if (c == '(') {
                opStack.push(sign);
                i++;
            } else if (c == ')') {
                opStack.pop();
                i++;
                continue;
            } else {
                int num = 0;
                while (i < s.length()) {
                    c = s.charAt(i);
                    if (Character.isDigit(c)) {
                        num = num * 10 + c - '0';
                        i++;
                    } else {
                        break;
                    }
                }
                res += num * sign;
            }
        }
        return res;
    }

}
