package algorithms.tree.traversal;

import algorithms.tree.TreeNode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * 二叉树的中序遍历
 *
 * @author Sean Yu
 */
public class No94 {
    public static void main(String[] args) {
        TreeNode node = new TreeNode(1);
        node.left = new TreeNode(2);
        new Solution94_II().inorderTraversal(node);
    }

}


/**
 * 思路1 ： 遍历
 */
class Solution94 {
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList();
        if (root == null) {
            return res;
        }
        res.addAll(inorderTraversal(root.left));
        res.add(root.val);
        res.addAll(inorderTraversal(root.right));
        return res;
    }
}

/**
 * 思路2 : 迭代
 * （1）每遍历一个节点，将它的左子孙全部入栈，直到左子节点为空
 * （2）从栈中弹出一个节点记为curr，记录值，并将该节点的右节点作为下一个要遍历的节点，重复（1）
 */
class Solution94_II {
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList();
        if (root == null) {
            return res;
        }
        Deque<TreeNode> stack = new ArrayDeque();
        TreeNode curr = root;
        while (curr != null || !stack.isEmpty()) {
            if (curr != null) {
                stack.push(curr);
                curr = curr.left;
            } else {
                curr = stack.pop();
                res.add(curr.val);
                curr = curr.right;
            }
        }
        return res;
    }
}

