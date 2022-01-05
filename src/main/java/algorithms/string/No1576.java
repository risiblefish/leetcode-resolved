package algorithms.string;

/**
 * 1576. 替换所有的问号
 * @author Sean Yu
 */
public class No1576 {
}

/**
 * 思路：
 * (1)首先要读懂题意，题目的意思是不能出现连续的字符，而不是字符串，所以只需要保证 ? 左右2个字符与它不同即可
 * (2)? 左右的不同字符l,r，要么l==r,要么 l != r， 即 ： l和r的可能值至多只会有2个， 所以 ? 的备选字符，只需要找3个不同的字符即可，这里选a,b,c 这3个字符作为填充备选
 * (3)?可能出现在开始位置末位置，所以要注意一下边界条件
 */
class Solution576 {
    public String modifyString(String s) {
        char[] arr = s.toCharArray();
        int n = arr.length;
        for(int i = 0 ; i < n ; i++){
            if(arr[i] == '?'){
                for(char backup = 'a' ; backup <= 'c' ; backup++){
                    if(i > 0 && arr[i-1] == backup || i < n - 1 && arr[i+1] == backup){
                        continue;
                    }
                    arr[i] = backup;
                    break;
                }
            }
        }
        return new String(arr);
    }
}
