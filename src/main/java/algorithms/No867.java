package algorithms;

/**
 * @author Sean Yu
 */
public class No867 {
}

/**
 * 思路：
 * 选择遍历对角线的上部or下部，进行逐一替换
 */
class Solution867 {
    public int[][] transpose(int[][] matrix) {
        if(matrix == null || matrix.length == 0){
            return matrix;
        }
        int rowLen = matrix.length;
        int colLen = matrix[0].length;
        for(int i = 0 ; i < rowLen ; i++){
            for (int j = 0 ; j < colLen ; j++){
                if(j < i){
                    swap(matrix,i,j);
                }
            }
        }
        return matrix;
    }

    private void swap(int[][] matrix, int i , int j){
        int temp = matrix[i][j];
        matrix[i][j] = matrix[j][i];
        matrix[j][i] = temp;
    }
}
