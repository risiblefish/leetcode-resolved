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
        System.out.println(String.format("���г���c-����Ϊ[%s]ʱ��ÿ����ܼ�ֵ[%.2f]����", v, p));
        System.out.println("�����Լ۱Ȳ���Ļ�");
        System.out.println(String.format("��Ӧ��c����[%.2f]", 600 / p));
        System.out.println(String.format("��Ӧ��c+����[%.2f]", 800 / p));
        System.out.println(String.format("��Ӧ��b-����[%.2f]", 1000 / p));
        System.out.println(String.format("��Ӧ��b����[%.2f]", 1800 / p));
        System.out.println(String.format("��Ӧ��b+����[%.2f]", 6000 / p));
        System.out.println(String.format("��Ӧ��a-����[%.2f]", 30000 / p));
    }
}
