package algorithms.string;

/**
 * @author Sean Yu
 */
public class No151 {
    public static void main(String[] args) {
        new Solution151().reverseWords("  hello world  ");
    }
}

/**
 * 思路： 双指针
 *
 * 从右往左（先预处理右边的空格），每找到一个单词，就往sb里加入反转后的内容+空格， 最后删除最后添加的那个空格
 *
 * 时间：O(n)
 * 空间：O(n)
 */
class Solution151 {
    public String reverseWords(String s) {
        int fast = s.length() - 1;
        int slow = fast;
        char[] arr = s.toCharArray();
        StringBuilder sb = new StringBuilder();
        while (fast >= 0) {
            //从【当前位置】开始右往左遍历，找到第一个不是空格的字符
            while (slow >= 0 && arr[slow] == ' ') {
                slow--;
            }
            //如果右边字符已经超过了最左边，则无需遍历快指针
            if(slow < 0){
                break;
            }
            //令fast从当前第一个不是空格的字符开始向左遍历
            fast = slow;
            while (fast >= 0 && arr[fast] != ' ') {
                fast--;
            }
            sb.append(s.substring(fast + 1, slow + 1));
            sb.append(" ");
            //更新slow位置
            slow = fast;
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }
}

