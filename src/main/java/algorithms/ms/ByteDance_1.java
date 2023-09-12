package algorithms.ms;

import java.util.Arrays;

/**
 * 给1个去重数组，1个整数limit
 * 求用数组中的数组成的【小于】limit的 最大数
 *
 * @author Sean Yu
 */
public class ByteDance_1 {
    public static void main(String[] args) {
        Solution_ByteDance_1 test = new Solution_ByteDance_1();
        int[] arr = new int[]{1,2,3};
        int limit = 123;
        System.out.println(test.find(arr, limit));
    }
}

class Solution_ByteDance_1{
    int[] arr;
    int limit;
    public int find(int[] arr, int limit){
        this.arr = arr;
        this.limit = limit - 1;
        Arrays.sort(arr);
        int div = 1;
        while(div * 10 < limit){
            div *= 10;
        }
        int ans = f(div);
        if(ans == -1){
            return buildRest(div / 10);
        }
        return ans;
    }

    private int buildRest(int div){
        int ans = 0;
        while(div > 0){
            ans += arr[arr.length - 1] * div;
            div /= 10;
        }
        return ans;
    }

    private int f(int div){
        if(div == 0){
            return limit;
        }
        int cur = limit / div % 10;
        int nearIdx = nearestIndex(cur);
        if(nearIdx == -1){
            return -1;
        }
        if(cur == arr[nearIdx]){
            int ans = f(div / 10);
            if(ans != -1){
                return ans;
            }else if(nearIdx > 0){
                return limit / (div * 10) * div * 10 + arr[nearIdx-1] * div + buildRest(div / 10);
            }
            return -1;
        }
        return limit / (div * 10) * div * 10 + arr[nearIdx] * div + buildRest(div / 10);
    }


    private int nearestIndex(int num){
        int l = 0 , r = arr.length - 1;
        int idx = -1;
        while(l <= r){
            int mid = l + (r - l) / 2;
            if(arr[mid] <= num){
                idx = mid;
                l = mid + 1;
            }
            else {
                r = mid - 1;
            }
        }
        return idx;
    }
}
