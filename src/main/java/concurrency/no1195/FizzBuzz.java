package concurrency.no1195;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.IntConsumer;

/**
 * @author Sean Yu
 */
public class FizzBuzz {
    private int n;
    ReentrantLock lock = new ReentrantLock();
    Condition fizzCon = lock.newCondition();
    Condition buzzCon = lock.newCondition();
    Condition fbCon = lock.newCondition();
    Condition simpleCon = lock.newCondition();

    volatile int order = 1;
    volatile int curr = 1;

    public FizzBuzz(int n) {
        this.n = n;
    }

    // printFizz.run() outputs "fizz".
    public void fizz(Runnable printFizz) throws InterruptedException {
        while (curr <= n) {
            try {
                lock.lock();
                System.out.println("now fizz locked");
                while (order != 1) {
                    fizzCon.await();
                }
                if (curr % 3 == 0) {
                    printFizz.run();
                }
                //唤醒buzz线程
                order = 2;
                buzzCon.signal();
            } finally {
                lock.unlock();
                System.out.println("now fizz unlock");
            }
        }
    }

    // printBuzz.run() outputs "buzz".
    public void buzz(Runnable printBuzz) throws InterruptedException {
        while (curr <= n) {
            try {
                lock.lock();
                System.out.println("now buzz locked");
                while (order != 2) {
                    buzzCon.await();
                }
                if (curr % 5 == 0) {
                    printBuzz.run();
                }
                order = 3;
                //唤醒fizzbuzz线程
                fbCon.signal();
            } finally {
                lock.unlock();
                System.out.println("now buzz unlock");
            }
        }
    }

    // printFizzBuzz.run() outputs "fizzbuzz".
    public void fizzbuzz(Runnable printFizzBuzz) throws InterruptedException {
        while (curr <= n) {
            try {
                lock.lock();
                System.out.println("now fizzbuzz locked");

                while (order != 3) {
                    fbCon.await();
                }
                if (curr % 3 == 0 && curr % 5 == 0) {
                    printFizzBuzz.run();
                }
                order = 4;
                //唤醒打印普通数字的线程
                simpleCon.signal();
            } finally {
                lock.unlock();
                System.out.println("now fizzbuzz unlock");
            }
        }
    }

    // printNumber.accept(x) outputs "x", where x is an integer.
    public void number(IntConsumer printNumber) throws InterruptedException {
        while (curr <= n) {
            try {
                lock.lock();
                System.out.println("now simple locked");
                while (order != 4) {
                    simpleCon.await();
                }
                printNumber.accept(curr);
                curr++;
                //将线程运行顺序复位到fizz线程优先执行
                order = 0;
                fizzCon.signal();
            } finally {
                lock.unlock();
                System.out.println("now simple unlock");
            }
        }
    }


    public static void main(String[] args) throws InterruptedException {
        FizzBuzz test = new FizzBuzz(5);

        Runnable printFizz = () -> System.out.println("fizz");
        Runnable printBuzz = () -> System.out.println("fizz");
        Runnable printFizzBuzz = () -> System.out.println("fizzBuzz");
        IntConsumer ic = System.out::println;

        Thread fizzT = new Thread(() -> {
            try {
                test.fizz(printFizz);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread buzzT = new Thread(() -> {
            try {
                test.buzz(printBuzz);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread fbT = new Thread(() -> {
            try {
                test.fizzbuzz(printFizzBuzz);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread simpleT = new Thread(() -> {
            try {
                test.number(ic);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        fizzT.start();
        buzzT.start();
        fbT.start();
        simpleT.start();
    }
}
