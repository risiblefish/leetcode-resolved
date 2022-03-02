package algorithms.string;

/**
 * 564. 寻找最近的回文数
 */
public class No564 {
    public static void main(String[] args) {
        String s = "235843009213693952";
        System.out.println(new Solution564().nearestPalindromic(s));
    }
}

/**
 * 本题难点在于特殊情况较多：共5种，第一次很难考虑周全
 * 思路参考官方题解
 * 5个备选值里选最小：
 * p1: 99...99,
 * p2 : 100...001,
 * p3: n替换低位对称
 * p4: n中轴数+1再对称 特殊情况，如果中轴数为9，则+1替换为0
 * p5: n中轴数-1再对称 特殊情况，如果中轴数为0，则-1替换为9
 */
class Solution564 {
    Long og;
    int len;

    public String nearestPalindromic(String n) {
        og = Long.parseLong(n);
        len = n.length();
        if (len == 1) {
            return "0".equals(n) ? "1" : Integer.parseInt(n) - 1 + "";
        }
        String res = "";
        int halfIdx = len / 2;
        String p1 = "";
        String p2 = "1";
        for (int i = 0; i < len - 1; i++) {
            p1 += 9;
            p2 += 0;
        }
        p2 += "1";
        String p3 = "";
        String p4 = "";
        String p5 = "";
        if (len % 2 == 0) {
            for (int i = 0; i < halfIdx; i++) {
                char c = n.charAt(i);
                p3 += c;
                if (i != halfIdx - 1) {
                    p4 += c;
                    p5 += c;
                }
            }
            int mid = n.charAt(halfIdx - 1) - '0';
            p4 += mid == 9 ? 0 : (mid + 1);
            p4 += mid == 9 ? 0 : (mid + 1);
            p5 += mid == 0 ? 9 : (mid - 1);
            p5 += mid == 0 ? 9 : (mid - 1);
            for (int i = halfIdx - 1; i >= 0; i--) {
                char c = n.charAt(i);
                p3 += c;
                if (i != halfIdx - 1) {
                    p4 += c;
                    p5 += c;
                }
            }
            res = getNearest(p1, p2, p3, p4, p5);
        } else {
            for (int i = 0; i < halfIdx; i++) {
                char c = n.charAt(i);
                p3 += c;
                p4 += c;
                p5 += c;
            }
            int mid = n.charAt(halfIdx) - '0';
            p3 += mid;
            p4 += mid == 9 ? 0 : (mid + 1);
            p5 += mid == 0 ? 9 : (mid - 1);
            for (int i = halfIdx - 1; i >= 0; i--) {
                char c = n.charAt(i);
                p3 += c;
                p4 += c;
                p5 += c;
            }
            res = getNearest(p1, p2, p3, p4, p5);
        }
        return res;
    }

    private String getNearest(String... candidates) {
        Long res = Long.MAX_VALUE;
        long minGap = Long.MAX_VALUE;
        for (String c : candidates) {
            long nc = Long.parseLong(c);
            if (nc == og) {
                continue;
            }
            long gap = Math.abs(nc - og);
            if (gap == minGap) {
                res = Math.min(res, nc);
            } else if (gap < minGap) {
                minGap = gap;
                res = nc;
            }
        }
        return res + "";
    }
}

