package algorithms.tree;

/**
 * 124. 二叉树中的最大路径和
 * @author Sean Yu
 */
public class No124 {
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
 *  思路： 树形DP
 *
 *  对于给定root的最大和路径，有5种情况，可以分成两类：和root无关，和root有关
 *  （1）最大和路径在root的左子树里（和root无关，因为不经过root）
 *  （2）最大和路径在root的右子树里（和root无关）
 *  （3）最大和路径在从root出发，到root的左子树结束 的某条路径（和root有关，因为经过root）
 *  （4）最大和路径在从root出发，到root的右子树结束 的某条路径（和root有关，因为经过root）
 *  （5）最大和路径在 从root的左子树出发，经过root，到root的右子树结束 的某条路径（和root有关，因为经过root）
 *
 *  对于root而言，最大和路径就是上述5种情况里，和最大的那条路径
 *  对于root而言，要得到上述5种值，需要分别从左右子树里获得一些信息：
 *  求（1），需要知道root左子树里的最大和
 *  求（2），需要知道root右子树里的最大和
 *  求（3），如果能知道从root.left出发，到root的左子树的最大和b，那么从root出发，到root左子树的最大和a，那么有 a = b > 0 ? a+b : a;
 *  求（4）, 同理（3）, 需要知道从root.right出发，到root的右子树的最大和
 *  求（5），同理（3），需要知道从root.left和从root.right出发，分别到左右子树的最大和
 *
 *  将信息提取一下, 分成3个属性：
 *  1. max(以当前节点为头节点的最大路径和，不一定要经过当前节点)
 *  2. selfLeftMax(以当前节点出发，到它的左子树的最大路径和)
 *  3. selfRightMax(以当前节点出发，到它的右子树的最大路径和)
 *
 *  我们将这些需要即时知道的信息，封装到一个自定义的类Info里，然后进行递归
 */
class Solution124 {
    public int maxPathSum(TreeNode root) {
        if(root == null){
            return 0;
        }
        return f(root).max;
    }

    private Info f(TreeNode root){
        if(root == null) {
            return null;
        }
        //初始化3个属性值为合理的值
        int max = root.val;
        int selfLeftMax = root.val;
        int selfRightMax = root.val;

        //求左右子树的信息
        Info leftInfo = f(root.left);
        Info rightInfo = f(root.right);

        //如果左子树信息不为空，需要更新root的2个属性：selfLeftMax 和 max
        if(leftInfo != null){
            //从左孩子出发的最大值
            int m = Math.max(leftInfo.selfLeftMax, leftInfo.selfRightMax);
            //如果从左孩子出发的最大值 > 0，则加上，否则就是root本身的值
            if(m > 0) {
                selfLeftMax += m;
            }
            //将max和左子树的max进行比较更新
            max = Math.max(max, leftInfo.max);
        }

        //如果右子树信息不为空，需要更新root的2个属性：selfRightMax 和 max
        if(rightInfo != null){
            //从右孩子出发的最大值
            int m = Math.max(rightInfo.selfLeftMax, rightInfo.selfRightMax);
            if(m > 0){
                selfRightMax += m;
            }
            //将max和右子树的max进行比较更新
            max = Math.max(max, rightInfo.max);
        }

        //判断情况（5）
        int sum = root.val;
        if(selfLeftMax > 0){
            sum = sum + selfLeftMax - root.val; //因为selfLeftMax此前已包含root.val，所以需要减掉
        }

        if(selfRightMax > 0){
            sum = sum + selfRightMax - root.val;
        }
        //更新root的max
        max = Math.max(max, sum);
        return new Info(max, selfLeftMax, selfRightMax);
    }

    /**
     * 还有一种做法是，将selfLeftMax和selfRightMax改为从左孩子和右孩子出发的最大路径和
     */
    class Info{
        int max;
        int selfLeftMax;
        int selfRightMax;
        public Info(int m , int l ,int r){
            max = m;
            selfLeftMax = l;
            selfRightMax = r;
        }
    }
}
