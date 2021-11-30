package algorithms.tree;

import java.util.ArrayList;
import java.util.List;

/**
 * 路径总和2
 * @author Sean Yu
 */
public class No113 {
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
class Solution113 {
    public List<List<Integer>> pathSum(TreeNode root, int targetSum) {
        List<List<Integer>> res = new ArrayList();
        List<Integer> path = new ArrayList();
        dfs(root, targetSum, path, res);
        return res;
    }

    private void dfs(TreeNode curr, int target, List<Integer> path, List<List<Integer>> list) {
        if (curr == null) {
            return;
        }
        path.add(curr.val);
        //判断是否是叶子节点
        //如果是叶子节点的情况下，满足条件，则搜集路径
        if (curr.left == null && curr.right == null && target == curr.val) {
            list.add(new ArrayList(path));
        }
        //如果不是叶子节点，则继续搜索左右子树
        else {
            dfs(curr.left, target - curr.val, path, list);
            dfs(curr.right, target - curr.val, path, list);
        }
        //回溯（撤销前面path.add(curr.val)的动作），无论是否是叶子节点，都需要进行回溯
        path.remove(path.size() - 1);
    }
}
