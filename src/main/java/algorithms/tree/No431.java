package algorithms.tree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * 431. 将 N 叉树编码为二叉树
 * @author Sean Yu
 */
public class No431 {
    public static void main(String[] args) {

    }
}


/*
// Definition for a Node.
class Node {
    public int val;
    public List<Node> children;

    public Node() {}

    public Node(int _val) {
        val = _val;
    }

    public Node(int _val, List<Node> _children) {
        val = _val;
        children = _children;
    }
};
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

/**
 * 思路：
 * 本题的难点，在于如何找到一个序列化与反序列化的规则
 * 具体规则：
 * (1)序列化（n叉树转为2叉树）：
 * 将n叉树的头节点，仍作为头节点，将该节点的所有孩子，做成头节点的左子树的右孩子
 * 比如：
 *       a
 *     / | \
 *    b  c  d
 *
 *    序列化后的二叉树形式为：
 *       a
 *      /
 *     b
 *      \
 *       c
 *        \
 *         d
 * (2)反序列化（2叉树转为n叉树）
 * 如果一个二叉树有左节点，那么这个左节点的右子树上的节点，都是它的兄弟节点
 */
class Codec431 {
    // Encodes an n-ary tree to a binary tree.
    public TreeNode encode(Node root) {
        if(root == null){
            return null;
        }
        TreeNode head = new TreeNode(root.val);
        head.left = enDfs(root.children);
        return head;
    }

    /**
     * 通过dfs将n叉树的孩子节点进行转换
     * @param children
     * @return
     */
    private TreeNode enDfs(List<Node> children){
        TreeNode head = null;
        TreeNode curr = null;
        for(Node child : children){
            //对每个孩子节点，它都能转化成一个二叉树中的节点，所以第1件事就是对每个Node都新建一个与之对应的TreeNode
            TreeNode t = new TreeNode(child.val);
            //如果head为空，即左子树的第一个节点为空，则将当前节点设为头结点
            if(head == null){
                head = t;
                curr = t;
            }
            //否则，将t设置为当前节点的右节, 并将当前节点移向它的右孩子
            else {
                curr.right = t;
                curr = curr.right;
            }
            //此时，如果当前节点有左节点，那么需要优先构造当前节点的左子树
            curr.left = enDfs(child.children);
        }
        return head;
    }

    // Decodes your binary tree to an n-ary tree.
    public Node decode(TreeNode root) {
        if(root == null){
            return null;
        }
        //n叉树的孩子，对应的是二叉树中左节点的右子树
        Node head = new Node(root.val, deDfs(root.left));
        return head;
    }

    /**
     * 通过dfs将二叉树转换为n叉树
     * @param root
     * @return
     */
    private List<Node> deDfs(TreeNode root){
        List<Node> children = new ArrayList();
        //通过不停遍历右节点来获取父节点的所有孩子
        while(root != null){
            //每一个n叉树的节点，它的孩子节点，则是由对应二叉树节点的左子树构成
            Node child = new Node(root.val, deDfs(root.left));
            //每遍历一个节点，都加入children集合
            children.add(child);
            //然后将当前二叉树的节点，移向下一个右孩子
            root = root.right;
        }
        return children;
    }
}

// Your Codec object will be instantiated and called as such:
// Codec codec = new Codec();
// codec.decode(codec.encode(root));
