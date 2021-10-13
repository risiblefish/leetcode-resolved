package algorithms.tree.traversal;

import algorithms.tree.TreeNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * No.102 树的层序遍历
 * @author Sean Yu
 */
public class No102 {
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
 * 思路：
 * 利用队列 和 计数
 */
class Solution {
    public List<List<Integer>> levelOrder(TreeNode root) {
        Queue<TreeNode> q = new LinkedList();
        List<List<Integer>> res = new ArrayList();
        if (root == null) {
            return res;
        }
        q.add(root);
        while (!q.isEmpty()) {
            int count = q.size();
            List<Integer> list = new ArrayList();
            while (count > 0) {
                TreeNode curr = q.poll();
                list.add(curr.val);
                if (curr.left != null) {
                    q.add(curr.left);
                }
                if (curr.right != null) {
                    q.add(curr.right);
                }
                count--;
            }
            res.add(list);
        }
        return res;
    }
}
