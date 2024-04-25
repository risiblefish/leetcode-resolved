package ih.soul;

/**
 * @author Sean Yu
 */
public class SoulDecomposeCalculator {
    public static void main(String[] args) {
        int val = 63;
        String type = "c-";
        cal(type, val);
    }

    private static void cal(String type, int v) {
        System.out.println(String.format("当前基于价格为[%s]的[%s]，得到对应的", v, type));
        double p = switch (type){
            case "c-" -> 450.0 / v;
            case "c" -> 600.0 / v;
            case "c+" -> 800.0 / v;
            case "b-" -> 1000.0 / v;
            case "b" -> 1800.0 / v;
            case "b+" -> 6000.0 / v;
            default -> 30000.0 / v;
        };
        System.out.println(String.format("c-[%.2f]", 450 / p));
        System.out.println(String.format("c[%.2f]", 600 / p));
        System.out.println(String.format("c+[%.2f]", 800 / p));
        System.out.println(String.format("b-[%.2f]", 1000 / p));
        System.out.println(String.format("b[%.2f]", 1800 / p));
        System.out.println(String.format("b+[%.2f]", 6000 / p));
        System.out.println(String.format("a-[%.2f]", 30000 / p));
    }
}
