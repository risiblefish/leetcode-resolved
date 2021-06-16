package algorithms.string;

/**
 * 剑指offer 05. 替换空格
 *
 * @author Sean Yu
 */
public class JzNo05 {
}

class SolutionJzNo05 {
    public String replaceSpace(String s) {
        StringBuilder sb = new StringBuilder();
        char[] arr = s.toCharArray();
        for (char ch : arr) {
            if (ch == ' ') {
                sb.append("%20");
            } else {
                sb.append(ch);
            }
        }
        return sb.toString();
    }
}
