package algorithms.binarysearch;

/**
 * No.63 x的平方根
 * @author Sean Yu
 */
public class No63 {
}

class Solution63 {
    public int mySqrt(int x) {
        int l = 0, r = x, ans = -1;
        //思考这里为何是<=而不是<    答：l==r的时候仍然有可能此时就是解
        while(l <= r){
            int mid = l + (r-l)/2;
            //精度问题
            long sum = (long)mid * mid;
            if(sum > x){
                r = mid - 1;
            }
            else{
                //因为要去掉小数，所以在取小值的时候对答案进行记录
                ans = mid;
                l = mid + 1;
            }
        }
        return ans;
    }
}
