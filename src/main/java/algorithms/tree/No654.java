package algorithms.tree;

/**
 * 654. 最大二叉树
 * @author Sean Yu
 */
public class No654 {
}

/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */

/**
 * 思路： 递归
 * 依据题意：
 * （1）先遍历数组找到最大值，然后作为根
 * （2）然后对最大值左半部分重复（1）构造左子树，对最大值有半部分重复（1）构造右子树，因此我们需要在递归函数里传入代表左右边界的下标
 * （3）注意越界的情况，比如最大值就是最左边的值，那它的左子树元素在数组的范围就是[0,-1]，此时左边界>右边界，这种情况直接返回null
 * 复杂度分析：
 * 每次找到最大值，需要遍历left 到 right 上的所有节点，假设每次遍历的节点数为k，k是不大于N的，所以为O（N）
 * 一共要构造n个节点，即调用至少n次construct方法，还要算上考虑构造底层null的节点，所以为O（N）
 * 故时间复杂度: O(N^2)
 * 空间复杂度：
 * 因为要调用n次construct，故递归的深度为n，每次存储1个节点，故空间复杂度为O(N)
 */
class Solution654 {
    public TreeNode constructMaximumBinaryTree(int[] nums) {
        return construct(nums,0,nums.length - 1);
    }

    private TreeNode construct(int[] nums, int left, int right){
        if(left > right) {
            return null;
        }
        int maxIndex = left;
        //find maxIndex
        for(int i = left; i <= right ; i++){
            if(nums[i] > nums[maxIndex]){
                maxIndex = i;
            }
        }
        TreeNode root = new TreeNode(nums[maxIndex]);
        root.left = construct(nums,left, maxIndex - 1);
        root.right = construct(nums, maxIndex+1, right);
        return root;
    }
}
