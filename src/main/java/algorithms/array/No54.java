package algorithms.array;

import java.util.ArrayList;
import java.util.List;

/**
 * 54. 螺旋矩阵
 * @author Sean Yu
 * @since 2024/6/1 20:12
 */

public class No54 {
}

class Solution54 {
    boolean[][] visited;

    public List<Integer> spiralOrder(int[][] matrix) {
        int rowLen = matrix.length;
        int colLen = matrix[0].length;
        visited = new boolean[rowLen][colLen];
        int cnt = rowLen * colLen;
        int row = 0, col = 0;
        //dir 0:l->r 1:up->down 2:r->l 3:down -> up
        int dir = 0;
        List<Integer> ans = new ArrayList();
        while (cnt > 0) {
            //由于cnt约束保证有效性，cnt在变为0之前，一定有没访问到的位置，所以但凡能进入while循环，m[row][col]一定是个合法的位置
            ans.add(matrix[row][col]);
            cnt--;
            visited[row][col] = true;
            //后面的所有if，目标都是找到并移动到下一个合法的位置
            if (dir == 0) {
                if (col + 1 < colLen && !visited[row][col + 1]) {
                    col++;
                } else {
                    dir = 1;
                    row++;
                }
            } else if (dir == 1) {
                if (row + 1 < rowLen && !visited[row + 1][col]) {
                    row++;
                } else {
                    dir = 2;
                    col--;
                }
            } else if (dir == 2) {
                if (col - 1 >= 0 && !visited[row][col - 1]) {
                    col--;
                } else {
                    dir = 3;
                    row--;
                }
            }
            //now dir is 3
            else {
                if (row - 1 >= 0 && !visited[row - 1][col]) {
                    row--;
                } else {
                    dir = 0;
                    col++;
                }
            }
        }
        return ans;
    }
}
