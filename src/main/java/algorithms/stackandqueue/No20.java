package algorithms.stackandqueue;

import java.util.Stack;

/**
 * 20. 有效的括号
 * @author Sean Yu
 */
public class No20 {
}

class Solution20 {
    public boolean isValid(String s) {
        Stack<Character> stack = new Stack();
        int n = s.length();
        for (int i = 0; i < n; i++) {
            char c = s.charAt(i);
            if (c == '(' || c == '[' || c == '{') {
                stack.push(c);
            } else {
                if (stack.isEmpty()) {
                    return false;
                }
                char prev = stack.pop();
                if (c == ')' && prev != '(') {
                    return false;
                } else if (c == ']' && prev != '[') {
                    return false;
                } else if (c == '}' && prev != '{') {
                    return false;
                }
            }
        }
        return stack.isEmpty();
    }
}