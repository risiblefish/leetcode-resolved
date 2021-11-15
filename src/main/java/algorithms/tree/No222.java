package algorithms.tree;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * 222. 完全二叉树的节点个数
 * @author Sean Yu
 */
public class No222 {
}

/**
 * 思路1 ：递归
 */
class Solution222_1 {
    public int countNodes(TreeNode root) {
        if(root == null) {
            return 0;
        }
        return countNodes(root.left) + countNodes(root.right) + 1;
    }
}

/**
 * 思路2: 非递归 -》 层序遍历
 */
class Solution222_2 {
    public int countNodes(TreeNode root) {
        if(root == null){
            return 0;
        }
        Deque<TreeNode> q = new ArrayDeque();
        int sum = 0;
        q.add(root);
        while(!q.isEmpty()){
            int count = q.size();
            sum += count;
            while(count > 0){
                TreeNode node = q.poll();
                if(node.left != null) {
                    q.add(node.left);
                }
                if(node.right != null){
                    q.add(node.right);
                }
                count--;
            }
        }
        return sum;
    }
}


