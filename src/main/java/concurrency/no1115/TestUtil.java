package concurrency.no1115;

/**
 * @author Sean Yu
 */
public class TestUtil {
    public static void test() {
        FooBar1 test = new FooBar1(5);

        Runnable printFoo = () -> System.out.print("foo");
        Runnable printBar = () -> System.out.print("bar");
        new Thread(() -> {
            try {
                test.foo(printFoo);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                test.bar(printBar);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
