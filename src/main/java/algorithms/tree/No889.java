package algorithms.tree;

import java.util.HashMap;
import java.util.Map;

/**
 * No.889. 根据前序和后序遍历构造二叉树
 * @author Sean Yu
 */
public class No889 {
    public static void main(String[] args) {
        int[] pre = new int[]{2,1};
        int[] po = new int[]{1,2};
        TreeNode root = new Solution889().constructFromPrePost(pre,po);
        System.out.println(root);
    }
}

/**
 * 递归法：
 */
class Solution889 {
    public TreeNode constructFromPrePost(int[] preorder, int[] postorder) {
        return f(preorder, 0, preorder.length - 1, postorder, 0, postorder.length - 1);
    }

    private TreeNode f(int[] preorder, int preL, int preR, int[] postorder, int poL, int poR) {
        if (preL > preR) { //重点, 越界情况不要忽略
            return null;
        }
        TreeNode root = new TreeNode(preorder[preL]);
        if (preL == preR) {
            return root;// 如果只有1个节点 则返回
        }
        int find = preorder[preL + 1];
        int count = 0;
        while (postorder[poL + count] != find) {
            count++;
        }
        root.left = f(preorder, preL + 1, preL + count + 1, postorder, poL, poL + count);
        root.right = f(preorder, preL + count + 2, preR, postorder, poL + count + 1, poR - 1);
        return root;
    }
}


/**
 * 优化版：
 * 提前记录postorder每个值的下标，牺牲空间换时间
 */
class Solution889_II {
    public TreeNode constructFromPrePost(int[] preorder, int[] postorder) {
        Map<Integer, Integer> indexMap = new HashMap();
        for (int i = 0; i < postorder.length; i++) {
            indexMap.put(postorder[i], i);
        }
        return f(indexMap, preorder, 0, preorder.length - 1, postorder, 0, postorder.length - 1);
    }

    private TreeNode f(Map<Integer, Integer> indexMap, int[] preorder, int preL, int preR, int[] postorder, int poL, int poR) {
        if (preL > preR) {
            return null;
        }
        TreeNode root = new TreeNode(preorder[preL]);
        if (preL == preR) {
            return root;// 如果只有1个节点 则返回
        }
        int find = preorder[preL + 1];
        int count = indexMap.get(find) - poL;
        root.left = f(indexMap, preorder, preL + 1, preL + count + 1, postorder, poL, poL + count);
        root.right = f(indexMap, preorder, preL + count + 2, preR, postorder, poL + count + 1, poR - 1);
        return root;
    }
}