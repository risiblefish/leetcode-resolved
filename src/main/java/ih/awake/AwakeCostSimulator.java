package ih.awake;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sean Yu
 */
public class AwakeCostSimulator {
    List<Quality> qualityList;

    public static void main(String[] args) {
        AwakeCostSimulator test = new AwakeCostSimulator();
        test.init();
        double cost = test.awakeOnceCostException();
        double point = test.awakeOncePointException();
        System.out.printf("觉醒1次的成本 %s 星钻， 能获得的积分 %s", cost, point);
    }

//    private void init(){
//       qualityList = new ArrayList<>();
//       qualityList.add(new Quality("E-", 4.3/100, 3, 10));
//       qualityList.add(new Quality("E", 19.8/100, 4, 15));
//       qualityList.add(new Quality("E+", 28.8/100, 5, 20));
//       qualityList.add(new Quality("D-", 20.0/100, 6, 30));
//       qualityList.add(new Quality("D", 9.2/100, 7, 50));
//       qualityList.add(new Quality("D+", 4.8/100, 8, 70));
//       qualityList.add(new Quality("C-", 4.4/100, 9, 100));
//       qualityList.add(new Quality("C", 4.3/100, 10, 150));
//       qualityList.add(new Quality("C+", 2.13/100, 11, 200));
//       qualityList.add(new Quality("B-", 1.62/100, 12, 300));
//       qualityList.add(new Quality("B", 0.55/100, 13, 600));
////       qualityList.add(new Quality("B+", 0.745/100, 14, 1800));
////       qualityList.add(new Quality("A-", 0.015/100, 15, 8000));
////       qualityList.add(new Quality("A", 0.0065/100, 16, 15000));
////       qualityList.add(new Quality("A+", 0.0025/100, 17, 25000));
////       qualityList.add(new Quality("S", 0.0015/100, 18, 50000));
//
//       double sum = 0f;
//        for (Quality quality : qualityList) {
//            sum += quality.probability;
//        }
//        System.out.printf("总概率为 %s \n", sum);
//    }

    private void init(){
        qualityList = new ArrayList<>();
        qualityList.add(new Quality("E-", 4.3/100, 3, 20));
        qualityList.add(new Quality("E", 19.8/100, 4, 30));
        qualityList.add(new Quality("E+", 28.8/100, 5, 40));
        qualityList.add(new Quality("D-", 20.0/100, 6, 60));
        qualityList.add(new Quality("D", 9.2/100, 7, 100));
        qualityList.add(new Quality("D+", 4.8/100, 8, 140));
        qualityList.add(new Quality("C-", 4.4/100, 9, 100));
        qualityList.add(new Quality("C", 4.3/100, 10, 150));
        qualityList.add(new Quality("C+", 2.13/100, 11, 200));
        qualityList.add(new Quality("B-", 1.62/100, 12, 300));
        qualityList.add(new Quality("B", 0.55/100, 13, 600));
//       qualityList.add(new Quality("B+", 0.745/100, 14, 1800));
//       qualityList.add(new Quality("A-", 0.015/100, 15, 8000));
//       qualityList.add(new Quality("A", 0.0065/100, 16, 15000));
//       qualityList.add(new Quality("A+", 0.0025/100, 17, 25000));
//       qualityList.add(new Quality("S", 0.0015/100, 18, 50000));

        double sum = 0f;
        for (Quality quality : qualityList) {
            sum += quality.probability;
        }
        System.out.printf("总概率为 %s \n", sum);
    }

    /**
     * 觉醒1次的成本
     * 对于每个品质i，获得的返星为： i的概率 * i的反星
     * 固定成本100
     */
    private double awakeOnceCostException(){
        double sum = 0;
        for (Quality quality : qualityList) {
            sum += quality.probability * quality.reward;
        }
        return 100 - sum;
    }

    /**
     * 觉醒1次能获得的积分
     * 对于每个品质i
     * i的概率 * i对应的分数求和
     */
    private double awakeOncePointException(){
        double sum = 0;
        for (Quality quality : qualityList) {
            sum += quality.probability * quality.point;
        }
        return sum;
    }
}


class Quality{
    String name;
    double probability;
    int point;
    int reward;

    public Quality(String name, double probability, int point, int reward) {
        this.name = name;
        this.probability = probability;
        this.point = point;
        this.reward = reward;
    }
}
