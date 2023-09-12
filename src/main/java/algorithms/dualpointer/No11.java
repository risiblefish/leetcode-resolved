package algorithms.dualpointer;

/**
 * 11. 盛水最多的容器
 * @author Sean Yu
 */
public class No11 {
}

/**
 * 双指针
 * 盛水面积 = 短板高度*宽度
 * 一开始把左右指针分别放于左右两端，这样做是为了让宽度达到最大
 * 假设左右2端各有一块板，且长短不一，
 * （1）如果向内移动长板，则面积只会变小，因为短板长度只可能变得更小，宽度-1， 没有增大的可能性。
 * （2）反之，如果向内移动短板，则原来的长板有可能变成短板，宽度-1， 新短板高度*（-1后的宽度） 有可能增大
 * 所以，
 * 要从短板方向上进行移动，
 * 如果短板移动后，高度比之前的短板更小，则可跳过计算，因为宽度和高度都变小了
 */
class Solution11 {
    public int maxArea(int[] height) {
        int l = 0, r = height.length - 1;
        int max = Math.min(height[l], height[r]) * (r - l);
        while(l < r){
            //是否要跳过本次计算
            boolean pass = false;
            if(height[l] <= height[r]){
                l++;
                pass = height[l] <= height[l-1];
            }else{
                r--;
                pass = height[r] <= height[r+1];
            }
            if(!pass){
                int sum = Math.min(height[l], height[r]) * (r - l);
                max = Math.max(max, sum);
            }
        }
        return max;
    }
}
