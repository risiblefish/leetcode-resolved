package algorithms.tree;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * 513. 找树左下角的值
 *
 * @author Sean Yu
 */
public class No513 {
    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        TreeNode t2 = root.left = new TreeNode(2);
        TreeNode t3 = root.right = new TreeNode(3);
        TreeNode t4 = t2.left = new TreeNode(4);
        TreeNode t5 = t3.left = new TreeNode(5);
        TreeNode t6 = t3.right = new TreeNode(6);
        TreeNode t7 = t5.left = new TreeNode(7);
        int v = new Solution513().findBottomLeftValue(root);
        System.out.println(v);
    }
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
 * 题目要求的是，找到【最底层】且【最左边】的节点的值
 * <p>
 * 所以，首先要考虑的是最底层（深度最大），其次才考虑左右的问题
 * 对此，可以通过传入深度来做记录
 * 至于左右的问题，可以通过遍历的顺序来规避，只更新【第一次】找到的深度最大的节点值
 * 无论是 前序，中序，后序，都是先遍历左子树，（因为只要有子树，那么根的值可以忽略，即左右）
 *
 * 复杂度分析：
 *
 *
 */
class Solution513 {
    public int findBottomLeftValue(TreeNode root) {
        if (root.left == null && root.right == null) {
            return root.val;
        }
        Deque<Res> q = new ArrayDeque();
        q.push(new Res(1, root.val));
        if (root.left != null) {
            find(root.left, 1, q);
        }
        if (root.right != null) {
            find(root.right, 1, q);
        }
        return q.pop().val;
    }

    private void find(TreeNode curr, int lastDepth, Deque<Res> q) {
        if (curr.left == null && curr.right == null) {
            Res lastRes = q.peek();
            //当前节点比res更深，则更新，只有 > 才更新（表示第一次找到，即最左边的节点），=不更新
            if (lastDepth + 1 > lastRes.depth) {
                q.pop();
                q.push(new Res(lastDepth + 1, curr.val));
            }
            return;
        }
        if (curr.left != null) {
            find(curr.left, lastDepth + 1, q);
        }
        if (curr.right != null) {
            find(curr.right, lastDepth + 1, q);
        }
    }

    class Res {
        int depth;
        int val;

        public Res(int d, int v) {
            this.depth = d;
            this.val = v;
        }
    }
}
