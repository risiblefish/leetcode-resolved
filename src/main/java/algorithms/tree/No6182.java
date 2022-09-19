package algorithms.tree;

import java.util.LinkedList;

/**
 * 6182. 反转二叉树的奇数层
 * @author Sean Yu
 */
public class No6182 {
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
 * 利用层序遍历 + 改变节点值（而不改变节点引用）来实现
 * 当前层是偶数层的时候（从第0层开始），说明下一层是奇数层，先将奇数层的节点依次入队列，然后用左右2个指针向中间移动，交换左右节点的值
 * 当前层是奇数层的时候，此时节点值已在上一层进行过交换，而下一层是偶数层，不用改变，所以正常bfs即可
 */
class Solution6182 {
    public TreeNode reverseOddLevels(TreeNode root) {
        LinkedList<TreeNode> q = new LinkedList();
        q.add(root);
        boolean even = true;
        while (!q.isEmpty()) {
            int cnt = q.size();
            while (cnt > 0) {
                TreeNode cur = q.poll();
                if (cur.left == null) {
                    return root;
                }
                q.add(cur.left);
                q.add(cur.right);
                cnt--;
            }
            if (even) {
                swap(q);
            }
            even = !even;
        }
        return root;
    }

    private void swap(LinkedList<TreeNode> list) {
        int l = 0, r = list.size() - 1;
        while (l < r) {
            int tmp = list.get(l).val;
            list.get(l).val = list.get(r).val;
            list.get(r).val = tmp;
            l++;
            r--;
        }
    }
}