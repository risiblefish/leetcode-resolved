package algorithms.binarysearch;

import java.util.ArrayList;

/**
 * 153. 寻找旋转排序数组中的最小值
 * @author Sean Yu
 */
public class No153 {
    public static void main(String[] args) {
        ArrayList<String> list = new ArrayList<String>();
        list.add("a");
        list.remove("b");

    }
}

/**
 *
 */
class Solution153 {
    public int findMin(int[] nums) {
        int l = 0, r = nums.length - 1;
        while(l < r){
            int m = l + (r - l) /2;
            if(nums[m] > nums[r]){
                l = m + 1;
            }else{
                r = m;
            }
        }
        return nums[l];
    }
}


