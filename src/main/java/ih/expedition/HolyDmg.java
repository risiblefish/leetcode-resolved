package ih.expedition;

import java.util.Random;

/**
 * @author Sean Yu
 */
public class HolyDmg {
    static int[] Aholy = new int[5];
    static int[] Nholy = new int[5];

    static Random RAND = new Random();


    public static void main(String[] args) {
        int times = 100000;
        long total = 0;
        for (int i = 0; i < times; i++) {
            simOneTime();
            int sum = 0;
            for (int j = 1; j < Aholy.length; j++) {
                sum += Aholy[j];
            }
//            System.out.println("本次模拟，14回合结束后共获得神伤层数： " + sum);
            total += sum;
        }
        System.out.println(total*1.0 / times);
    }

    private static void simOneTime(){
        for (int i = 1; i <= 15; i++) {
            simWithRandom();
        }
    }



    private static void simWithRandom() {
        for (int i = 1; i < Aholy.length; i++) {
            Aholy[i] += Nholy[i];
        }
        Aholy[4] += RAND.nextInt(2) == 0 ? 1 : 0;
        Nholy[4] += RAND.nextInt(2) == 0 ? 1 : 0;
        //round end holy cnt - 1
        for (int i = 1; i < Aholy.length; i++) {
            Aholy[i - 1] = Aholy[i];
            Nholy[i - 1] = Nholy[i];
        }
        Aholy[4] = 0;
        Nholy[4] = 0;
    }


    private static void printHoly() {
        for (int i = 1; i < Aholy.length; i++) {
            System.out.print(String.format("剩%s回合有%s层  ", i, Aholy[i]));
            System.out.print(" ");
        }
    }

}

