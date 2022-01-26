package algorithms.test;

/**
 * @author Sean Yu
 * @date 25/1/2022 上午10:05
 */
public class Test {
    /**
     * y1 = k * x1 + b
     * y2 = k * x2 + b
     *
     * y2 - y1 = k(x2 - x1)
     * k = y2 - y1 / (x2 - x1)
     * b = y1 - k * x1
     * @param args
     */
    public static void main(String[] args) {
        System.out.println((int)1e8);
        double x2 = 1727184433131L / 1e8;
        double y2 = 9555614;

        double x1 = 1704786895842L / 1e8;
        double y1 = 9432427;

        double k = (y2-y1) / (x2 - x1);
        double b = y1 - k * x1;
        System.out.println(String.format("k : %s, b : %s", k, b));
    }
}


