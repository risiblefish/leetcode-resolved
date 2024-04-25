package ih.awake;

/**
 * @author Sean Yu
 * @since 2024/4/12 11:36
 */

public class DecomposeHeroCountSimulator {
    public static void main(String[] args) {
        int threeStarCnt = 0;
        int fourStarCnt = 1;
        int fiveStarCnt = 0;
        int sixStarCnt = 0;
        int nineStarCnt = 0;
        int tenStarCnt = 0;
        double cnt = count(threeStarCnt, fourStarCnt, fiveStarCnt, sixStarCnt, nineStarCnt, tenStarCnt);
        System.out.printf("消耗%s个三星，%s个四星，%s个五星, %s个六星, %s个九星, %s个十星 可获得 %.4f个本体%n",
                threeStarCnt, fourStarCnt, fiveStarCnt, sixStarCnt, nineStarCnt, tenStarCnt, cnt);
    }


    public static double count(int threeStarCnt, int fourStarCnt, int fiveStarCnt, int sixStartCnt, int nineStarCnt, int tenStarCnt) {
        return (threeStarCnt * 10 + fourStarCnt * 35 + fiveStarCnt * 250 + sixStartCnt * 1250 + nineStarCnt * 1250 + tenStarCnt * 12000) / 50000.0 * 8;
    }
}
