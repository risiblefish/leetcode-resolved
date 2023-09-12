//package algorithms.math;
//
//import java.util.HashMap;
//import java.util.HashSet;
//
///**
// * 2354. 优质数对的数目
// */
//public class No2354 {
//
//}
//
///**
// * 思路：
// * （1）
// * 根据容斥原理， a并b = a + b - a交b
// * 所以 a并b + a交b = a + b
// * 所以将题目 a并b中 1的个数， + a交b 中1的个数 转换为  a中1的个数 + b中1的个数
// * （2）
// * 集合含重复元素，所以我们要进行去重并统计每个数中包含1的个数，以 key = 1的个数， val = 有多少个数的个数=key 的形式统计
// * （3）
// * 2重循环，遍历去重后的count集合，
// * 如果count_a + count_b >= k， 假设有n1个数中1的位数是count_a, 有n2个数中1的位数是count_b， 则共有n1 * n2个数对
// */
//class Solition2354 {
//    public long countExcellentPairs(int[] nums, int k) {
//        var set = new HashSet<Integer>();
//        var cntMap = new HashMap<Integer, Integer>();
//        for (int i : nums) {
//            if (set.add(i)) {
//                int count = Integer.bitCount(i);
//                cntMap.put(count, cntMap.getOrDefault(count, 0) + 1);
//            }
//        }
//        long ans = 0L;
//        for (var k1 : cntMap.keySet()) {
//            for (var k2 : cntMap.keySet()) {
//                if (k1 + k2 >= k) {
//                    ans += cntMap.get(k1) * cntMap.get(k2);
//                }
//            }
//        }
//        return ans;
//    }
//}