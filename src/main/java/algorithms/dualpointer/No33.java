package algorithms.dualpointer;

/**
 * 33. 搜索旋转排序数组
 * @author Sean Yu
 */
public class No33 {
    public static void main(String[] args) {
        Solution33 test = new Solution33();
        int[] nums = {3,1};
        int target = 4;
        System.out.println(test.search(nums, target));
    }
}

class Solution33 {
    public int search(int[] nums, int target) {
        int l = 0, r = nums.length - 1;
        while (l <= r) {
            int m = l + (r - l) / 2;
            if (nums[m] == target) {
                return m;
            }
            //此时，nums[m]一定不等于target
            //中点比右边大，说明中点到右侧一定存在波谷，说明左侧到中点一定是有序的
            if (nums[m] > nums[r]) {
                //如果target落在有序区间，即左区间
                if (nums[l] <= target && target < nums[m]) {
                    r = m - 1;
                } else {
                    l = m + 1;
                }
            }
            //否则，即nums[m] < nums[r], 中点到右侧一定是有序的
            //不难证明，如果m,r之间有波谷，而r又>m，
            //那么一定m->0是下降的，从0->r是上升的，而m<r，所以在0上升到r的过程中，一定会再产生1个m，题目保证每个数唯一，矛盾
            else {
                //如果target落在有序区间，即右区间
                if (nums[m] < target && target <= nums[r]) {
                    l = m + 1;
                } else {
                    r = m - 1;
                }
            }
        }
        return -1;
    }
}

class Solution {
    int[] arr;
    int target;
    public int search(int[] nums, int target) {
        arr = nums;
        this.target = target;
        return search(0, nums.length - 1);
    }

    private int search(int l, int r){
        if(l > r) return -1;
        int m = l + (r - l) / 2;
        //找到
        if(arr[m] == target) return m;
        //波谷在[m,r]上
        if(arr[m] > arr[r]){
            if(arr[l] <= target && target < arr[m]){
                r = m - 1;
            }else {
                l = m + 1;
            }
        }
        //波谷在[l,m]上
        else {
            if(arr[m] < target && target <= arr[r]){
                l = m + 1;
            }else {
                r = m - 1;
            }
        }
        return search(l, r);
    }
}
