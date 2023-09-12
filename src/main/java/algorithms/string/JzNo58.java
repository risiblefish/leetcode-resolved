package algorithms.string;


/**
 * 剑指 Offer 58 - II. 左旋转字符串
 * @author Sean Yu
 */
public class JzNo58 {
    public static void main(String[] args) {
        String b = "a";
        b += "c";
        System.out.println(b);

        System.out.println(appendStr(b,"c"));
        System.out.println("b".equals("c"));
    }

    private static String appendStr(String s,String append){
        s += append;
        return s;
    }
}



/**
 * 思路：
 * 如果允许使用库函数的话，可以用字符串切片
 */
class SolutionJzNo58 {
    public String reverseLeftWords(String s, int n) {
        StringBuilder sb = new StringBuilder(s.substring(n) + s.substring(0,n));
        return sb.toString();
    }
}