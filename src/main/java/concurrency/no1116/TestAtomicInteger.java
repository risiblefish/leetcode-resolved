package concurrency.no1116;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Sean Yu
 */
public class TestAtomicInteger {
    AtomicInteger ai = new AtomicInteger(0);
    private void update(){
        ai.incrementAndGet();
    }
    public static void main(String[] args) throws InterruptedException {
        TestAtomicInteger test = new TestAtomicInteger();
        Thread t1 = new Thread(() -> {
            System.out.println(test.ai.get());
            test.update();
        });

        Thread t2 = new Thread(() -> {
            System.out.println(test.ai.get());
            test.update();
        });

        Thread t3 = new Thread(() -> {
            System.out.println(test.ai.get());
            test.update();
        });

        t1.start();
        TimeUnit.SECONDS.sleep(1);
        t2.start();
        TimeUnit.SECONDS.sleep(1);
        t3.start();
    }
}
