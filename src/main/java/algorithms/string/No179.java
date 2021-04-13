package algorithms.string;

import java.util.Arrays;
import java.util.Comparator;

/**
 * 179. 最大数
 * @author Sean Yu
 */
public class No179 {
    public static void main(String[] args) {
        System.out.println(new Solution179().largestNumber(new int[]{0,9,8,7,6,5,4,3,2,1}));
//        System.out.println("52".compareTo("25"));
    }
}

/**
 * 思路： 定义一套排序规则，让nums按题意进行排序
 *
 * 比较器的实现分2种情况：
 * 第一种，2个数长度相等，比如45,39，返回更大的数即可
 * 第二种，2个数长度不等，比如3,30， 这种情况，先将2个数s1,s2转换为字符串，然后拼成303，330两种情况，逐位比较大小即可
 */
class Solution179 {
    public String largestNumber(int[] nums) {
        Integer[] arr = new Integer[nums.length];
        for (int i = 0; i < nums.length; i++) {
            arr[i] = nums[i];
        }
        Arrays.sort(arr, new MyComparator());
        StringBuilder sb = new StringBuilder();
        for (Integer num : arr) {
            sb.append(num);
        }
        return sb.charAt(0)== '0' ? "0" : sb.toString();
    }

    private class MyComparator implements Comparator<Integer> {
        @Override
        public int compare(Integer o1, Integer o2) {
            String s1 = o1 + "";
            String s2 = o2 + "";
            if (s1.length() == s2.length()) {
                return o2 - o1;
            } else {
                String s1s2 = s1 + s2;
                String s2s1 = s2 + s1;
                return s1s2.compareTo(s2s1);
            }
        }
    }
}

