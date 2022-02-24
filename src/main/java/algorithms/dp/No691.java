package algorithms.dp;

import java.util.HashMap;
import java.util.Map;

/**
 * 691. 贴纸拼词
 */
public class No691 {
    public static void main(String[] args) {
//        String[] stickers = new String[]{"these", "guess", "about", "garden", "him"};
//        String target = "atomher";
//        String[] stickers = new String[]{"a", "b", "c", "abc"};
//        String target = "abc";
        String[] stickers = new String[]{"with", "example", "science"};
        String target = "thehat";
        System.out.println(new Solution691().minStickers(stickers, target));
    }
}

/**
 * 暴力递归 到 动态规划
 *
 * 递归的思路： 在有解k的情况下， 总是先选出第1个字符串，第2个字符串，，，第k个字符串
 * 假设备选字符组stickers里有h个字符串
 * 那么可以穷举，
 * (1)刚开始， 可以选 stickers[0] ， stickers[1], stickers[2], .... stickers[h] 共h种情况
 * (2)选择完成之后， 从target里去掉stickers[?]的字符，然后剩下的字符串假设叫left
 * (3)对left而言，它就变成一个新的target，但字符比target更少，又从(1)开始， 直到left为空（长度为0）， 这个过程就是子过程
 *
 * 然后我们可以把每次得到的的target和count缓存下来，避免重复计算
 * 一个重要的优化部分：
 * 当我们判断target 能否 被sticker 消消乐时， 只用判断target的第1个字符，是否在sticker中， 这样能避免大量的遍历
 * 因为target有解的前提是，target的每个字符都能被消消乐，所以target的第1个字符一定要能被消消乐。
 *
 * 另一个优化： 对于 stickers 我们可以只进行1次toCharArray， 然后把这些array缓存下来
 */
class Solution691 {
    int[][] sti;
    Map<String, Integer> cache;

    public int minStickers(String[] stickers, String target) {
        cache = new HashMap<>();
        int slen = stickers.length;
        sti = new int[slen][1];
        for (int i = 0; i < stickers.length; i++) {
            sti[i] = getArr(stickers[i]);
        }
        int count = process(target);
        return count == Integer.MAX_VALUE ? -1 : count;
    }

    private int process(String target) {
        if (target.length() == 0) {
            return 0;
        }
        if (cache.containsKey(target)) {
            return cache.get(target);
        }
        int count = Integer.MAX_VALUE;
        for (int[] s : sti) {
            char c = target.charAt(0);
            //重要优化
            if (s[c - 'a'] > 0) {
                String left = minus(getArr(target), s);
                if (left.length() < target.length()) {
                    int l = process(left);
                    count = Math.min(count, l);
                }
            }
        }
        return count == Integer.MAX_VALUE ? count : count + 1;
    }

    private String minus(int[] left, int[] s) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 26; i++) {
            if (s[i] > 0 && left[i] > 0) {
                left[i] -= s[i];
            }
            if (left[i] > 0) {
                int cnt = left[i];
                while (cnt > 0) {
                    sb.append((char) (i + 'a'));
                    cnt--;
                }
            }
        }
        return sb.toString();
    }

    private int[] getArr(String s) {
        int[] arr = new int[26];
        for (char ch : s.toCharArray()) {
            arr[ch - 'a']++;
        }
        return arr;
    }
}
