package algorithms.math;

/**
 * 779.第K个语法符号
 * @author Sean Yu
 * @create 2022/7/10 13:23
 */
public class No779 {
}

/**
 * 思路： 找规律
 *  第1行： 0
 *  第2行： 0 | 1
 *  第3行： 01 | 10
 *  第4行： 0110 | 1001
 *  第5行： 01101001 | 10010110
 *  ...
 *
 *  发现，
 *  （1）第1个数总是为0
 *  （2）第n行的前半段，刚好是第n-1行，后半段，刚好是前半段取反，即 第n-1行 取反
 *
 *  当找第K个数的时候，
 *  如果k在 当前行 的前半段，则k就在上一行的同一个位置
 *  如果k在 当前行 的后半段，可以先算出K相对于后半段的位置，然后找出上一行这个位置的值，把值反过来
 */
class Solution779 {
    public int kthGrammar(int n, int k) {
        if(n == 1){
            return 0;
        }
        //第n行的长度
        int curLen = (int)Math.pow(2, n-1);
        //如果k在后半段
        if(k > curLen / 2){
            int val = kthGrammar(n-1, k - curLen / 2);
            return val == 1 ? 0 : 1;
        }
        //如果k在前半段
        else{
            return kthGrammar(n-1, k);
        }
    }
}