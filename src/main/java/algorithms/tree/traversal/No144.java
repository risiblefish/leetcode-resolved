package algorithms.tree.traversal;

import algorithms.tree.TreeNode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * 144. 二叉树的前序遍历
 * @author Sean Yu
 */
public class No144 {
}

/**
 * 思路1 ： 递归
 */
class Solution144 {
    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList();
        if(root == null) {
            return res;
        }
        res.add(root.val);
        res.addAll(preorderTraversal(root.left));
        res.addAll(preorderTraversal(root.right));
        return res;
    }
}


/**
 * 思路2：迭代（借助栈）
 */
class Solution_II {
    public List<Integer> preorderTraversal(TreeNode root) {
        Deque<TreeNode> stack = new ArrayDeque();
        List<Integer> res = new ArrayList();
        if(root == null){
            return res;
        }
        stack.push(root);
        while(!stack.isEmpty()){
            TreeNode node = stack.pop();
            res.add(node.val);
            if(node.right != null){
                stack.push(node.right);
            }
            if(node.left != null){
                stack.push(node.left);
            }
        }
        return res;
    }

    public List<Integer> preorderTraversal1(TreeNode root) {
        Deque<TreeNode> stack = new ArrayDeque();
        List<Integer> res = new ArrayList();
        stack.push(root);
        while(root != null || !stack.isEmpty()){
            TreeNode node = stack.pop();
            if(node.right != null){
                stack.push(node.right);
            }
            if(node.left != null){
                stack.push(node.left);
            }
            res.add(root.val);
        }
        return res;
    }
}
