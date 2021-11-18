package algorithms.tree;

/**
 * 112. 路径总和
 * @author Sean Yu
 */
public class No112 {
}


/**
 * Definition for a binary tree node.
 * public class TreeNode {
 * int val;
 * TreeNode left;
 * TreeNode right;
 * TreeNode() {}
 * TreeNode(int val) { this.val = val; }
 * TreeNode(int val, TreeNode left, TreeNode right) {
 * this.val = val;
 * this.left = left;
 * this.right = right;
 * }
 * }
 */

/**
 *  思路： 递归
 *  复杂度分析：
 *  时间：每个节点会遍历1次，所以是O（N）
 *  空间：最坏的情况下，树会退化到单链，栈里会存储N个节点，所以是O(N)
 *
 */
class Solution112 {
    public boolean hasPathSum(TreeNode root, int targetSum) {
        if (root == null) {
            return false;
        }
        if (root.left == null && root.right == null) {
            return root.val == targetSum;
        }
        return hasPathSum(root.left, targetSum - root.val) || hasPathSum(root.right, targetSum - root.val);
    }
}
