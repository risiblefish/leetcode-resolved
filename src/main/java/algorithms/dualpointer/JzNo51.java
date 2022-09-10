package algorithms.dualpointer;

/**
 * 剑指 Offer 51. 数组中的逆序对
 */
public class JzNo51 {
}

/**
 * 思路：
 * 容易想到2个特例：
 * （1）对于顺序数组比如 1 2 3 4 5， 每一个数都比后面的数更小，所以逆序对是0
 * （2）对于逆序数组比如 5 4 3 2 1  每个数都可以和前面所有数组成逆序对，比如4前面有5共1个数，3前面有5 4 共2个数，..., 共 1 + 2 + 3 + 4 = 10个逆序对
 *
 *  如果我们把数组划分为左右2段arrL,arrR，且这2段都【分别有序】
 *  那么，在merge这2段时，每选出一个arrR的数，这意味着这个数比arrL剩下的数都小，此时就能产生[arrL剩下的数的长度]个逆序对
 *
 *  利用分治的思想来统计逆序对
 *  比如 7 5 6 4
 *  （1）首先不断拆分到不能拆分，得到 [7] [5] [6] [4] 共4段，每段长度为1，所以每段产生的逆序对为0
 *  （2）将[7][5]合并，将[6][4]合并
 *   （2）- 1 ：
 *    arrL = 7
 *    arrR = 5
 *   进行归并排序的merge操作
 *   此时右边段的数5更小，所以先merge到辅助数组，此时左边还剩1个数，所以产生1个逆序对(7,5)
 *   此时原数组变为 [5 7] [6] [4]
 *   （2）-2：
 *   arrL = 6
 *   arrR = 4
 *   同理，merge过程产生1个逆序对(6,4)，此时原数组变为 [5 7][4 6]
 *  (3)将[5 7]进行合并[4 6]
 *  arrL = 5 7
 *  arrR = 4 6
 *  先选出4，此时arrL还剩2个数，所以产生2个逆序对（5 4） (7 4)
 *  然后选出5，不产生逆序对
 *  然后选出6，此时arrL还剩1个数，产生1个逆序对(7 6)
 *  最后选出7
 *  此时原数组变为[4 5 6 7]已有序
 *
 *  因为在归并过程中，每个数至多和其他数比较1次，所以产生的逆序对不会重复
 *
 *
 */
class SolutionJzNo51 {
    int[] arr, help;
    public int reversePairs(int[] nums) {
        if(nums.length < 2) return 0;
        arr = nums;
        help = new int[arr.length];
        return sortAndCount(0, nums.length - 1);
    }

    private int sortAndCount(int l, int r){
        if(l == r) return 0;
        int m = l + (r - l) / 2;
        //统计左半部分的逆序对
        int leftCount = sortAndCount(l, m);
        //统计有半部分的逆序对
        int rightCount = sortAndCount(m+1, r);
        if(arr[m] < arr[m+1]){
            return leftCount + rightCount;
        }
        //统计左右2个有序段中的逆序对
        int crossCount = merge(l,m,r);
        return leftCount + rightCount + crossCount;
    }

    private int merge(int l, int m, int r){
        int i = 0, count = 0, p1 = l, p2 = m + 1;
        while(p1 <= m && p2 <= r){
            if(arr[p1] <= arr[p2]){
                help[i++] = arr[p1++];
            }
            else{
                help[i++] = arr[p2++];
                count += m - p1 + 1;
            }
        }
        while(p1 <= m){
            help[i++] = arr[p1++];
        }
        while(p2 <= r){
            help[i++] = arr[p2++];
        }
        for(int cur = l ; cur <= r; cur++){
            arr[cur] = help[cur - l];
        }
        return count;
    }
}