package algorithms.bitoperation;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Sean Yu
 * @date 8/1/2022 下午7:30
 */
public class No89 {
    public static void main(String[] args) {
        System.out.println(new Solution89().grayCode(2));
    }
}


class Solution89 {
    public List<Integer> grayCode(int n) {
        List<Integer> res = new LinkedList();
        int count = 2;
        for(int i = 1 ; i < n ; i++){
            count = count << 1;
        }
        int num = 1;
        for(int i = 0 ; i < count - 1 ; i++){
            res.add(num - 1);
            if(i == count - 2){
                res.add(num-2);
            }
            else num = num << 1;
        }
        return res;
    }
}