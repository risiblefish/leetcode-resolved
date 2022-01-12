package algorithms.bitoperation;

/**
 * @author Sean Yu
 * @date 2021/7/7 4:57
 */
public class PrintBinary {
    public static void main(String[] args) {
        System.out.println(-0);
        print(Integer.MIN_VALUE);
    }

    static void print(int num) {
        StringBuilder sb = new StringBuilder();
        for (int i = 31; i >= 0; i--) {
            int b = (num & (1 << i)) == 0 ? 0 : 1;
            sb.append(b);
        }
        System.out.println(sb);
    }



}
