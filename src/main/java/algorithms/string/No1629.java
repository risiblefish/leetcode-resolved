package algorithms.string;

/**
 * 1629. 按键持续时间最长的键
 * @author Sean Yu
 * @date 9/1/2022 下午9:59
 */
public class No1629 {
    public static void main(String[] args) {
        System.out.println(new Solution1629().slowestKey(new int[]{12, 23, 36, 46, 62}, "spuda"));
    }
}

/**
 * 思路：
 * 审题要仔细，
 * （1）题目要求 【连续】按键时间最长，比如同一个键在连续的时间被2次按下，这也不算连续。
 * （2）并且时间一样的情况下，选择“更大”的字符
 */
class Solution1629 {
    public char slowestKey(int[] releaseTimes, String keysPressed) {
        char[] arr = keysPressed.toCharArray();
        int sum = releaseTimes[0];
        int max = releaseTimes[0];
        char ans = arr[0];
        for(int i = 1 ; i < releaseTimes.length ; i++){
            sum = releaseTimes[i] - releaseTimes[i-1];
            if(sum > max){
                max = sum;
                ans = arr[i];
            }
            if(sum == max){
                ans = ans - arr[i] >= 0 ? ans : arr[i];
            }
        }
        return ans;
    }
}