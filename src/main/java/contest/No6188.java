package contest;

import java.util.Random;

/**
 * @author Sean Yu
 */
public class No6188 {
}

class Solution6188 {
    int[] hi;
    String[] na;
    Random rand;

    public String[] sortPeople(String[] names, int[] heights) {
        rand = new Random();
        na = names;
        hi = heights;
        sort(0, na.length - 1);
        return names;
    }

    private void sort(int l, int r) {
        if (l >= r) {
            return;
        }
        int randIdx = l + rand.nextInt(r - l + 1);
        swap(randIdx, r);
        int[] eq = partition(l, r);
        sort(l, eq[0] - 1);
        sort(eq[1] + 1, r);
    }

    private int[] partition(int l, int r) {
        if (l > r) {
            return new int[]{-1, -1};
        }
        if (l == r) {
            return new int[]{l, r};
        }
        int cur = l;
        int lessBound = l - 1;
        int moreBound = r;
        int target = hi[moreBound];
        while (cur < moreBound) {
            if (hi[cur] == target) {
                cur++;
            } else if (hi[cur] > target) {
                swap(cur, lessBound + 1);
                cur++;
                lessBound++;
            } else {
                swap(cur, moreBound - 1);
                moreBound--;
            }
        }
        swap(cur, r);
        return new int[]{lessBound + 1, moreBound};
    }


    private void swap(int l, int r) {
        int tmp = hi[l];
        hi[l] = hi[r];
        hi[r] = tmp;

        String tmpStr = na[l];
        na[l] = na[r];
        na[r] = tmpStr;
    }
}
