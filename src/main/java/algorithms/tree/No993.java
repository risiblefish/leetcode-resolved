package algorithms.tree;

/**
 * 993. 二叉树的堂兄弟节点
 */
public class No993 {
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
 * 首先理解堂兄弟节点的概念： 深度相同 但 父节点不同
 * 用一个类 来 存储父节点和该节点的深度 方便比较
 */
class Solution993 {
    public boolean isCousins(TreeNode root, int x, int y) {
        TreeNode vh = new TreeNode(0);
        Res resX = find(root, vh, 0, x);
        Res resY = find(root, vh, 0, y);
        int dx = resX.getDepth();
        int dy = resY.getDepth();
        if (dx == -1 || dy == -1) {
            return false;
        }
        return dx == dy && !resX.getParent().equals(resY.getParent());
    }

    private Res find(TreeNode root, TreeNode parent, int depth, int target) {
        if (root == null) {
            return new Res(parent, -1);
        }
        if (root.val == target) {
            return new Res(parent, depth);
        }
        Res l = find(root.left, root, depth + 1, target);
        Res r = find(root.right, root, depth + 1, target);
        return l.getDepth() >= r.getDepth() ? l : r;
    }

    private class Res {
        private TreeNode parent;
        private int depth;

        public Res(TreeNode parent, int depth) {
            this.parent = parent;
            this.depth = depth;
        }

        public TreeNode getParent() {
            return parent;
        }

        public int getDepth() {
            return depth;
        }
    }
}
