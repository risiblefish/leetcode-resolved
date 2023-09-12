package ih.expedition;

import java.util.Random;

/**
 * @author Sean Yu
 */
public class GuanxinMark {
    final static Random RAND = new Random();
    public static void main(String[] args) {
        for (int debuffCnt = 5; debuffCnt <= 10 ;debuffCnt ++){
            int times = 100000;
            long sum = 0;
            for (int i = 0; i < times; i++) {
                sum += sim(debuffCnt);
            }
            System.out.println(String.format("��ÿ�غ���0����Ϊ1/%s������£���14�غϺ����ʱ������ƽ��ֵΪ%.2f", debuffCnt,  sum*1.0/times));
        }
    }

    /**
     * ģ���14�غϽ���ʱ��ֵ
     * @return
     */
    private static int sim(int debuffCnt){
        int sum = 0;
        for (int i = 1; i <= 14; i++) {
            //roll a num between [0,9]
            sum = RAND.nextInt(debuffCnt) < 1 ? 0 : sum + 45;
            sum = Math.min(sum, 300);
        }
        return sum;
    }
}
