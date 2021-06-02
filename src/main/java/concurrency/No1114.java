package concurrency;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 1114. 按序打印
 * @author Sean Yu
 */
public class No1114 {
}

/**
 * 思路 ：使用volatile 或者 atomic 来变相加锁，从而控制顺序
 */
class Foo1 {
    volatile int i = 0;

    public Foo1() {

    }

    public void first(Runnable printFirst) throws InterruptedException {
        // printFirst.run() outputs "first". Do not change or remove this line.
        printFirst.run();
        i++;
    }

    public void second(Runnable printSecond) throws InterruptedException {
        while (i != 1) {
        }
        // printSecond.run() outputs "second". Do not change or remove this line.
        printSecond.run();
        i++;
    }

    public void third(Runnable printThird) throws InterruptedException {
        while (i != 2) {
        }
        // printThird.run() outputs "third". Do not change or remove this line.
        printThird.run();
    }
}

class Foo2 {
    AtomicInteger jobDone = new AtomicInteger(0);
    public Foo2() {

    }
    public void first(Runnable printFirst) throws InterruptedException {
        // printFirst.run() outputs "first". Do not change or remove this line.
        printFirst.run();
        jobDone.getAndIncrement();
    }

    public void second(Runnable printSecond) throws InterruptedException {
        while (jobDone.get() != 1) {
        }
        // printSecond.run() outputs "second". Do not change or remove this line.
        printSecond.run();
        jobDone.getAndIncrement();
    }

    public void third(Runnable printThird) throws InterruptedException {
        while (jobDone.get() != 2) {
        }
        // printThird.run() outputs "third". Do not change or remove this line.
        printThird.run();
    }
}