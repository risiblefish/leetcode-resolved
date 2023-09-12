package contest;

import algorithms.tree.TreeNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

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
class Solution6242 {
    public List<List<Integer>> closestNodes(TreeNode root, List<Integer> queries) {
        List<List<Integer>> ans = new ArrayList();
        for(int q : queries){
            ans.add(Arrays.asList(findL(q, root), findR(q, root)));
        }
        
        return ans;
    }
    
    private int findL(int q, TreeNode root){
        if(root == null) return -1;
        if(root.val <= q) {
            return Math.max(root.val, findL(q, root.right));
        }
        //root.val > q
        else{
            return findL(q, root.left);
        }
    }
    
    private int findR(int q, TreeNode root){
        if(root == null) return -1;
        if(root.val >= q){
            int ans = root.val;
            int r = findR(q, root.left);
            return r >= 0 ? Math.min(ans, r) : ans;
        }
        else {
            return findR(q, root.right);
        }
    }

    public static void main(String[] args) {
        Random rand = new Random();
        int[] arr = {2, 3, 2, 1, 4, 4};
        int eng = 0;
        for (int i = 1; i <= 15; i++) {
            for (int j = 0; j < arr.length; j++) {
                if(j != 1 && arr[j] == 1){
                    eng += rand.nextInt(2) == 0 ? 20 : 30;
                }
                if(j == 1){
                    if(eng >= 100){
                        System.out.println(String.format("暗战在第%s回合开大",i));
                        eng = 0;
                    }else {
                        eng += 80;
                    }
                }
            }
            eng += rand.nextInt(10) == 0 ? 0 : 10;
            eng += 50;
            eng -=100;
            eng = Math.max(eng, 0);
        }
        double n = 100;
        int cnt = 0;
        while(n > 10){
            cnt++;
            n *= 0.95;
        }
        System.out.println(cnt);
    }
}