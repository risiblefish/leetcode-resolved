package algorithms.hashtable;

/**
 * 383. 赎金信
 * @author Sean Yu
 */
public class No383 {
}

/**
 * 思路： 题目保证字母只有小写，所以可以开常熟空间的数组，记录每个字母出现的次数
 */
class Solution383 {
    public boolean canConstruct(String ransomNote, String magazine) {
        int[] count = new int[26];
        int mlen = magazine.length();
        int rlen = ransomNote.length();
        for (int i = 0; i < rlen; i++) {
            count[ransomNote.charAt(i) - 'a']++;
        }
        for (int i = 0; i < mlen; i++) {
            count[magazine.charAt(i) - 'a']--;
        }
        for (int i = 0; i < count.length; i++) {
            if (count[i] > 0) {
                return false;
            }
        }
        return true;
    }
}
