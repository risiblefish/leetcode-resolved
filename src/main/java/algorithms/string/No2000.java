package algorithms.string;

/**
 * 2000. 反转单词前缀
 * @author Sean Yu
 */
public class No2000 {
    public static void main(String[] args) {
        System.out.println(new Solution2000().reversePrefix("abcd", 'z'));
    }
}


class Solution2000 {
    public String reversePrefix(String word, char ch) {
        int i = 0;
        while(i < word.length() && word.charAt(i) != ch ){
            i++;
        }
        if(i == word.length()){
            return word;
        }
        StringBuilder sb = new StringBuilder();
        int r = i+1;
        while(i >= 0){
            sb.append(word.charAt(i--));
        }
        while(r < word.length()){
            sb.append(word.charAt(r++));
        }
        return sb.toString();
    }
}