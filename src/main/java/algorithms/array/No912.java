package algorithms.array;

import java.util.Arrays;

/**
 * 912. 排序数组
 * @author Sean Yu
 */
public class No912 {
    public static void main(String[] args) {
        int[] nums = new int[]{-4,0,7,4,9,-5,-1,0,-7,-1};
        System.out.println(Arrays.toString(new Solution912().sortArray(nums)));
    }
}


/**
 *  思路： 堆排序
 *
 *  构建大根堆，将数组想象成一颗完全二叉树（即从左往右填，只有最后一层可能没填满）
 *  对数组的每个下标i,
 *  父节点对应数组下标 = (i-1)/2
 *  左孩子对应数组下标 = = 2 * i + 1
 *  右孩子对应数组下标 = = 2 * i + 2
 *
 */
class Solution912 {
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
            if(right < end){
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
