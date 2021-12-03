package algorithms.tree;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;

/**
 * @author Sean Yu
 */
public class No101 {
    public static void main(String[] args) {
//        TreeNode root = new TreeNode(1);
//        TreeNode sl = new TreeNode(2);
//        TreeNode sr = new TreeNode(2);
//        root.left = sl;
//        root.right = sr;
//
//        TreeNode sll = new TreeNode(3);
//        TreeNode slr = new TreeNode(4);
//        TreeNode srl = new TreeNode(4);
//        TreeNode srr = new TreeNode(3);
//
//        sl.left = sll;
//        sl.right = slr;
//
//        sr.left = srl;
//        sr.right = srr;
//
//        boolean flag = new Solution101_II().isSymmetric(root);
//        System.out.println(flag);
        LinkedList<Object> list = new LinkedList<>();
        list.add(null);
        System.out.println(list.size());
    }

}


/**
 * 思路1: 递归
 */
class Solution101_I {
    public boolean isSymmetric(TreeNode root) {
        if(root == null){
            return true;
        }
        return isSymmetric(root.left, root.right);
    }

    private boolean isSymmetric(TreeNode left, TreeNode right){
        if(left == null && right == null) {
            return true;
        }
        if(left == null && right != null){
            return false;
        }
        if(left != null && right == null){
            return false;
        }
        else {
            if(left.val != right.val){
                return false;
            }
            return isSymmetric(left.left,right.right) && isSymmetric(left.right,right.left);
        }
    }
}

/**
 * 思路2： 使用队列
 */
class Solution101_II {
    public boolean isSymmetric(TreeNode root) {
        if(root == null) return true;
        Deque<TreeNode> q = new LinkedList();//这里把Linkedlist换成ArrayDeque会报错，思考为什么？ ---Deque的add不允许添加null，而List则可以
        q.add(root.left);
        q.add(root.right);
        while(!q.isEmpty()){
            TreeNode l = q.poll();
            TreeNode r = q.poll();
            if(l == null && r == null) continue;
            if(l == null || r == null) return false;
            if(l.val != r.val) return false;
            q.add(l.left);
            q.add(r.right);
            q.add(l.right);
            q.add(r.left);
        }
        return true;
    }
}
