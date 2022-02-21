package algorithms.recursion;

import java.util.ArrayList;
import java.util.List;

/**
 * 面试08.06 汉诺塔
 * @author Sean Yu
 * @date 15/2/2022 下午10:05
 */
public class MS0806 {
    public static void main(String[] args) {
        ArrayList<Integer> A = new ArrayList<>();
//        A.add(3);
        A.add(2);
        A.add(1);
        A.add(0);
        ArrayList<Integer> B = new ArrayList<>();
        ArrayList<Integer> C = new ArrayList<>();
        SolutionMS0806 test = new SolutionMS0806();
        test.hanota(A, B, C);
        System.out.println(C);
    }
}

/**
 * 思路：
 * 将n个盘子从A移动到C
 * 当 n = 1时， 直接将盘子从A移动到C
 * 当 n > 1时，
 * (1)将A的前 n-1个盘子 移动到B , (2)将A的最后一个盘子移动到C，(3) 将B的n-1个盘子移动到C
 *
 * 其中，（1）将A的n-1个盘子 移动到B 就是子问题， (3)将B的n-1个盘子移动到C 也是子问题
 * 子问题 可以归纳为： 将k个盘子从 一个柱子 移向 另一个柱子
 */
class SolutionMS0806 {
    public void hanota(List<Integer> A, List<Integer> B, List<Integer> C) {
        move(A.size(), A, C, B);
    }

    private void move(int n, List<Integer> from, List<Integer> to, List<Integer> other) {
        if (n == 1) {
            to.add(from.remove(from.size() - 1)); //注意 这里不能写成 from.remove(0), n == 1的时候，from的size()不一定为1
        } else {
            move(n - 1, from, other, to);
            to.add(from.remove(from.size() - 1)); //注意 这里不能写成 from.remove(0)
            move(n - 1, other, to, from);
        }
    }
}

