package algorithms.tree;

/**
 * 98. 验证二叉搜索树
 * @author Sean Yu
 */
public class No98 {
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
 * 由于BST具有中序遍历结果是升序的性质
 * 思路1： 先将中序遍历的结果存入集合，再判断集合是否是完全升序
 * 思路2： 直接在递归过程中进行判断
 * 坑： BST的性质是root的值要大于【左子树】的值，而不是【左节点】的值，同理，root要大于【右子树】的值，而不是【右节点】的值
 * 比如root.val > root.left.val， 但root.left.left.val > root.val，即左子树不是BST，这样整棵树也就不是BST
 * 所以递归的时候，不能简单地 root.val > root.left.val && root.val < root.right.val 就认为是true
 * 这里可以用一个全局变量pre来记录前一次访问的节点值，通过不停地更新pre，来进行递归
 *
 * 复杂度分析：
 * 最坏的情况下，要遍历整棵树，即n个节点，时间和空间复杂度均是O(N)
 */
class Solution98 {
    private Integer pre = null;
    public boolean isValidBST(TreeNode root) {
        if(root == null){
            return true;
        }
        boolean isLeftBST = isValidBST(root.left);
        if(pre == null || pre < root.val){
            pre = root.val;
        }
        //pre.val >= root.val
        else {
            return false;
        }
        boolean isRightBST = isValidBST(root.right);
        return isLeftBST && isRightBST;
    }
}
