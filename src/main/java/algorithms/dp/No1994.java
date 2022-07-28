package algorithms.dp;

import java.io.IOException;

/**
 * 1994. 好子集的数目
 * @author Sean Yu
 * @create 2022/7/22 17:14
 */
public class No1994 {
    public static void main(String[] args) throws IOException {
//        int[] nums = new int[]{18, 28, 2, 17, 29, 30, 15, 9, 12};
        int[] nums = new int[]{1,2,3,4};
        System.out.println(new Solution1994().numberOfGoodSubsets(nums));
    }
}

/**
 * 思路 ：
 * 每个子集，它可以表示成若干个质数的乘积，比如（3，4），其乘积为12 = 2 * 2 * 3， 也就是2个质数2，1个质数3
 * 其中，成为好子集的条件是，子集里至少包含1个质数，且每个质数只能出现至多1次
 * 题目保证nums[i]的范围在[1,30]，30以内的质数总共有10个，所以我们可以用一个二进制的低10位，来表示一个好子集的质数分布情况
 * e.g.
 * 30以内的质数:
 * [29,23,19,17,13,11,7,5,3,2]
 * 如 二进制 0100000001 ->  表示29出现0次，23出现1次，19出现0次，... ，3出现0次，2出现1次 的好子集，
 * 那么，这10个质数，总共有 二进制 0000000001 ~ 1111111111 这些质数分布情况，所以可以开辟 1 << 10位 的数组空间来存储每种好子集出现次数
 * 令 state[i] 表示 质数分布为十进制i的好子集的数量，比如0000001001 十进制为9, status[9] 表示 质数分布只包含 7和2 的好子集的个数
 * status[0]表示空集，根据题意，空集不能算好子集，我们可以把它赋予一个有意义的值 -> 考虑了1的可能性数量
 * 由于不同位置的1，会被算作不同的1，
 * 如果数组中有 2, 1_a, 3,  1_b, 7,  1_c , ... (这里加上字母来区分不同的1)，
 * 其中遍历到了1_a时，可以选则将1_a加入子集，或不加入，即2种选择，遍历到1_b时，也是2种选择，... 每遍历到1，就有2种选择，所以数组中如果有n个1，就是2的n次方种选择
 * 我们把考虑到加入1的可能性数量存入status[0]
 * 当我们尝试将一个数k加入到质数分布在 0000000000 ~ 1111111111 的所有好子集里去的时候，这个数能被加入的条件是：
 * 1. k本身是一个好数（不含平方因子）
 * 2. 要加入k的好子集，其质数分布i,不能包含k所包含的质数因子，即需满足 i & （k的质数分布） == 0
 * 由于k的范围在[1,30]，我们可以直接枚举1~30的质数分布，这里用bitMap[k]来表示，其中k的因子里重复出现质数，即不是好数，直接令其为-1
 *
 * 3. 如果k可以被加入质数分布为i的好子集，假设数组里k共出现cnt次，且质数分布为i的好子集有status[i]个
 * 那么k加入之后，会产生新的好子集, 其质数分布为 bitMap[k]|i ，新增的数量，则为 cnt * status[i]个
 *
 * 4. 我们可以先统计数组中每个数出现的次数，在统计完成后，再从2-30进行遍历，将存在数组中的数，尝试和所有好子集进行合并，然后对合并产生的新的好子集进行增值
 * 5. 最后统计 status[1] ~ status[1<<10 - 1] 的和，就是所求的解
 */
class Solution1994 {
    //30以内的质数: [29,23,19,17,13,11,7,5,3,2]
    int[] bitMap = new int[]{
            //0 - 占位
            -1,
            //1 - 非好数
            -1,
            //2 - 0000000001
            1,
            //3 - 0000000010
            2,
            //4 - 非好数
            -1,
            //5 - 0000000100
            4,
            //6 - 0000000011
            3,
            //7 - 0000001000
            8,
            //8 - 非好数
            -1,
            //9 - 非好数
            -1,
            //10 - 0000000101
            5,
            //11 - 0000010000
            16,
            //12 - 非好数
            -1,
            //13 - 0000100000
            32,
            //14 - 0000001001
            9,
            //15 - 0000000110
            6,
            //16 - 非好数
            -1,
            //17 - 0001000000
            64,
            //18 - 非好数
            -1,
            //19 - 0010000000
            128,
            //20 - 非好数
            -1,
            //21 - 0000001010
            10,
            //22 - 0000010001
            17,
            //23 - 0100000000
            256,
            //24 - 非好数
            -1,
            //25 - 非好数
            -1,
            //26 - 000010001
            33,
            //27 - 非好数
            -1,
            //28 - 非好数
            -1,
            //29 - 1000000000
            512,
            //30 - 0000000111
            7
    };
    int[] cnt = new int[31];
    int MOD = (int) 1e9 + 7;
    int statusLen = 1 << 10;
    int[] status = new int[statusLen];

    public int numberOfGoodSubsets(int[] nums) {
        for (int num : nums) {
            cnt[num]++;
        }
        status[0] = 1;
        for (int i = 0; i < cnt[1]; i++) {
            status[0] = status[0] * 2 % MOD;
        }

        for(int k = 2 ; k <= 30 ; k++){
            //只有当k出现在原数组中，且k是一个好数时，才有可能使得好子集数量增加
            if(cnt[k] > 0 && bitMap[k] != -1){
                for(int i = 0 ; i < statusLen ; i++){
                    if((bitMap[k] & i) == 0){
                        //比如k的质数分布是001, 好子集i的质数分布是100，它们合并之后，变成101, 需要累加到质数分布为101的好子集中去，这里to就是合并后的好子集的质数分布
                        int to = bitMap[k] | i;
                        long sum = (long)status[i] * cnt[k] + status[to];
//                        status[to] = (int)((status[to] + ((long)status[i] * cnt[k])) % MOD);
                        status[to] = (int)(sum % MOD);
                    }
                }
            }
        }

        int ans = 0;
        for(int i = 1 ; i < statusLen ; i++){
            ans += status[i];
        }
        return ans;
    }
}