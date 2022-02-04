package algorithms.array;

/**
 * 1725. 可以形成最大正方形的矩形数目
 * @author Sean Yu
 */
public class No1725 {
}


class Solution1725 {
    public int countGoodRectangles(int[][] rectangles) {
        int cnt = 0;
        int maxLen = -1;
        for(int[] r : rectangles){
            int len = Math.min(r[0],r[1]);
            if(len > maxLen){
                maxLen = len;
                cnt = 1;
            }
            else if(len  == maxLen){
                cnt++;
            }
        }
        return cnt;
    }
}