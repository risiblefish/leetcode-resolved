package algorithms.tree;

/**
 * No.559 n叉树的深度
 * @author Sean Yu
 */
public class No559 {
}

class Solution559_I {
    public int maxDepth(Node root) {
        return dfs(0,root);
    }

    private int dfs(int depth, Node node){
        if(node == null) return depth;
        depth++;
        int res = depth;
        for(Node child : node.children){
            res = Math.max(dfs(depth,child), res);
        }
        return res;
    }
}
