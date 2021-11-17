package algorithms.tree;

/**
 *  No.404. 左叶子之和
 * @author Sean Yu
 */
public class No404 {
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
 * 首先明确左叶子的定义，首先它是叶子节点（没有子节点），其次它是左节点（即父节点的left）
 * 在此基础上，只需要正常遍历一遍树，然后用一个boolean来记录它是否是左节点即可
 */
class Solution404 {
    public int sumOfLeftLeaves(TreeNode root) {
        return traversal(root, false);
    }

    private int traversal(TreeNode curr, boolean isLeft){
        if(curr == null) {
            return 0;
        }
        //是否是叶子节点
        if(curr.left == null && curr.right == null){
            //是否是左叶子
            return isLeft ? curr.val : 0;
        }
        return traversal(curr.left, true) + traversal(curr.right, false);
    }
}
