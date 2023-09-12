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

/**
 * 思路2： 利用二叉树递归模板求解
 *
 * 在拿到一个节点root的时候,判断以root为根的二叉树，是否是BST，需要满足一些条件，
 * 根据BST的性质，有
 * （1）root左子树的最大值，必须 <= root.val
 * （2）root右子树的最小值，必须 >= root.val
 * （3）root的左子树必须是BST
 * （4）root的右子树必须是BST
 *
 * 如果我们拿到root的时候，能拿到以上信息，那么root是否是BST就能判断出来了，
 * 所以我们把信息提取整理一下，得到3个属性
 * 1. isBST 当前树是否是BST
 * 2.maxVal 当前树的节点最大值
 * 3.minVal 当前树的节点最小值
 * 把它们封装在一个结构体Info里，然后就能进行递归了
 *
 * 在递归的时候，
 * 先把出口条件确定
 * 把递归需要的信息初始化成合理的值
 * 根据各种case来更新信息
 */
class Solution98_II {
    public boolean isValidBST(TreeNode root) {
        if(root == null){
            return true;
        }
        return process(root).isBST;
    }

    private Info process(TreeNode root){
        //确定出口条件，root为null时不确定min和max怎么填，就返回null，交给上层处理
        if(root == null){
            return null;
        }
        //获得左右子树的信息
        Info leftInfo = process(root.left);
        Info rightInfo = process(root.right);

        //将递归需要的信息先初始化成合理的值
        boolean isBST = true;
        int maxVal = root.val;
        int minVal = root.val;

        //根据各种case来更新这些信息
        if(leftInfo != null){
            maxVal = Math.max(maxVal,leftInfo.maxVal);
            minVal = Math.min(minVal,leftInfo.minVal);
            if(leftInfo.maxVal >= root.val || !leftInfo.isBST){
                isBST = false;
            }
        }
        if(rightInfo != null){
            maxVal = Math.max(maxVal, rightInfo.maxVal);
            minVal = Math.min(minVal, rightInfo.minVal);
            if(rightInfo.minVal <= root.val || !rightInfo.isBST){
                isBST = false;
            }
        }
        //将更新后的信息封装后返回
        return new Info(isBST, maxVal, minVal);
    }

    class Info{
        boolean isBST;
        int maxVal;
        int minVal;
        public Info(boolean isBST, int maxVal, int minVal){
            this.isBST = isBST;
            this.maxVal = maxVal;
            this.minVal = minVal;
        }
    }
}
