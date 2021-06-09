package algorithms.hashtable;

/**
 * 896. 单调数列
 * @Author: Sean Yu
 * @Date: 2021/2/28 17:01
 */
public class No896 {
}


/**
 * 思路1： 遍历2次数组，分别判断是否单调递增或递减
 * 思路2： 遍历1次数组，如果即出现a[i]>a[j]又出现a[k] < a[h] 则为非单调
 */
class Solution896 {
    public boolean isMonotonic(int[] A) {
        //题目保证A.length >= 1
        if(isIncreasing(A)) {
            return true;
        }
        return isDecreasing(A);
    }

    private boolean isIncreasing(int[] a){
        for(int i = 0 ; i < a.length - 1; i++){
            if(a[i] > a[i+1]){
                return false;
            }
        }
        return true;
    }

    private boolean isDecreasing(int[] a){
        for(int i = 0 ; i < a.length - 1; i++){
            if(a[i] < a[i+1]){
                return false;
            }
        }
        return true;
    }
}