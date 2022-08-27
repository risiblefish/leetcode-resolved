package algorithms.dualpointer;

/**
 * 35. 搜索插入位置
 * @author Sean Yu
 */
public class No35 {
    public static void main(String[] args) {
        int[] nums = new int[]{1};
        int target = 1;
        System.out.println(new Solution35().searchInsert(nums, target));
    }
}

class Solution35 {
    public int searchInsert(int[] nums, int target) {
        int l = 0 , r = nums.length - 1;
        while(l < r){
            int m = l + (r - l) / 2;
            if(nums[m] == target){
                return m;
            }
            if(nums[m] < target){
                l = m + 1;
            }
            else {
                r = m - 1;
            }
        }
        //target < nums[l] 和 target == nums[l]， 都返回l，分别表示要插入的位置，和存在的索引的位置
        //target > nums[l] 返回 l + 1 表示要在后面新插入
        return target <= nums[l] ? l : l + 1;
    }
}
