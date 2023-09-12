package algorithms.stackandqueue;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

public class No393 {
    public static void main(String[] args) {
        int[] data = {228,189,160,229,165,189,13,10};
        System.out.println(new Solution255().validUtf8(data));
        System.out.println(100000 | 100000 | 100000 | 100000 | 100000);
    }
}

class Solution255 {
    public boolean validUtf8(int[] data) {
        Queue<String> q = new ArrayDeque();
        for (int i : data) {
            q.offer(toBi(i));
        }

        int n = 0;
        while (!q.isEmpty()) {
            String first = q.poll();
            n = count(first);
            if (n == 0) {
                continue;
            }
            if(n == 1){
                return false;
            }
            if(n > 4){
                return false;
            }
            while (!q.isEmpty() && n > 1) {
                n--;
                if (q.poll().indexOf("10") != 0) {
                    return false;
                }
            }
            if(n > 1) {
                return false;
            }
        }
        return true;
    }


    private String toBi(int n) {
        String res = "";
        int len = 0;
        while (n > 0) {
            int left = n % 2;
            res = left + res;
            len++;
            n /= 2;
        }
        while (len < 8) {
            res = "0" + res;
            len++;
        }
        return res;
    }

    private int count(String s) {
        int i = 0;
        int len = s.length();
        while (i < len && s.charAt(i) == '1') {
            i++;
        }
        return i;
    }
}
