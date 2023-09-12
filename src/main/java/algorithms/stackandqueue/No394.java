package algorithms.stackandqueue;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * 394. 字符串解码
 * @author Sean Yu
 * @create 2022/7/6 08:26
 */
public class No394 {
    public static void main(String[] args) {
        Map<Integer,int[]> map = new HashMap<>();
        System.out.println(new Solution394().decodeString("3[a]2[bc]"));
    }
}

/**
 * 思路： https://leetcode.cn/problems/decode-string/solution/decode-string-fu-zhu-zhan-fa-di-gui-fa-by-jyd/
 */
class Solution394 {
    public String decodeString(String s) {
        Stack<Integer> ins = new Stack();
        Stack<String> ss = new Stack();
        StringBuilder res = new StringBuilder();
        int cnt = 0;
        for (char c : s.toCharArray()) {
            if (c == '[') {
                ins.push(cnt);
                ss.push(res.toString());
                cnt = 0;
                res = new StringBuilder();
            } else if (c == ']') {
                StringBuilder tmp = new StringBuilder();
                int curCnt = ins.pop();
                while (curCnt > 0) {
                    tmp.append(res);
                    curCnt--;
                }
                if (!ss.isEmpty()) {
                    res = new StringBuilder(ss.pop() + tmp);
                } else {
                    res = new StringBuilder(tmp);
                }
            } else if (Character.isDigit(c)) {
                cnt = cnt * 10 + (int) (c - '0');
            } else {
                res.append(c);
            }
        }
        return res.toString();
    }
}

/**
 * 递归解法
 */
class Solution394_II {
    String og;
    int n;
    int idx;

    public String decodeString(String s) {
        n = s.length();
        og = s;
        return dfs();
    }

    private String dfs() {
        int cnt = 0;
        StringBuilder res = new StringBuilder();
        while (idx < n) {
            char c = og.charAt(idx);
            if (Character.isDigit(c)) {
                cnt = cnt * 10 + c - '0';
            }
            else if (Character.isAlphabetic(c)) {
                res.append(c);
            }
            else if (c == '[') {
                idx++;
                String tmp = dfs();
                while (cnt > 0) {
                    res.append(tmp);
                    cnt--;
                }
            } else {
                return res.toString();
            }
            idx++;
        }
        return res.toString();
    }
}
