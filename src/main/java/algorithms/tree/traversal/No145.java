package algorithms.tree.traversal;

import algorithms.tree.TreeNode;

import java.util.*;

/**
 * 145. 二叉树的后序遍历
 *
 * @author Sean Yu
 */
public class No145 {
    public static void main(String[] args) {
    }
}

/**
 * 思路1  ： 递归
 */
class Solution145 {
    public List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList();
        if (root == null) {
            return res;
        }
        res.addAll(postorderTraversal(root.left));
        res.addAll(postorderTraversal(root.right));
        res.add(root.val);
        return res;
    }
}

/**
 * 思路2 ： 迭代
 */
class Solution145_II {
    public List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList();
        Deque<TreeNode> stack = new ArrayDeque();
        TreeNode lastAdd = null;
        while (root != null || !stack.isEmpty()) {
            while (root != null) {
                stack.push(root);
                root = root.left;
            }
            if (!stack.isEmpty()) {
                root = stack.pop();
                //一个节点能被添加的条件：当前节点没有右节点，或者 ，当前节点的右子树已经遍历完成
                //右子树已遍历完成的条件： 当前节点的右节点 = 上一个添加的节点
                if (root.right == null || root.right == lastAdd) {
                    res.add(root.val);
                    lastAdd = root;
                    root = null;
                } else {
                    //再次入栈
                    stack.push(root);
                    root = root.right;
                }
            }
        }
        return res;
    }
}
