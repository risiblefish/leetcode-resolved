package algorithms.tree;

/**
 * @author Sean Yu
 */
public class No104 {
}

/**
 * 思路1： 递归
 */
class Solution104_I {
    public int maxDepth(TreeNode root) {
        return dfs(0, root);
    }

    private int dfs(int depth, TreeNode node) {
        if (node == null) {
            return depth;
        }
        depth++;
        return Math.max(dfs(depth, node.left), dfs(depth, node.right));
    }
}
