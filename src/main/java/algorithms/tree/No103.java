package algorithms.tree;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * 103. 二叉树的锯齿形层序遍历
 * @author Sean Yu
 */
public class No103 {
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
 * 层序遍历的基础上，加上一个方向判断，如果是右，就将当前层序reverse加入结果集即可
 */
class Solution103 {
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> ans = new ArrayList();
        if(root == null){
            return ans;
        }
        Queue<TreeNode> q = new ArrayDeque();
        boolean left = true;
        q.add(root);
        while(!q.isEmpty()){
            int cnt = q.size();
            List<Integer> list = new ArrayList();
            while(cnt > 0){
                cnt--;
                TreeNode cur = q.poll();
                list.add(cur.val);
                if(cur.left != null){
                    q.add(cur.left);
                }
                if(cur.right != null){
                    q.add(cur.right);
                }
            }
            if(!left){
                reverseList(list);
            }
            ans.add(list);
            left = !left;
        }
        return ans;
    }

    private void reverseList(List<Integer> list){
        int l = 0 , r = list.size()  - 1;
        while(l < r){
            int temp = list.get(r);
            list.set(r, list.get(l));
            list.set(l, temp);
            l++;
            r--;
        }
    }
}
