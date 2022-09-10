package algorithms.stackandqueue;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * 20. 有效的括号
 * @author Sean Yu
 */
public class No20 {
    public static void main(String[] args) {
        String s = "()[]{}";
        System.out.println(new Solution20().isValid(s));
    }
}

class Solution20 {
    public boolean isValid(String s) {
        Map<Character,Character> map = new HashMap();
        map.put(')','(');
        map.put(']','[');
        map.put('}','{');
        Stack<Character> stack = new Stack();
        for(char c : s.toCharArray()){
            if(c == '(' || c == '[' || c == '{'){
                stack.push(c);
            }
            else{
                if(stack.isEmpty() || stack.peek()!= map.get(c)){
                    return false;
                }
                stack.pop();
            }
        }
        return stack.isEmpty();
    }
}