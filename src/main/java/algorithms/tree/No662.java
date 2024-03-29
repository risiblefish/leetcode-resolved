package algorithms.tree;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

/**
 * No.662. 二叉树最大宽度
 * @author Sean Yu
 */
public class No662 {
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
 * 思路1 ： 层序遍历
 *
 * 难点主要是 利用层数来表现每个节点的位置， 通过观察可以发现，在一颗完全二叉树中，当前节点n的左右子节点的位置 分别是  n.position * 2 和 n.position * 2 + 1
 */
class Solution662 {
    public int widthOfBinaryTree(TreeNode root) {
        Queue<MyNode> q = new LinkedList();
        q.add(new MyNode(root,0,0));
        int maxWidth = 0, leftPos = 0, currDepth = 0;
        while(!q.isEmpty()){
            MyNode n = q.poll();
            if(n.node != null){
                q.add(new MyNode(n.node.left, n.depth + 1, 2 * n.position));
                q.add(new MyNode(n.node.right, n.depth + 1, 2 * n.position + 1));
                //触发这个条件，当且仅当遍历到下一层的时候，这时这个非空节点必然是最左边的那个点，leftPos记录的是当前层最左边非空节点的位置
                if(currDepth != n.depth){
                    currDepth = n.depth;
                    leftPos = n.position;
                }
                //只有当前节点非空的时候，才去计算和更新宽度
                maxWidth = Math.max(maxWidth, n.position - leftPos + 1);
            }
        }
        return maxWidth;
    }

    class MyNode{
        TreeNode node;
        int depth;
        int position;
        public MyNode(TreeNode node,int d,int p){
            this.node = node;
            this.depth = d;
            this.position = p;
        }
    }
}

/**
 * 思路2 ： 深度优先搜索
 *
 * 这里用了前序遍历， 每个节点，都和该节点同一层最左边的节点进行距离计算，然后最大的举例就是题解
 * 所以需要求出每层最左边节点的position，由于是深度优先遍历，所以每到下一层，第一个被记录的非空节点的position就是该层最左边的位置
 */
class Solution662_II {
    int maxWidth = 0;
    Map<Integer,Integer> map;
    public int widthOfBinaryTree(TreeNode root) {
        map = new HashMap();
        dfs(root, 0,0);
        return maxWidth;
    }

    private void dfs(TreeNode root, int depth, int position){
        if(root == null) {
            return;
        }
        map.putIfAbsent(depth, position);
        maxWidth = Math.max(maxWidth, position - map.get(depth) + 1);
        dfs(root.left, depth + 1, 2 * position);
        dfs(root.right, depth + 1, 2 * position + 1);
    }
}

/**
 * 复杂度分析： 由于2种方式都是每个节点遍历一次，所以时间是O（N），空间也是O（N）
 */
