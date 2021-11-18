package algorithms.tree;

/**
 * No.111. 二叉树的最小深度
 *
 * @author Sean Yu
 */
public class No111 {
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
 * 思路1： 递归
 */
class Solution111 {
    public int minDepth(TreeNode root) {
        return m(root, 0);
    }

    private int m(TreeNode node, int lastDepth) {
        if (node == null) {
            return lastDepth;
        }
        //如果当前node不为空
        int currDepth = lastDepth + 1;
        if (node.left == null && node.right == null) {
            return currDepth;
        }
        if (node.left == null && node.right != null) {
            return m(node.right, currDepth);
        }
        if (node.right == null && node.left != null) {
            return m(node.left, currDepth);
        }
        return Math.min(m(node.left, currDepth), m(node.right, currDepth));
    }
}
