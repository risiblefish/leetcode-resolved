package algorithms.tree;

/**
 * @author Sean Yu
 * @date 3/12/2021 上午10:52
 */
public class No1373 {
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
 *  思路： 套用树形DP模板
 */
class Solution1373 {
    public int maxSumBST(TreeNode root) {
        return proceed(root).sum;
    }

    private Info proceed(TreeNode root){
        if(root == null){
            //max min unknown
            return null;
        }
        Info leftInfo = proceed(root.left);
        Info rightInfo = proceed(root.right);

        int sum = 0;
        int selfSum = root.val;
        int max = root.val;
        int min = root.val;
        boolean isBST = false;

        if(leftInfo == null && rightInfo == null){
            isBST = true;
            sum = Math.max(sum, selfSum);
        }
        else {
            if(leftInfo != null && rightInfo == null){
                if(leftInfo.isBST && root.val > leftInfo.max){
                    isBST = true;
                    selfSum += leftInfo.selfSum;
                    sum = Math.max(selfSum, leftInfo.sum);
                    min = leftInfo.min;
                }else {
                    sum = leftInfo.sum;
                }
            }
            else if(leftInfo == null && rightInfo != null){
                if(rightInfo.isBST && root.val < rightInfo.min){
                    isBST = true;
                    selfSum += rightInfo.selfSum;
                    sum = Math.max(selfSum, rightInfo.sum);
                    max = rightInfo.max;
                }else {
                    sum = rightInfo.sum;
                }
            }
            else {
                if(leftInfo.isBST
                        && rightInfo.isBST
                        && root.val > leftInfo.max
                        && root.val < rightInfo.min) {
                    isBST = true;
                    selfSum += leftInfo.selfSum + rightInfo.selfSum;
                    sum = Math.max(leftInfo.sum, rightInfo.sum);
                    sum = Math.max(selfSum, sum);
                    min = leftInfo.min;
                    max = rightInfo.max;
                }else{
                    sum= Math.max(leftInfo.sum, rightInfo.sum);
                }
            }
        }
        return new Info(sum, selfSum, max, min, isBST);
    }

    class Info{
        int sum;
        int selfSum; //以当前节点为头（即包含当前节点）的二叉搜索树的全部子节点的和
        int max;
        int min;
        boolean isBST;
        public Info(int su, int se, int ma, int mi, boolean flag){
            sum = su;
            selfSum = se;
            max = ma;
            min = mi;
            isBST = flag;
        }
    }
}
