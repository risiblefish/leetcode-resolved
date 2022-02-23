package algorithms.dualpointer;

/**
 * 917. 仅仅反转字母
 */
public class No917 {
    public static void main(String[] args) {
        System.out.println(new Solution917().reverseOnlyLetters("ab_cd"));
    }
}

class Solution917 {
    public String reverseOnlyLetters(String s) {
        char[] arr = s.toCharArray();
        int l = 0, r = arr.length - 1;
        while (l < r) {
            while (l < r && !Character.isAlphabetic(arr[l])) {
                l++;
            }
            if (l >= r) {
                break;
            }
            while (r > l && !Character.isAlphabetic(arr[r])) {
                r--;
            }
            if (l < r) {
                swap(l++, r--, arr);
            }
        }
        return new String(arr);
    }

    private void swap(int i, int j, char[] arr) {
        char temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
