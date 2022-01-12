package algorithms.tree;

/**
 * 1644. 二叉树的最近公共祖先 II
 * @author Sean Yu
 * @date 7/12/2021 下午4:55
 */
public class No1644 {
}

/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class Solution1644 {
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        return process(root, p, q).ans;
    }

    private Info process(TreeNode root, TreeNode p, TreeNode q){
        if(root == null){
            return new Info(false, false, null);
        }
        Info leftInfo = process(root.left, p, q);
        Info rightInfo = process(root.right, p, q);
        boolean findp = leftInfo.findp || rightInfo.findp || root == p;
        boolean findq = leftInfo.findq || rightInfo.findq || root == q;
        TreeNode ans = null;
        if(leftInfo.findp && leftInfo.findq) {
            ans = leftInfo.ans;
        }
        else if(rightInfo.findp && rightInfo.findq){
            ans = rightInfo.ans;
        }
        else {
            if(findp && findq){
                ans = root;
            }
        }
        return new Info(findp, findq, ans);
    }


    class Info{
        boolean findp;
        boolean findq;
        TreeNode ans;
        public Info(boolean fp, boolean fq, TreeNode a){
            findp = fp;
            findq = fq;
            ans = a;
        }
    }
}