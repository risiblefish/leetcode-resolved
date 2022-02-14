package algorithms.stackandqueue;

import java.util.Arrays;

/**
 * 1996. 游戏中弱角色的数量
 * @author Sean Yu
 * @date 28/1/2022 下午6:52
 */
public class No1996 {
    public static void main(String[] args) {
        System.out.println(new Solution1996().numberOfWeakCharacters(new int[][]{{1, 5}, {10, 4}, {4, 3}}));
    }
}

class Solution1996 {
    public int numberOfWeakCharacters(int[][] properties) {
        Arrays.sort(properties, (arr1, arr2) -> {
            int res = arr1[0] - arr2[0];
            //如果攻击力相同，就按防御力降序排序，避免攻击力相同，防御力不同 情况的干扰
            if( res == 0){
                return arr2[1] - arr1[1];
            }
            return res;
        });

        int cnt = 0, max = 0;
        int r = properties.length - 1;
        while(r >= 0){
            int[] curr = properties[r];
            if(curr[1] < max){
                cnt++;
            }
            else {
                max = curr[1];
            }
            r--;
        }
        return cnt;
    }
}