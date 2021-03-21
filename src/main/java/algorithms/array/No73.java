package algorithms.array;

import java.util.HashSet;
import java.util.Set;

/**
 * No. 73 矩阵置零
 * @Author: Sean Yu
 * @Date: 2021/3/21 12:54
 */
public class No73 {
}

/**
 * 空间复杂度 m + n 解法
 * a[i][j] = 0 ， 那么有a[i][ALL] = 0, a[ALL][j] = 0
 * 所以，在第一次遍历中，记录所有的i,j， 由于i至多为m，j至多为n，所以空间复杂度为O（m+n）
 */
class Solution73 {
    public void setZeroes(int[][] matrix) {
        Set<Integer> rowSet = new HashSet();
        Set<Integer> colSet = new HashSet();

        for(int row = 0 ; row < matrix.length; row++){
            for(int col = 0 ; col < matrix[0].length; col++){
                if(matrix[row][col] == 0){
                    rowSet.add(row);
                    colSet.add(col);
                }
            }
        }

        for(int row = 0 ; row < matrix.length; row++){
            for(int col = 0 ; col < matrix[0].length; col++){
                if(rowSet.contains(row) || colSet.contains(col)){
                    matrix[row][col] = 0;
                }
            }
        }
    }
}
