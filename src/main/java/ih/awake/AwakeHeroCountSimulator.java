package ih.awake;

/**
 * @author Sean Yu
 */
public class AwakeHeroCountSimulator {
    public static void main(String[] args) {
        sim(1920);
        System.out.println(1.0 + (1.0 / (20.0/5.92 - 1.0)));
    }

    private static void sim(int n){
        int init = n;
        int cnt = n;
        double point = 0;
        while(n > 0){
            n--;
            point += 5.92;
            if(point >= 20){
                cnt++;
                n++;
                point -= 20;
            }
        }
        String msg = String.format("觉醒本体数%s, 最终获得本体数%s", init, cnt);
        System.out.println(msg);

    }
}
