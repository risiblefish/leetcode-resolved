package algorithms.bitoperation;

import java.util.Random;

/**
 * 题目要求：
 * 给定一个int f()，它的作用是等概率返回[1,5]的一个整数
 * 请利用f()，编写一个方法，它等概率返回[1,7]的一个整数
 *
 * @author Sean Yu
 * @date 2021/7/8 22:04
 */
public class RandToRand {
    static Random rand = new Random();

    //等概率返回[1,5]区间内的一个整数
    public static int f(){
        return rand.nextInt(5) + 1;
    }

    public static void printTest(){
        int[] res = new int[8];
        for (int i = 0; i < 1000000; i++) {
            res[f4()] ++;
        }
        for (int i = 0; i < res.length; i++) {
            System.out.println(String.format("%s 出现了 %s 次", i, res[i]));
        }
    }

    //=============================== 解答过程 =====================================
    //step 1. 利用f()，编写一个f1()，能等概率返回一个[0,1]内的整数
    public static int f1(){
        int i;
        for(;;){
            i = f();
            if(i < 3){
                return 0;
            }
            else if(i > 3){
                return 1;
            }
        }
    }

    //step 2. 利用f1()，写出f2()，其能等概率返回一个[0,7]内的整数
    public static int f2(){
        return  (f1() << 2)
                + (f1() << 1)
                + f1();
    }

    //step 3. 利用f2()，写出f3()，其能等概率返回一个[0,6]内的整数
    public static int f3(){
        int i;
        for(;;){
            i = f2();
            if(i != 7){
                return i;
            }
        }
    }

    //step 4. 利用f3()，写出f4()，其能等概率返回一个[1,7]内的整数
    public static int f4(){
        return f3() + 1;
    }

    public static void main(String[] args) {
        printTest();
    }
}
