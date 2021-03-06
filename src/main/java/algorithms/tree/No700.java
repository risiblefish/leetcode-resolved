package algorithms.tree;

/**
 * 700. 二叉搜索树中的搜索
 *
 * @author Sean Yu
 */
public class No700 {
    public static void main(String[] args) {
        System.out.println('a' > 'b');
    }
}

/**
 * 思路：
 * BST的性质：中序遍历为升序
 * 所以对每个节点，可以和val做比较，如果val比该节点小，则遍历左子树，否则遍历右子树
 */
class Solution700 {
    public TreeNode searchBST(TreeNode root, int val) {
        if (root == null) {
            return null;
        }
        if (root.val == val) {
            return root;
        }
        if (val < root.val) {
            return searchBST(root.left, val);
        } else {
            return searchBST(root.right, val);
        }
    }
}


