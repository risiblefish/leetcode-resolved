package concurrency.no1115;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * 两个不同的线程将会共用一个 FooBar?实例。其中一个线程将会调用?foo()?方法，另一个线程将会调用?bar()?方法。
 * <p>
 * 请设计修改程序，以确保 "foobar" 被输出 n 次。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/print-foobar-alternately
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author Sean Yu
 */
public class FooBar1 {
    private int n;

    private CyclicBarrier cb;
    private volatile boolean isFoo;

    public FooBar1(int n) {
        this.n = n;
//        this.cb = new CyclicBarrier(2,()->{
//            System.out.println("  所以线程都到栅栏了，冲破栅栏");
//        });
        this.cb = new CyclicBarrier(2);
        isFoo = true;
    }

    public void foo(Runnable printFoo) throws InterruptedException {
        for (int i = 0; i < n; i++) {
            //自旋等待
            while (!isFoo) {
            }
            printFoo.run();
            isFoo = false;
            try {
//                System.out.println(String.format("\nfoo第%s次到达栅栏",i));
                cb.await();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }

    public void bar(Runnable printBar) throws InterruptedException {
        for (int i = 0; i < n; i++) {
            try {
//                System.out.println(String.format("\nbar第%s次到达栅栏",i));
                cb.await();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
            printBar.run();
            isFoo = true;
        }
    }

    public static void main(String[] args) {
        TestUtil.test();
    }
}
