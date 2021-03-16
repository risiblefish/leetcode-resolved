package algorithms.dualpointer;

/**
 * 11. 盛水最多的容器
 * @author Sean Yu
 */
public class No11 {
}

/**
 * 暴力法（超时）
 */
class SolutionI {
    public int maxArea(int[] height) {
        int n = height.length;
        int currHeighest;
        int max = 0;
        for(int i = 0 ; i < n ; i++){
            currHeighest = 0;
            for(int j = n-1 ; j > i ; j--){
                if(height[j] > currHeighest) {
                    int square = Math.min(height[i],height[j]) * (j-i);
                    max = Math.max(max,square);
                    currHeighest = height[j];
                }
            }
        }
        return max;
    }
}

/**
 * 双指针
 */
class SolutionII {
    public int maxArea(int[] height) {
        int n = height.length;
        int l = 0, r = n-1;
        int square = 0;
        while(l < r){
            square = Math.max(square, Math.min(height[l],height[r]) * (r-l));
            if(height[l] <= height[r]){
                l++;
            }else {
                r--;
            }
        }
        return square;
    }
}
