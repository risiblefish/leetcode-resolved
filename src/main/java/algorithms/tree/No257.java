package algorithms.tree;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sean Yu
 */
public class No257 {
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
 * 回溯的话，可以避开每次构造新的字符串
 */
class Solution257 {
    public List<String> binaryTreePaths(TreeNode root) {
        List<String> res = new ArrayList();
        if(root == null){
            return res;
        }
        if(root.left == null && root.right == null){
            res.add(root.val + "");
        }
        List<TreeNode> path = new ArrayList();
        path.add(root);

        if(root.left != null){
            path.add(root.left);
            traceBack(root.left, path, res);
            path.remove(path.size() - 1);
        }

        if(root.right != null){
            path.add(root.right);
            traceBack(root.right, path, res);
            path.remove(path.size() - 1);
        }
        return res;
    }

    private void traceBack(TreeNode curr, List<TreeNode> path, List<String> res){
        if(curr.left == null && curr.right == null){
            StringBuilder sb = new StringBuilder();
            for(TreeNode node : path){
                if(sb.length() == 0){
                    sb.append(node.val);
                }
                else {
                    sb.append("->");
                    sb.append(node.val);
                }
            }
            res.add(sb.toString());
            return;
        }
        if(curr.left != null){
            path.add(curr.left);
            traceBack(curr.left, path, res);
            path.remove(path.size() - 1);
        }

        if(curr.right != null){
            path.add(curr.right);
            traceBack(curr.right, path, res);
            path.remove(path.size() - 1);
        }
    }
}
