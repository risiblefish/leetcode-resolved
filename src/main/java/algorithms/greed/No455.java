package algorithms.greed;

import java.util.Arrays;

/**
 * 455. 分发饼干
 * @author Sean Yu
 * @date 2020/12/25 9:51
 */
public class No455 {
}

/**
 * 思路： 贪心
 *
 * 用满足孩子胃口的最小的那个饼干去喂食
 */
class Solution {
    public int findContentChildren(int[] g, int[] s) {
        Arrays.sort(g);
        Arrays.sort(s);
        int count = 0;
        int k = 0;
        boolean find;
        for(int i = 0 ; i < g.length; i++){
            find = false;
            //遍历饼干
            while(k < s.length){
                //如果当前饼干能够满足当前孩子胃口
                if(s[k] - g[i] >= 0){
                    count++;
                    k++;
                    find = true;
                    break;
                }
                //否则，继续看下一块饼干是否满足当前孩子胃口
                else {
                    k++;
                }
            }
            //如果当前的所有饼干都不能满足当前孩子，那么也不能满足后面的孩子，直接退出
            if(!find){
                break;
            }
        }
        return count;
    }
}
