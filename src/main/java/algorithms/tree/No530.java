package algorithms.tree;

/**
 * @author Sean Yu
 */
public class No530 {
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
class Solution530 {
    Integer pre = null;
    int minGap = Integer.MAX_VALUE;
    public int getMinimumDifference(TreeNode root) {
        dfs(root);
        return minGap;
    }

    private void dfs(TreeNode root){
        if(root == null) {
            return;
        }
        dfs(root.left);
        if(pre == null){
            pre = root.val;
        }else {
            minGap = Math.min(minGap, root.val - pre);
            //do not forget to update pre
            pre = root.val;
        }
        dfs(root.right);
    }
}
