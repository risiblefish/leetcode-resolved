package algorithms;

import java.util.PriorityQueue;

/**
 * @author Sean Yu
 * @create 2022/3/23 10:35 PM
 */
public class No440 {
    public static void main(String[] args) {
        int n = 13;
        int k = 2;
        System.out.println(new Solution440().findKthNumber(n, k));
    }
}

class Solution440 {
//    public int findKthNumber(int n, int k) {
//        Integer[] arr = new Integer[n+1];
//        for(int i = 0; i < n ; i++){
//            arr[i] = i+1;
//        }
//        Arrays.sort(arr, (n1, n2) -> {
//            String s1 = n1 + "";
//            String s2 = n2 + "";
//            return s1.compareTo(s2);
//        });
//        return arr[k-1];
//    }

    public int findKthNumber(int n, int k) {
        PriorityQueue<Integer> pq = new PriorityQueue<>((n1,n2) -> {
            String s1 = n1 + "";
            String s2 = n2 + "";
            return s1.compareTo(s2);
        });
        for(int i = 1 ; i <= n ; i++){
            pq.offer(i);
        }
        for(int i = 1; i < k; i++){
            pq.poll();
        }
        return pq.poll();
    }
}
