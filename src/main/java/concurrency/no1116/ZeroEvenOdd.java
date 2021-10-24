package concurrency.no1116;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntConsumer;

/**
 * 使用while自旋
 * @author Sean Yu
 */
public class ZeroEvenOdd {
    //打印到n退出
    private int n;

    /**
     * flag 的值：
     * 0 表示 打印0，下一个打印奇数
     * 1 表示 打印奇数，下一个打印0
     * 2 表示 打印0，下一个打印偶数
     * 3 表示 打印偶数，下一个打印0
     */
    private AtomicInteger flag = new AtomicInteger(0);
    private AtomicInteger curr = new AtomicInteger(0);
    private volatile boolean isEnd = false;

    public ZeroEvenOdd(int n) {
        this.n = n;
    }

    public void zero(IntConsumer printNumber) {
        while (!isEnd) {
            while (flag.get() != 0 && flag.get() != 2) {
                if (isEnd) {
                    return;
                }
            }
            printNumber.accept(0);
            if (flag.get() == 0) {
//                System.out.println("now 0, then odd");
                flag.set(1);
            } else {
//                System.out.println("now 0, then even");
                flag.set(3);
            }
        }
    }

    public void odd(IntConsumer printNumber) {
        while (!isEnd) {
            while (flag.get() != 1) {
                if (isEnd) {
                    return;
                }
            }
            int val = curr.incrementAndGet();
            printNumber.accept(val);
//            System.out.println("now odd, then 0");
            if(val >= n){
                isEnd = true;
            }
            flag.set(2);
        }
    }

    public void even(IntConsumer printNumber) {
        while (!isEnd) {
            while (flag.get() != 3) {
                if (isEnd) {
                    return;
                }
            }
            int val = curr.incrementAndGet();
            printNumber.accept(val);
//            System.out.println("now even, then 0");
            if(val >= n){
                isEnd = true;
            }
            flag.set(0);
        }
    }

    public static void main(String[] args) {
        ZeroEvenOdd test = new ZeroEvenOdd(2);
        IntConsumer ic = System.out::print;

        Thread t0 = new Thread(() -> {
            test.zero(ic);
        });

        Thread t1 = new Thread(() -> {
            test.odd(ic);
        });

        Thread t2 = new Thread(() -> {
            test.even(ic);
        });

        t0.start();
        t1.start();
        t2.start();
    }
}
