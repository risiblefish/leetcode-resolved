package concurrency.no1116;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.IntConsumer;

/**
 * 使用condition
 * @author Sean Yu
 */
public class ZeroEvenOdd2 {
    private int n;
    ReentrantLock lock = new ReentrantLock();
    Condition cZero = lock.newCondition();
    Condition cOdd = lock.newCondition();
    Condition cEven = lock.newCondition();
    volatile int order = 0;

    public ZeroEvenOdd2(int n) {
        this.n = n;
    }

    // printNumber.accept(x) outputs "x", where x is an integer.
    public void zero(IntConsumer printNumber) throws InterruptedException {
        try {
            lock.lock();
            //这里的for循环，是为了辅助，让当前线程知道下一个是该唤醒奇数线程还是偶数线程
            for (int i = 1; i <= n; i++) {
                if (order != 0) {
                    cZero.await();
                }
                printNumber.accept(0);
                if (i % 2 == 0) {
                    order = 2;
                    cEven.signal();
                } else {
                    order = 1;
                    cOdd.signal();
                }
            }
        } finally {
            lock.unlock();
        }
    }


    public void even(IntConsumer printNumber) throws InterruptedException {
        try {
            lock.lock();
            for (int i = 2; i <= n; i += 2) {
                if (order != 2) {
                    cEven.await();
                }
                printNumber.accept(i);
                order = 0;
                cZero.signal();
            }
        } finally {
            lock.unlock();
        }
    }

    public void odd(IntConsumer printNumber) throws InterruptedException {
        try {
            lock.lock();
            for (int i = 1; i <= n; i += 2) {
                if (order != 1) {
                    cOdd.await();
                }
                printNumber.accept(i);
                order = 0;
                cZero.signal();
            }
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        ZeroEvenOdd2 test = new ZeroEvenOdd2(2);
        IntConsumer ic = System.out::println;

        Thread t0 = new Thread(() -> {
            try {
                test.zero(ic);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread t1 = new Thread(() -> {
            try {
                test.odd(ic);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread t2 = new Thread(() -> {
            try {
                test.even(ic);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        t0.start();
        t1.start();
        t2.start();
    }
}
