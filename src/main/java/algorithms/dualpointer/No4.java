package algorithms.dualpointer;

/**
 * 4. 寻找两个正序数组的中位数
 * @author Sean Yu
 */
public class No4 {
    public static void main(String[] args) {
        Solution4 t = new Solution4();
        int[] nums1 = new int[]{0, 0, 0, 0, 0};
        int[] nums2 = new int[]{-1, 0, 0, 0, 0, 0, 1};
        System.out.println(t.findMedianSortedArrays(nums1, nums2));
    }
}

/**
 * 思路：
 * 将问题转化为 求第k小的数
 * 每次去掉 【k/2个左右】 最小的数,..., 直到k=1时，最小的数整个范围中第k小的数
 * 之所以用 左右， 是因为越界情况的存在，导致不能每次一定能去掉 k/2个 最小的数
 *
 * e.g.先看总长度为奇数的例子
 * nums1 = {1,3,5,7,9,11,13}
 * nums2 = {2,4}
 * 此时总长度 = 7 + 2 = 9， 所以需要求第k = （9+1）/2 = 5小的数， 令p1, p2表示当前在num1,num2中遍历到的数的下标
 * (1)
 * 初始时，k = 5, p1 = 0, p2 = 0
 *
 * (2)
 * step = 5 / 2 = 2,
 * 由于从p1,p2开始数step = 2个数后，都没有越界，所以我们比较nums1[p1+step-1]，nums2[p2 + step -1]，即3和4的大小
 * 由于3 < 4, 说明这step个数，要从nums1中去掉，所以更新p1的下标，p1指向nums1中的5, p2不更新
 *
 * (3)
 *  由于（2）去掉了2个最小的数，原问题求第5小的数，此时变成了求5-2=第3小的数
 *  step = 3 / 2 = 1
 *  由于从p1,p2开始数step = 1个数后，都没有越界，所以此时比较5和2的大小
 *  由于2 < 5， 说明这step个数，要从num2中去掉，此时更新p2的下标，p2指向num2中的4，p1不更新
 *
 * (4)
 *  由于(3)又去掉了1个最小的数，此时原问题变成了求3-1=第2小的数
 *  step = 2/2 = 1
 *  此时，p2已经指向了4，后面没有数了，步长更新为nums2.length - p2 + 1 = 1
 *  即比较5和4的大小，
 *  由于4 < 5，说明这step个数，需要从nums2中去掉，此时更新p2的下标，p2指向越界处，p1不更新
 *
 * （5）
 * 由于（4）又去掉了1个最小数，此时原问题变成求2-1=第1小的数
 * 在此之前，发现p2已经越界，所以从nums1里找到第1小的数返回即可，即返回5
 *
 *  至此，就求到了长度和为9的2个有序数组中，第5小的数，这个数也是这2个数组的中位数
 *
 *  对于长度和为偶数的情况，除了求第 k= (len + 1）/2 小的值之外， 再求第k+1小的值，然后对这2个值求平均值即可
 */
class Solution4 {
    int[] nums1, nums2;

    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        this.nums1 = nums1;
        this.nums2 = nums2;
        //2个数组总长度为奇数
        if ((nums1.length + nums2.length) % 2 == 1) {
            return findKth((nums1.length + nums2.length + 1) / 2 , 0, 0);
        }
        //2个数组总长度为偶数
        double sum = findKth((nums1.length + nums2.length + 1) / 2, 0, 0)
                   + findKth((nums1.length + nums2.length + 1) / 2  + 1, 0, 0);
        return sum / 2.0;
    }

    private int findKth(int k, int p1, int p2) {
        /* e.g.
         * nums1 = {},
         * nums2 = {1,2}
         * k = 1, p1 = 0, p2 = 0
         * 此时p1已经越界，所以只需返回nums2中第k小的数
         */
        if (p1 == nums1.length) {
            return nums2[p2 + k - 1];
        }
        if (p2 == nums2.length) {
            return nums1[p1 + k - 1];
        }
        if (k == 1) {
            return Math.min(nums1[p1], nums2[p2]);
        }
        int step = k / 2;
        //p1 + 步长可能越界，可能更新步长
        if (p1 + step - 1 >= nums1.length) {
            //步长 = 等差数列求项数 = nums1最后一个下标 - nums1当前下标 + 1
            step = (nums1.length - 1) - p1 + 1;
        }
        //同理，可能更新步长
        if (p2 + step - 1 >= nums2.length) {
            step = (nums2.length - 1) - p2 + 1;
        }
        //当步长最终确定后，此时newP1, newP2一定不会越界
        int newP1 = p1 + step - 1;
        int newP2 = p2 + step - 1;
        //去掉前step个最小的数，然后将指针指向新的位置
        if (nums1[newP1] <= nums2[newP2]) {
            p1 = newP1 + 1;
        } else {
            p2 = newP2 + 1;
        }
        //更新k值
        k -= step;
        return findKth(k, p1, p2);
    }
}
