package algorithms.tree.traversal;

import algorithms.tree.TreeNode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * No.199 二叉树的右视图
 * @author Sean Yu
 */
public class No199 {
}



/**
 * 思路： 利用queue进行层序遍历，只记录 每层的最后一个节点 的值
 */
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
class Solution199 {
    public List<Integer> rightSideView(TreeNode root) {
        List<Integer> res = new ArrayList();
        if(root == null) return res;
        Deque<TreeNode> q = new ArrayDeque();
        q.add(root);
        while(!q.isEmpty()){
            int cnt = q.size();
            while(cnt > 0){
                TreeNode cur = q.poll();
                if(cur.left != null) {
                    q.add(cur.left);
                }
                if(cur.right != null){
                    q.add(cur.right);
                }
                if(cnt == 1){
                    res.add(cur.val);
                }
                cnt--;
            }
        }
        return res;
    }
}
