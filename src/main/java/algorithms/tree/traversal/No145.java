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
 *
 * 这个解法是从前序遍历的非递归版本推演而来
 * 在前序遍历的非递归版本中，对栈内元素的迭代策略是：
 * （1）弹出1个节点，
 * （2）加入节点值，
 * （3）如果该节点有右节点，则压栈，
 * （4）如果该节点有左节点，则压栈
 *
 * 因为栈是后进先出，前序遍历期望是 根 左 右，所以左节点要最后入栈，右节点要先入栈
 * 第(2)步由于只是记录，不涉及栈操作，所以可以放在（2）~（4）之间任一步执行
 *
 * 后序遍历：
 * 利用上述步骤，可以得到前序遍历的结果，如果我们把左右节点的压栈顺序交换一下，得到的结果是 根 右 左， 我们只需将这个结果逆序，就能得到 左 右 根（后序遍历）
 *
 *
 */
class Solution145_II {
    public List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList();
        if (root == null) {
            return res;
        }
        Deque<TreeNode> s1 = new ArrayDeque();
        Deque<TreeNode> s2 = new ArrayDeque();
        s1.push(root);
        while (!s1.isEmpty()) {
            TreeNode curr = s1.pop();
            //由 "记录结果"替换为"入s2栈"， 便于最后做逆序，这一步顺序可以随意
            s2.push(curr);
            //先左
            if (curr.left != null) {
                s1.push(curr.left);
            }
            //再右
            if (curr.right != null) {
                s1.push(curr.right);
            }
        }
        while (!s2.isEmpty()) {
            res.add(s2.pop().val);
        }
        return res;
    }
}

