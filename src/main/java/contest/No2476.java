package contest;

import algorithms.tree.TreeNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Sean Yu
 */
public class No2476 {
}

/**
 * Definition for a binary tree node.
 * public class TreeNode {
 * int val;
 * TreeNode left;
 * TreeNode right;
 * TreeNode() {}
 * TreeNode(int val) { this.val = val; }
 * TreeNode(int val, TreeNode left, TreeNode right) {
 * this.val = val;
 * this.left = left;
 * this.right = right;
 * }
 * }
 */
class Solution2476 {
    public static void main(String[] args) {
        /**
         * [6,2,13,1,4,9,15,null,null,null,null,null,null,14]
         * [2,5,16]
         */
        List<Integer> og = Arrays.asList(1, 2, 4, 6, 9, 13, 14, 15);
        Solution2476 test = new Solution2476();
        test.list = og;
        System.out.println(test.firstMore(16));
    }
    List<Integer> list;
    public List<List<Integer>> closestNodes(TreeNode root, List<Integer> queries) {
        List<List<Integer>> ans = new ArrayList();
        list = traversal(root);
        for (int q : queries) {
            int fm = firstMore(q);
            int left = fm == -1 ? -1 : fm - 1;
            int ll = lastLess(q);
            int right = ll == -1 ? -1 : ll + 1;
            ans.add(Arrays.asList(left, right));
        }
        return ans;
    }

    //找到第一个>target的索引
    int firstMore(int target) {
        int l = 0, r = list.size() - 1;
        while (l <= r) {
            int m = l + (r - l) / 2;
            int cur = list.get(m);
            if (cur <= target) {
                l = m + 1;
            }
            //cur > target
            else {
                if (m == 0 || list.get(m - 1) <= target) {
                    return m;
                } else {
                    r = m - 1;
                }
            }
        }
        return -1;
    }

    //找到最后一个<target的索引
    int lastLess(int target) {
        int l = 0, r = list.size() - 1;
        while (l <= r) {
            int m = l + (r - l) / 2;
            int cur = list.get(m);
            if (cur >= target) {
                r = m - 1;
            }
            //cur < target
            else {
                if (m == list.size() - 1 || list.get(m + 1) >= target) {
                    return m;
                } else {
                    l = m + 1;
                }
            }
        }
        return -1;
    }

    private List<Integer> traversal(TreeNode root) {
        if (root == null) return null;
        List<Integer> list = new ArrayList();
        list.addAll(traversal(root.left));
        list.add(root.val);
        list.addAll(traversal(root.right));
        return list;
    }
}