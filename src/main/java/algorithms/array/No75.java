package algorithms.array;

/**
 * 75. 颜色分类
 * @author Sean Yu
 */
public class No75 {
}

/**
 * 思路： 这道题又叫荷兰国旗问题
 * 参考912题的快排解法
 * 这里题目只有3种颜色，所以可以选择中间的颜色为target，将比它小的放到之前，比它大的放到之后
 */
class Solution75 {
    public void sortColors(int[] nums) {
        int lessBound = -1, moreBound = nums.length;
        int cur = 0;
        int target = 1;
        while(cur < moreBound){
            if(nums[cur] < target){
                swap(nums, cur, lessBound+1);
                lessBound++;
                cur++;
            }
            else if(nums[cur] == target){
                cur++;
            }
            else {
                swap(nums, cur, moreBound - 1);
                moreBound--;
            }
        }
    }
    private void swap(int[] arr, int i, int j){
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
