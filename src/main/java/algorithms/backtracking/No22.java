package algorithms.backtracking;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * 22. 括号生成
 * 思路：
 * 暴力法，先通过dfs递归生成所有可能的组合，然后再判断每一个组合是否合法
 *
 * @author Sean Yu
 */
class Solution22 {
    List<String> res;
    int n;
    public List<String> generateParenthesis(int n) {
        res = new ArrayList();
        this.n = n;
        dfs("",0,0);
        return res;
    }

    private void dfs(String temp, int lcnt, int rcnt){
        //出口条件1
        if (lcnt > n || rcnt > n) return;
        //出口条件2
        if(lcnt == n && rcnt == n){
            if(isValid(temp)){
                res.add(temp);
            }
            return;
        }
        dfs(temp+"(", lcnt+1, rcnt);
        dfs(temp+")", lcnt, rcnt+1);
    }

    private boolean isValid(String s){
        Deque<Character> stack = new ArrayDeque();
        for(char ch : s.toCharArray()){
            if(ch == '('){
                stack.push('(');
            }else {
                if(stack.isEmpty()) {
                    return false;
                }
                stack.pop();
            }
        }
        return stack.isEmpty();
    }
}
