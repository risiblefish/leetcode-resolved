package concurrency.no1195;

import java.util.function.IntConsumer;

/**
 * @author Sean Yu
 */
public class FizzBuzz2 {
    private int n;

    volatile int order = 1;
    volatile int curr = 1;
    volatile boolean isFizzBuzz = false;

    public FizzBuzz2(int n) {
        this.n = n;
    }

    // printFizz.run() outputs "fizz".
    public void fizz(Runnable printFizz) throws InterruptedException {
        while (curr <= n) {
            while (order != 1) {
                Thread.yield();
            }
//            System.out.println("now fizz");
            if (curr <= n && curr % 3 == 0 && curr % 5 != 0) {
                printFizz.run();
                isFizzBuzz = true;
            }
            order = 2;
        }
    }

    // printBuzz.run() outputs "buzz".
    public void buzz(Runnable printBuzz) throws InterruptedException {
        while (curr <= n) {
            while (order != 2) {
                Thread.yield();
            }
//            System.out.println("now buzz");
            if (curr <= n && curr % 5 == 0 && curr % 3 != 0) {
                printBuzz.run();
                isFizzBuzz = true;

            }
            order = 3;
        }
    }

    // printFizzBuzz.run() outputs "fizzbuzz".
    public void fizzbuzz(Runnable printFizzBuzz) throws InterruptedException {
        while (curr <= n) {
            while (order != 3) {
                Thread.yield();
            }
//            System.out.println("now fizzbuzz");
            if (curr <= n && curr % 3 == 0 && curr % 5 == 0) {
                printFizzBuzz.run();
                isFizzBuzz = true;
            }
            order = 4;
        }
    }

    // printNumber.accept(x) outputs "x", where x is an integer.
    public void number(IntConsumer printNumber) throws InterruptedException {
        while (curr <= n) {
            while (order != 4) {
                Thread.yield();
            }
//            System.out.println("now simple number");
            if(curr <= n  && !isFizzBuzz) {
                printNumber.accept(curr);
            }
            curr++;
            order = 1;
            isFizzBuzz = false;
        }
    }


    public static void main(String[] args) throws InterruptedException {
        FizzBuzz2 test = new FizzBuzz2(15);

        Runnable printFizz = () -> System.out.println("fizz");
        Runnable printBuzz = () -> System.out.println("buzz");
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
