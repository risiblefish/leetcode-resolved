package algorithms.tree.JzNo37;

/**
 * 剑指 Offer 37. 序列化二叉树
 * @author Sean Yu
 */

import algorithms.tree.TreeNode;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Definition for a binary tree node.
 * public class TreeNode {
 * int val;
 * TreeNode left;
 * TreeNode right;
 * TreeNode(int x) { val = x; }
 * }
 */

/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
public class Codec {
    int i = 0;

    // Encodes a tree to a single string.
    public String serialize(TreeNode root) {
        Queue<String> q = new LinkedList();
        se(root, q);
        StringBuilder sb = new StringBuilder();
        while (!q.isEmpty()) {
            sb.append(q.poll());
            sb.append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    /**
     * 序列化
     * 思路：递归
     * （1）先确定递归入参： 传入跟节点，以及保存节点的队列q，序列化需要遍历树的所有节点，所以返回值设为void，将序列化结果保存在q里
     * （2）再确定递归出口条件： 如果当前节点是空，则加入null
     * （3）最后确定递归函数体：遍历顺序随意，这里选择了前序遍历
     * @param root
     * @param q
     */
    private void se(TreeNode root, Queue<String> q) {
        if (root == null) {
            q.add(null);
        } else {
            q.add(root.val + "");
            se(root.left, q);
            se(root.right, q);
        }
    }

    /**
     * 反序列化
     * @param data
     * @return
     */
    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        String[] arr = data.split(",");
        return de(arr);
    }

    /**
     * 思路：
     * 递归
     * @param arr
     * @return
     */
    private TreeNode de(String[] arr) {
        if (i >= arr.length) {
            return null;
        }
        String s = arr[i++];
        if ("null".equals(s)) {
            return null;
        } else {
            TreeNode curr = new TreeNode(Integer.parseInt(s));
            curr.left = de(arr);
            curr.right = de(arr);
            return curr;
        }
    }
}

// Your Codec object will be instantiated and called as such:
// Codec codec = new Codec();
// codec.deserialize(codec.serialize(root));
