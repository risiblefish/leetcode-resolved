package algorithms.arrayandhash;

/**
 * 剑指 Offer 03. 数组中重复的数字
 * @author Sean Yu
 */
public class JzNo03 {
}

/**
 * 用set或者散列表去重，这里用了散列
 */
class SolutionJzNo03 {
    public int findRepeatNumber(int[] nums) {
        //题目保证n在[2,100000]
        boolean[] find = new boolean[100001];
        for(int i = 0 ; i < nums.length ; i++){
            if(find[nums[i]]){
                return nums[i];
            }
            find[nums[i]] = true;
        }
        return 0;
    }
}