package ih.expedition;

/**
 * @author Sean Yu
 */
public class FreyaSim {
    public static void main(String[] args) {
        sim(1,0,0);
    }

    private static void sim(int round, int aAtt, int nAtt) {
        if(round > 15) return;
        if(round >= 3){
            aAtt+=14;
            nAtt+=14;
            aAtt+=nAtt;
        }
        System.out.println(String.format("��%s�غϣ�������Ϊ%s%%", round, aAtt));
        sim(round+1, aAtt, nAtt);
    }
}
