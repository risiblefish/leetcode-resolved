package algorithms.hashtable;

/**
 * 27. 移除元素
 *
 * @author Sean Yu
 */
public class No27 {
}

/**
 * 思路： 双指针
 * 用curr从左往右扫描
 * 遇到值为val的，就和last交换，由于last可能也是val，所以curr不能急着往后，只能把last向左移动
 * 遇到值不为val的，就curr继续向右移动
 * 需要注意的是，curr和last的区间是[0,len-1],而不是[0,len)，所以curr = last这种情况也在有效的区间里
 */
class Solution27 {
    public int removeElement(int[] nums, int val) {
        if (nums.length == 0) {
            return 0;
        }
        int curr = 0, last = nums.length - 1;
        while (curr <= last) {
            if (nums[curr] == val) {
                int temp = nums[curr];
                nums[curr] = nums[last];
                nums[last] = temp;
                last--;
            } else {
                curr++;
            }
        }
        return last + 1;
    }
}
