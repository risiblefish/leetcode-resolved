package algorithms.stack;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * 227. 基本计算器II
 * @author Sean Yu
 */
public class No227 {
    public static void main(String[] args) {
        System.out.println(new Solution227II().calculate("3+2*2"));
    }
}

/**
 * 思路： 因为没有括号，可以提前计算 乘法和除法 的结果，然后放入栈中，最后进行加减计算
 */
class Solution227 {
    public int calculate(String s) {
        Deque<Character> opStack = new ArrayDeque<>();
        Deque<Integer> numStack = new ArrayDeque<>();
        int i = 0;
        while (i < s.length()) {
            char c = s.charAt(i);
            if (c == ' ') {
                i++;
                continue;
            } else if (c == '+' || c == '-') {
                opStack.push(c);
                i++;
            } else if (c == '*') {
                i = pushNum(s, i + 1, numStack);
                int num2 = numStack.pop();
                int num1 = numStack.pop();
                numStack.push(num1 * num2);
            } else if (c == '/') {
                i = pushNum(s, i + 1, numStack);
                int num2 = numStack.pop();
                int num1 = numStack.pop();
                numStack.push(num1 / num2);
            } else {
                i = pushNum(s, i, numStack);
            }
        }
        while (!opStack.isEmpty()) {
            int num1 = numStack.pollLast();
            int num2 = numStack.pollLast();
            if (opStack.pollLast() == '+') {
                numStack.addLast(num1 + num2);
            } else {
                numStack.addLast(num1 - num2);
            }
        }
        return numStack.pop();
    }

    private int pushNum(String s, int index, Deque<Integer> numStack) {
        int num = 0;
        while (index < s.length()) {
            char c = s.charAt(index);
            if (c == ' ') {
                index++;
                continue;
            } else if (Character.isDigit(c)) {
                num = num * 10 + c - '0';
                index++;
            } else {
                break;
            }
        }
        numStack.push(num);
        return index;
    }
}

/**
 * 思路：
 * 使用preSign来记录当前左边最靠近的符号
 * 用一个在循环之外的num来记录当前遍历的数的值
 * 用一个栈来维护前面出现的数
 * 如果该符号是加号，则将num入栈
 * 如果该符号是减号，则将-num入栈
 * 如果该符号是乘号，则将栈顶的数弹出，与当前的num想乘，然后再将结果入栈
 * 如果该符号是除号，则将栈顶的数弹出，作为被除数，除以当前的num，再将结果入栈
 *
 * 细节：
 * （1）i == n-1时，要进行最后结算，所以//处不能用else if，为了达到相同的目的，需要加上!Character.isDigit(c)
 */
class Solution227II {
    public int calculate(String s) {
        Deque<Integer> numStack = new ArrayDeque();
        char preSign = '+';
        int n = s.length();
        int num = 0;
        for (int i = 0; i < n; i++) {
            char c = s.charAt(i);
            if (Character.isDigit(c)) {
                num = num * 10 + c - '0';
            }
            //如果按下面的逻辑写，这里不能用else if，因为要继续判断i == n-1
            if (!Character.isDigit(c) && c != ' ' || i == n - 1) {
                if (preSign == '+') {
                    numStack.push(num);
                } else if (preSign == '-') {
                    numStack.push(-num);
                } else if (preSign == '*') {
                    int num1 = numStack.pop();
                    numStack.push(num1 * num);
                } else if (preSign == '/') {
                    int num1 = numStack.pop();
                    numStack.push(num1 / num);
                }
                num = 0;
                preSign = c;
            }
        }
        int res = 0;
        while (!numStack.isEmpty()) {
            res += numStack.pop();
        }
        return res;
    }
}
