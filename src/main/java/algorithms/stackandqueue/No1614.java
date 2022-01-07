package algorithms.stackandqueue;

/**
 * 1614. 括号的最大嵌套深度
 * @author Sean Yu
 * @date 7/1/2022 下午11:19
 */
public class No1614 {
}

/**
 * 由于题目保证给定的s都是有效的串，所以用depth来模拟栈的进出操作即可，用max记录栈达到过的最大深度
 */
class Solution1614 {
    public int maxDepth(String s) {
        char[] arr = s.toCharArray();
        int n = arr.length;
        int max = 0;
        int depth = 0;
        for(int i = 0; i < n ; i++){
            if(arr[i] == '('){
                ++depth;
                max = Math.max(max, depth);
            }
            if(arr[i] == ')'){
                depth--;
            }
        }
        return max;
    }
}