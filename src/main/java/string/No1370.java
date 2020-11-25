package string;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * @author Sean Yu
 */
public class No1370 {
    public static void main(String[] args) {
        System.out.println(new Solution1370().sortString("aaaabbbbcccc"));
    }
}

/**
 * 给你一个字符串 s ，请你根据下面的算法重新构造字符串：
 * <p>
 * 1.从 s 中选出 最小 的字符，将它 接在 结果字符串的后面。
 * 2.从 s 剩余字符中选出 最小 的字符，且该字符比上一个添加的字符大，将它 接在 结果字符串后面。
 * 3.重复步骤 2 ，直到你没法从 s 中选择字符。
 * 4.从 s 中选出 最大 的字符，将它 接在 结果字符串的后面。
 * 5.从 s 剩余字符中选出 最大 的字符，且该字符比上一个添加的字符小，将它 接在 结果字符串后面。
 * 6.重复步骤 5 ，直到你没法从 s 中选择字符。
 * 7.重复步骤 1 到 6 ，直到 s 中所有字符都已经被选过。
 * 在任何一步中，如果最小或者最大字符不止一个 ，你可以选择其中任意一个，并将其添加到结果字符串。
 * <p>
 * 请你返回将 s 中字符重新排序后的 结果字符串
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/increasing-decreasing-string
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
class Solution1370 {
    public String sortString(String s) {
        if (s == null) {
            return null;
        }
        char[] arr = s.toCharArray();
        Arrays.sort(arr);
        LinkedList<Character> list = new LinkedList();
        for (char c : arr) {
            list.add(c);
        }
        StringBuilder result = new StringBuilder();

        do {
            Character c1 = stepOne(list);
            if (c1 == null) {
                return result.toString();
            }
            result.append(c1);

            Character c2 = stepTwo(list, c1);
            while (c2 != null) {
                result.append(c2);
                c2 = stepTwo(list, c2);
            }

            Character c4 = stepFour(list);
            if (c4 == null) {
                return result.toString();
            }
            result.append(c4);

            Character c5 = stepFive(list, c4);
            while (c5 != null) {
                result.append(c5);
                c5 = stepFive(list, c5);
            }
        } while (!list.isEmpty());

        return result.toString();
    }


    public Character stepOne(LinkedList<Character> list) {
        return list.isEmpty() ? null : list.removeFirst();
    }

    public Character stepTwo(LinkedList<Character> list, char lastAdd) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) > lastAdd) {
                return list.remove(i);
            }
        }
        return null;
    }

    public Character stepFour(LinkedList<Character> list) {
        return list.isEmpty() ? null : list.removeLast();
    }

    public Character stepFive(LinkedList<Character> list, char lastAdd) {
        for (int i = list.size() - 1; i >= 0; i--) {
            if (list.get(i) < lastAdd) {
                return list.remove(i);
            }
        }
        return null;
    }
}
