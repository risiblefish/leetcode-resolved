package algorithms.math;

/**
 * @author Sean Yu
 */
public class No31 {
}


/**
 * 根据算法，按如下步骤操作：
 * （1）先找出最大的索引 k 满足 nums[k] < nums[k+1]，如果不存在，就翻转整个数组然后返回；
 * （2）再找出另一个最大索引 l 满足 nums[l] > nums[k]；
 * （3）交换 nums[l] 和 nums[k]；
 * （4）最后翻转 nums[k+1:]
 *
 * 举个例子：
 *
 * 比如 nums = [1,2,7,4,3,1]，下一个排列是什么？
 *
 * 我们找到第一个最大索引是 nums[1] = 2
 *
 * 再找到第二个最大索引是 nums[4] = 3
 *
 * 交换，nums = [1,3,7,4,2,1];
 *
 * 翻转，nums = [1,3,1,2,4,7]
 *
 * 完毕!
 *
 * 所以,
 *
 * 时间复杂度：O(n)O(n)O(n)
 *
 * 空间复杂度：O(1)O(1)O(1)
 *
 * 作者：powcai
 * 链接：https://leetcode.cn/problems/next-permutation/solutions/3830/xia-yi-ge-pai-lie-by-powcai/
 * 来源：力扣（LeetCode）
 * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
 */
class Solution31 {
    public void nextPermutation(int[] nums) {
        boolean findK = false;
        int k = nums.length - 2;
        while(k >= 0){
            if(nums[k] < nums[k+1]){
                findK = true;
                break;
            }
            k--;
        }
        if(!findK){
            reverseFromIndex(nums, 0);
        }
        else{
            int l = nums.length - 1;
            while(nums[l] <= nums[k] && l >= 0){
                l--;
            }
            //swap arr[k] and arr[l]
            int temp = nums[k];
            nums[k] = nums[l];
            nums[l] = temp;
            //reverse from k+1
            reverseFromIndex(nums, k+1);
        }
    }

    /**
     * 从idx处开始翻转数组
     */
    private void reverseFromIndex(int[] nums, int idx){
        int l = idx, r = nums.length - 1;
        while(l < r){
            int temp = nums[l];
            nums[l] = nums[r];
            nums[r] = temp;
            l++;
            r--;
        }
    }
}
