package contest;

import algorithms.tree.TreeNode;

import java.util.*;

/**
 * @author Sean Yu
 */
public class No6235 {
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
class Solution {
    public int minimumOperations(TreeNode root) {
        List<List<Integer>> res = traversal(root);
        int ans = 0;
        for (List<Integer> list : res) {
            ans += count(list);
        }
        return ans;
    }

    private int count(List<Integer> list) {
        PriorityQueue<Integer> q = new PriorityQueue();
        for (Integer i : list) {
            q.add(i);
        }
        int cnt = 0;
        int last = list.size() - 1;
        int i = 0;
        while (list.get(i) != q.poll()) {
            swap(list, i, last--);
            cnt++;
        }
        return cnt;
    }

    private void swap(List<Integer> list, int i, int j) {
        int temp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, temp);
    }

    private List<List<Integer>> traversal(TreeNode root) {
        List<List<Integer>> ans = new ArrayList();
        Queue<TreeNode> q = new LinkedList();
        q.add(root);
        while (!q.isEmpty()) {
            int cnt = q.size();
            List<Integer> list = new ArrayList();
            while (cnt > 0) {
                cnt--;
                TreeNode cur = q.poll();
                list.add(cur.val);
                if (cur.left != null) {
                    q.add(cur.left);
                }
                if (cur.right != null) {
                    q.add(cur.right);
                }
            }
            ans.add(list);
        }
        return ans;
    }
}
