package algorithms.bitoperation;

/**
 * 给定一个整数数组，只有1个数出现k次，其余的数出现了m次，求出现k次的数， 其中 k < m
 * 思路：
 * 每个数都可以用二进制来表示，二进制在每一位上，只能是0或者1
 * 比如数组为[5,5,5,2,2,3,3,3],即k = 2, m = 3， 即5的二进制101,出现了3次，2的二进制10出现了2次，3的二进制11出现了3次
 * 如果我们统计每一位上1出现的个数，则是303 + 020 + 033 -> 326，
 * 假设从左往右看，第1位出现了3个1，第2位出现2个1，第3位出现6个1
 * 假设第a位上出现了b个1，如果b是m的倍数，说明出现k次的数在这位上为0，反之，如果b不是m的倍数，说明出现k次的数在这位上为1
 *
 * @author Sean Yu
 */
public class KandM {
    public static void main(String[] args) {
        int[] arr = new int[]{2,2,2,1,1,3,3,3};
        System.out.println(find(arr, 2, 3));
    }

    private static int find(int[] arr, int k, int m){
        int[] help = new int[32];
        for (int num : arr) {
            for(int i = 0 ; i < 32 ; i++){
                //统计每位上1出现的次数
                help[i] += (num >> i) & 1;
            }
        }
        int ans = 0;
        for(int i = 0 ; i < 32; i++){
            //如果这位上1不是m的倍数，说明出现k次的数在这位上包含1
            if(help[i] % m != 0){
                ans |= 1 << i;
            }
        }
        return ans;
    }
}
