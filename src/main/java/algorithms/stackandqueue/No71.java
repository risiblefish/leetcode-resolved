package algorithms.stackandqueue;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * 71. 简化路径
 * @author Sean Yu
 */
public class No71 {
    public static void main(String[] args) {
        System.out.println(new Solution71().simplifyPath("/a/./b/../../c/"));
    }
}

/**
 * 思路： 用栈模拟即可
 * 题目保证了path有效，且以/开头
 *  1.首先去掉最后的/，如果存在的话
 *
 *  2. 对于每次找到位于i位置的/， 我们寻找从i开始，下一个/的位置j，
 *  那么从i到j-1的字符串str，可能有几种情况:
 *  (1) str 就是/ 说明i的下一个字符也是/，所以跳过本次i的处理
 *  (2) str 是/. 说明是当前目录本身，也跳过本次处理
 *  (3) str 是/.. 说明返回上级目录， 这个时候如果栈非空，就把栈顶的目录弹出
 *  (4) 其他情况，str是一个目录， 我们将它压栈
 *
 *  3. 将i更新到j位置
 *
 */
class Solution71 {
    public String simplifyPath(String path) {
        Deque<String> dq = new ArrayDeque();
        char[] arr = path.toCharArray();
        int n = arr.length;
        //去掉最后的/
        if(arr[n-1] == '/'){
            n--;
        }
        int i = 0;
        while(i < n){
            //如果当前字符是/
            if(arr[i] == '/'){
                //找到下一个/的位置
                int j = i + 1;
                while(j < n && arr[j] != '/'){
                    j++;
                }
                String str = path.substring(i,j);
                //将i移动到下一个/的位置
                i = j;

                //下一个字符也是/ 以及 后2个字符是 /. 都跳过
                if("/".equals(str) || "/.".equals(str)){
                    continue;
                }
                else if("/..".equals(str)){
                    if(!dq.isEmpty()){
                        dq.removeLast();
                    }
                }
                //将当前目录压栈
                else {
                    dq.addLast(str);
                }
            }
        }
        StringBuilder sb = new StringBuilder();
        //如果最后栈为空，就返回根目录
        if(dq.isEmpty()){
            return "/";
        }
        while(!dq.isEmpty()){
            sb.append(dq.removeFirst());
        }
        return sb.toString();
    }
}
