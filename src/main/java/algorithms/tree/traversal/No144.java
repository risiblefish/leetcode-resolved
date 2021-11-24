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
 *
 * 最开始先把头结点入栈
 * 迭代过程（针对栈）：
 * 当栈不为空时，
 * 每次弹出一个节点记为node,
 * 记录node.val
 * 如果node有右节点，则压栈右节点
 * 如果node有左节点，则压栈左节点
 *
 *
 * 如此往复，直到栈空
 */
class Solution_II {
    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList();
        if(root == null){
            return res;
        }
        Deque<TreeNode> stack = new ArrayDeque();
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
}
