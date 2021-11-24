package algorithms.tree;

/**
 * 617. 合并二叉树
 * @author Sean Yu
 */
public class No617 {
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
 * 思路：
 * 递归，但是是传入2个节点
 *
 * 复杂度分析：
 * 只有2个节点都非空时，才会产生合并操作，当有一个节点为空时，直接返回另一个节点，所以被访问到的节点数 = min(树1的节点数，树2的节点数)
 * 令 m 和 n 分别是两个二叉树的节点个数。
 * 时间复杂度: O（min(m,n)）
 * 空间复杂度：O(min(m,n))，空间复杂度取决于递归调用的层数，递归调用的层数不会超过较小的二叉树的最大高度，最坏情况下，二叉树的高度等于节点数。
 */
class Solution617 {
    public TreeNode mergeTrees(TreeNode root1, TreeNode root2) {
        if(root1 == null) {
            return root2;
        }
        if(root2 == null) {
            return root1;
        }
        TreeNode root = new TreeNode(root1.val + root2.val);
        root.left = mergeTrees(root1.left, root2.left);
        root.right = mergeTrees(root1.right, root2.right);
        return root;
    }
}
