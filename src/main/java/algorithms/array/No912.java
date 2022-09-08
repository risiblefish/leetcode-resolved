package algorithms.array;

import java.util.Arrays;
import java.util.Random;

/**
 * 912. 排序数组
 *
 * @author Sean Yu
 */
public class No912 {
    public static void main(String[] args) {
//        int[] nums = new int[]{-7, -5, -4, -1, -1, 4, 0, 7, 0, 9};
        int[] nums = new int[]{9, 8, 7, 6, 5, 4, 1, 2, 3};
        System.out.println(Arrays.toString(new Solution912_QuickSort().sortArray(nums)));
    }
}


/**
 * 思路： 堆排序
 * <p>
 * 构建大根堆，将数组想象成一颗完全二叉树（即从左往右填，只有最后一层可能没填满）
 * 对数组的每个下标i,
 * 父节点对应数组下标 = (i-1)/2
 * 左孩子对应数组下标 = = 2 * i + 1
 * 右孩子对应数组下标 = = 2 * i + 2
 */
class Solution912_HeapSort {
    int[] arr;

    public int[] sortArray(int[] nums) {
        arr = nums;
        int n = arr.length;
        //不断插入节点构建并维护大根堆
        for (int i = 0; i < n; i++) {
            insert(i);
        }
        //不断将头结点与最后一个节点交换，然后维护大根堆，并将最后一个节点end范围缩小
        for (int i = n - 1; i >= 0; i--) {
            popMax(i);
        }
        return nums;
    }

    private void popMax(int end) {
        //首先将最顶部和最后一个节点end交换，交换之后，end就视为不可用
        swap(0, end);
        int i = 0;
        //如果当前节点有左孩子：这样设置判断条件，是因为完全二叉树的插入顺序就是一定先插入左孩子，然后插入右孩子，如果没有左孩子，那么一定没有右孩子
        while (i * 2 + 1 < end) {
            int left = i * 2 + 1;
            int larger = left;
            int right = left + 1;
            //如果右孩子可用
            if (right < end) {
                larger = arr[right] > arr[left] ? right : left;
            }
            //如果交换后的头结点已经比左右孩子都大，则已经是大根堆
            if (arr[i] >= arr[larger]) {
                break;
            }
            //否则，和比自己大的，较大的孩子交换位置
            swap(i, larger);
            //继续判断交换后，堆是否还需要调整
            i = larger;
        }
    }

    /**
     * 先按完全二叉树顺序插入，如果当前节点比他父节点大，就不断交换上浮
     *
     * @param i
     */
    private void insert(int i) {
        int parent = (i - 1) / 2;
        while (arr[i] > arr[parent]) {
            swap(i, parent);
            i = parent;
            parent = (i - 1) / 2;
        }
    }

    private void swap(int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}


/**
 * 快排：
 * <p>
 * 核心思路： 划分partition
 * <p>
 * 从数组中选出一个数target，将比target小的放到target左边，将比target大的放到target右边， 将和target一样大的放中间
 * <p>
 * 具体操作：
 * （1）我们令L,R上最后一个数为target，即arr[R], 然后在L到R范围上进行划分
 * （2）用cur记录当前下标，从L开始，然后维护2个边界，
 * lessBound 代表比target小的右边界，它指向的数arr[lessBound]，就是当前最后一个小于target的数，初始化为L-1，
 * moreBound 代表比target大的左边界, 它指向的数arr[moreBound]，就是当前最后一个比target大的数，初始化为R
 * 可以把这2个边界想象成2个括号，)内的就是小于target的数，(内的就是大于target的数，这2个括号不断地向中间移动，
 * 之所以2个边界要初始化为L-1和R，   比如 )3,1,2,(5  L-1就是让刚开始 )左边没有比target更小数， 因为5是target，所以意味着(右边也没有比target更大的数
 * （3）开始划分
 * I. 如果arr[cur] < target， 那么将arr[cur]和lessBound下一个数交换，然后扩大lessBound
 * e.g. ) 3, 1, 2, (5  cur一开始指向3， 3比5小，3和)下一个数交换（和自己交换），然后 小于边界扩大， cur也后移， 变成 3),1,2,(5
 * <p>
 * II. 如果arr[cur] == target, 那么直接将cur往后移
 * <p>
 * III. 如果arr[cur] > target, 将arr[cur]和 大于边界 前1个数交换，然后扩大 大于边界， 但cur不变，因为交换过后的数还没有进行比较
 * <p>
 * 当cur 撞上 大于边界时， 说明此时已经划分完成
 * <p>
 * 随机的重要性：
 * 因为我们选择了L,R上最后一个数作为target，最坏的情况下，比如1,2,3,4,5 ,每次划分，右边界都是不动的，所以复杂度变成o(n^2)
 * 为了降低这种概率，我们每次在划分之前，在L,R区间上随机选出一个数，与最后一个数做交换
 */
class Solution912_QuickSort {
    int[] arr;
    Random rand = new Random();

    public int[] sortArray(int[] nums) {
        arr = nums;
        sort(0, arr.length - 1);
        return nums;
    }

    private void sort(int l, int r) {
        if (l >= r) {
            return;
        }
        int randIdx = l + rand.nextInt(r - l + 1);
        swap(randIdx, r);
        int[] eq = partition(l, r);
        sort(l, eq[0] - 1);
        sort(eq[1] + 1, r);
    }

    private int[] partition(int l, int r) {
        if (l > r) {
            return new int[]{-1, -1};
        }
        if (l == r) {
            return new int[]{l, r};
        }
        int cur = l;
        int lessBound = l - 1;
        int moreBound = r;
        int target = arr[moreBound];
        while (cur < moreBound) {
            if (arr[cur] == target) {
                cur++;
            } else if (arr[cur] < target) {
                swap(cur, lessBound + 1);
                cur++;
                lessBound++;
            } else {
                swap(cur, moreBound - 1);
                moreBound--;
            }
        }
        //cur == moreBound
        swap(cur, r);
        return new int[]{lessBound + 1, moreBound};
    }


    private void swap(int i, int j) {
        if (i == j) {
            return;
        }
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}


/**
 * 归并排序 - 递归版本：
 *
 * 思路：每次将数组不断地划分成左右2部分，使左右2部分有序，然后再merge
 */
class Solution_MergeSort_Recursion {
    int[] arr;

    public int[] sortArray(int[] nums) {
        arr = nums;
        sort(0, nums.length - 1);
        return nums;
    }

    private void sort(int l, int r) {
        if (l == r) {
            return;
        }
        int mid = l + (r - l) / 2;
        sort(l, mid);
        sort(mid + 1, r);
        merge(l, mid, r);
    }

    private void merge(int l, int m, int r) {
        int[] help = new int[r - l + 1];
        int i = 0, p1 = l, p2 = m + 1;
        while (p1 <= m && p2 <= r) {
            help[i++] = arr[p1] <= arr[p2] ? arr[p1++] : arr[p2++];
        }
        while (p1 <= m) {
            help[i++] = arr[p1++];
        }
        while (p2 <= r) {
            help[i++] = arr[p2++];
        }
        for (int cur = l; cur <= r; cur++) {
            arr[cur] = help[cur - l];
        }
    }
}
