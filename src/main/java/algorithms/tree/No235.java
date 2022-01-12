package algorithms.tree;

/**
 * 235. 二叉搜索树的最近公共祖先
 * @author Sean Yu
 * @date 7/12/2021 下午4:50
 */
public class No235 {
}

/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */

/**
 * 思路 ： 题目保证p,q一定存在
 */
class Solution235 {
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if(root.val > p.val && root.val > q.val){
            return lowestCommonAncestor(root.left, p, q);
        }
        if(root.val < p.val && root.val < q.val){
            return lowestCommonAncestor(root.right,p, q);
        }
        return root;
    }
}
