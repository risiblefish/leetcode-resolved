package algorithms.stack;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * 1190. 反转每对括号间的子串
 * @author Sean Yu
 */
public class No1190 {
    public static void main(String[] args) {
        System.out.println(new Solution1190().reverseParentheses("i(evol)u"));
    }
}

/**
 * 思路：利用栈
 */
class Solution1190 {
    public String reverseParentheses(String s) {
        Deque<Character> stack = new ArrayDeque();
        StringBuilder res = new StringBuilder();
        StringBuilder temp = new StringBuilder();
        int len = s.length();
        int i = 0;
        while(i < len){
            char c = s.charAt(i);
            if(c == ')'){
                if(temp.length() > 0) {
                    temp.delete(0,temp.length());
                }
                while(stack.peek() != '('){
                    temp.append(stack.pop());
                }
                stack.pop();// pop (
                for(int j = 0 ; j < temp.length(); j++){
                    stack.push(temp.charAt(j));
                }
            }else {
                stack.push(c);
            }
            i++;
        }

        while(!stack.isEmpty()){
            res.append(stack.pop());
        }
        return res.reverse().toString();
    }
}
