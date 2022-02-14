package algorithms.string;

/**
 * 2047. 句子中的有效单词数
 * @author Sean Yu
 * @date 27/1/2022 下午7:46
 */
public class No2047 {
    public static void main(String[] args) {
        System.out.println(new Solution2047().countValidWords("!this  1-s b8d!"));
    }
}

class Solution2047 {
    public int countValidWords(String sentence) {
        char[] arr = sentence.toCharArray();
        int ans = 0;
        for(int i = 0 ; i < arr.length ; i++){
            if(arr[i] != ' '){
                int start = i;
                while(i < arr.length && arr[i] != ' '){
                    i++;
                }
                //此时 i 处 要么越界，要么 为空格, 所以i作为end 不能算进去
                if(isValidWord(arr, start, i)){
                    ans++;
                }
            }
        }
        return ans;
    }

    private boolean isValidWord(char[] arr, int start, int end) {
        int cnt = 0;
        for (int i = start; i < end; i++) {
            char c = arr[i];
            if (c >= '0' && c <= '9') {
                return false;
            }
            else if (c == '-') {
                if (i == start || i == end - 1 || cnt > 0 || !Character.isAlphabetic(arr[i + 1])) {
                    return false;
                }
                cnt++;
            }
            if ((c == '!' || c == ',' || c == '.') && i != end - 1) {
                return false;
            }
        }
        return true;
    }
}