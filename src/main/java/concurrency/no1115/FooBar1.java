package concurrency.no1115;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * ������ͬ���߳̽��Ṳ��һ�� FooBar?ʵ��������һ���߳̽������?foo()?��������һ���߳̽������?bar()?������
 * <p>
 * ������޸ĳ�����ȷ�� "foobar" ����� n �Ρ�
 * <p>
 * ��Դ�����ۣ�LeetCode��
 * ���ӣ�https://leetcode-cn.com/problems/print-foobar-alternately
 * ����Ȩ������������С���ҵת������ϵ�ٷ���Ȩ������ҵת����ע��������
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
//            System.out.println("  �����̶߳���դ���ˣ�����դ��");
//        });
        this.cb = new CyclicBarrier(2);
        isFoo = true;
    }

    public void foo(Runnable printFoo) throws InterruptedException {
        for (int i = 0; i < n; i++) {
            //�����ȴ�
            while (!isFoo) {
            }
            printFoo.run();
            isFoo = false;
            try {
//                System.out.println(String.format("\nfoo��%s�ε���դ��",i));
                cb.await();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }

    public void bar(Runnable printBar) throws InterruptedException {
        for (int i = 0; i < n; i++) {
            try {
//                System.out.println(String.format("\nbar��%s�ε���դ��",i));
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
