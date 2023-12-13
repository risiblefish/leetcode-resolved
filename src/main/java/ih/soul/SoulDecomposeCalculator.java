package ih.soul;

/**
 * @author Sean Yu
 */
public class SoulDecomposeCalculator {
    public static void main(String[] args) {
        int val = 97;
        cal(val);
    }

    private static void cal(int v) {
        double p = 450.0 / v;
        System.out.println(String.format("当市场价c-单价为[%s]时，每点魂能价值[%.2f]活钻", v, p));
        System.out.println("保持性价比不变的话");
        System.out.println(String.format("对应的c单价[%.2f]", 600 / p));
        System.out.println(String.format("对应的c+单价[%.2f]", 800 / p));
        System.out.println(String.format("对应的b-单价[%.2f]", 1000 / p));
        System.out.println(String.format("对应的b单价[%.2f]", 1800 / p));
        System.out.println(String.format("对应的b+单价[%.2f]", 6000 / p));
        System.out.println(String.format("对应的a-单价[%.2f]", 30000 / p));
    }
}
