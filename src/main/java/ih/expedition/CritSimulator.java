package ih.expedition;

/**
 * @author Sean Yu
 */
public class CritSimulator {
    public static void main(String[] args) {
        double p = 0.575;
        while(p < 1){
            String msg = String.format("暴击率为%.3f",  p);
            System.out.println(msg);
            simulate(p);
            p += 0.1f;
        }
    }

    private static void simulate(double p) {
        double sum = 0;
        for (int i = 0; i <= 5; i++) {
            double po = combine(5, i) * Math.pow(p, i) * Math.pow(1.0 - p, 5 - i);
//            if(i == 4) {
                String msg = String.format("暴击%s次，非暴击%s次的概率为%.4f", i, 5 - i, po);
                System.out.println(msg);
//            }
            sum += po;
        }
//        System.out.println(sum);
        System.out.println("================================");
    }

    private static long combine(int n, int k){
        return fact(n) / (fact(k) * (fact(n - k)));
    }

    private static  long fact(int n){
        long sum = 1;
        for(int i = 2; i <= n; i++){
            sum *= i;
        }
        return sum;
    }
}
