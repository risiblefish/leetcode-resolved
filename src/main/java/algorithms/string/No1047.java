package algorithms.string;

import java.util.*;

/**
 * 1047. 删除字符串中的所有相邻重复项
 * @author Sean Yu
 */
public class No1047 {
    public static void main(String[] args) {
        System.out.println(new Solution1047II().removeDuplicates("aababaab"));
    }
}

/**
 * 思路： 利用栈
 * 栈中将保存当前还未重复的字符
 * 在一次遍历中：
 * 如果栈不为空并且当前遍历到的字符与栈首字母相同同，就将栈首字符弹出
 * 否则，则将当前遍历到的字符入栈
 *
 * 在一次遍历完成后，栈中剩余元素就是解
 */
class Solution1047I {
    public String removeDuplicates(String S) {
        if (S == null || S.length() == 0) {
            return S;
        }
        Stack<Character> stack = new Stack();
        char[] arr = S.toCharArray();
        int i = 0;
        while (i < arr.length) {
            if (stack.isEmpty()) {
                stack.push(arr[i]);
            } else {
                if (stack.peek() == arr[i]) {
                    stack.pop();
                } else {
                    stack.push(arr[i]);
                }
            }
            i++;
        }
        StringBuilder sb = new StringBuilder();
        while (!stack.isEmpty()) {
            sb.append(stack.pop());
        }
        return sb.reverse().toString();
    }
}

/**
 * 优化版：
 * 利用StringBuilder/StringBuffer等工具
 * 因为它们有append(),deleteCharAt()等方法，因此可以当做栈来使用
 * 如此一来，再最后将栈内剩余元素转string的时候，就不用再遍历一次
 */
class Solution1047II {
    public String removeDuplicates(String S) {
        if (S == null || S.length() == 0) {
            return S;
        }
        StringBuilder stack = new StringBuilder();
        int top = -1;
        for (int i = 0 ; i < S.length() ; i++){
            char curr = S.charAt(i);
            if(top >= 0 && curr == stack.charAt(top)){
                stack.deleteCharAt(top);
                top--;
            }else{
                stack.append(curr);
                top++;
            }
        }
        return stack.toString();
    }
}
