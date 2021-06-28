package algorithms.string;

/**
 * 459. 重复的子字符串
 * @author Sean Yu
 */
public class No459 {
    public static void main(String[] args) {
        System.out.println(new Solution459().repeatedSubstringPattern("abab"));
    }
}

/**
 * 截取 + 拼接
 */
class Solution459 {
    public boolean repeatedSubstringPattern(String s) {
        StringBuilder sb = new StringBuilder();
        sb.append(s);
        sb.append(s);
        sb.deleteCharAt(0);
        sb.deleteCharAt(sb.length() - 1);
        return sb.indexOf(s) != -1;
    }
}

/**
 * KMP
 */
class Solution459_II {
    public boolean repeatedSubstringPattern(String s) {
        char[] pattern = s.toCharArray();
        int[] pmt = getPmtTable(pattern);
        String queryStr = s + s;
        char[] query = queryStr.toCharArray();
        int j = 0;
        //细节，query串需要去头去尾，即 s + s去掉第一个和最后一个字符
        for (int i = 1; i < query.length - 1; i++) {
            while (j > 0 && query[i] != pattern[j]) {
                j = pmt[j - 1];
            }
            if (query[i] == pattern[j]) {
                j++;
            }
            if (j == pattern.length) {
                return true;
            }
        }
        return false;
    }

    public int[] getPmtTable(char[] arr) {
        int j = 0;
        int[] pmt = new int[arr.length];
        for (int i = 1; i < arr.length; i++) {
            while (j > 0 && arr[i] != arr[j]) {
                j = pmt[j - 1];
            }
            if (arr[i] == arr[j]) {
                j++;
            }
            pmt[i] = j;
        }
        return pmt;
    }
}


/**
 * 优化KMP
 */
class Solution_III {
    public boolean repeatedSubstringPattern(String s) {
        char[] pattern = s.toCharArray();
        int[] pmt = getPmtTable(pattern);
        int len = pmt.length;
        return pmt[len - 1] != 0 && len % (len - pmt[len - 1]) == 0;
    }

    public int[] getPmtTable(char[] arr){
        int j = 0;
        int[] pmt = new int[arr.length];
        for(int i = 1 ; i < arr.length ; i++){
            while(j > 0 && arr[i] != arr[j]){
                j = pmt[j-1];
            }
            if(arr[i] == arr[j]){
                j++;
            }
            pmt[i] = j;
        }
        return pmt;
    }
}
