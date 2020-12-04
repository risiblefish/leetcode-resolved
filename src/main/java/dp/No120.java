package dp;

import java.util.Arrays;
import java.util.List;

/**
 * 120. 三角形最小路径和
 *
 * @author Sean Yu
 */
public class No120 {
    public static void main(String[] args) {
        List<List<Integer>> list;
        List<Integer> first = Arrays.asList(-1);
        List<Integer> second = Arrays.asList(2, 3);
        List<Integer> thrid = Arrays.asList(1, -1, -3);
        list = Arrays.asList(first, second, thrid);
        System.out.println(new Solution120().minimumTotal(list));
    }
}


/**
 * 分析最后一步：
 * <p>
 * 如果最优解的路径不为空，那么一定包含最后一个节点
 * 对这个节点而言，能到达它的，要么是它左上方的节点，要么是它右上方的节点，而最优解就是和更小的那个
 * <p>
 * 子问题：由 求包含最后一个节点的最小和 --变为-->  求包含最后一个节点左上&右上节点的最小和
 * <p>
 * 2.确定状态转移方程
 * <p>
 * 由于入参是 List<List<Integer>> t,即每一个子列表，就代表一层
 * 如t.get(0) 表示第1层， t.get(1)表示第2层， 所以要找到2层之间的关系。
 * <p>
 * 题目给出提示：相邻的结点 在这里指的是 下标 与 上一层结点下标 相同或者等于 上一层结点下标 + 1 的两个结点。
 * <p>
 * 所以对于当前层的某个节点t.get(i).get(k)而言，它的左父节点就是t.get(i-1).get(k-1),它的右父节点就是t.get(i-1).get(k) //前提是父节点存在
 * <p>
 * 用dp(i,k)表示第i层（i = 0,1,...）第k个节点（k = 0,1,...）的最小路径和，那么有
 * dp(i,k) = t.get(i).get(k) + min{ dp(i-1,k-1) , dp(i-1,k)}
 * <p>
 * 最后的解，就是dp(最下一排里)最小的那个
 * <p>
 * 3. 确定初始条件和边界情况
 * dp(0,0) = t.get(0).get(0)
 * <p>
 * 4. 计算顺序，从上到下
 * <p>
 * 由于当前行的值只依赖于上一行，所以可以直接在原有列表上更新，这样空间复杂度就是O（N）
 */
class Solution120 {
    public int minimumTotal(List<List<Integer>> triangle) {
        if (triangle == null || triangle.size() == 0) {
            return 0;
        }
        //记录三角形的层数
        int n = triangle.size();

        //如果只有1层，必定只有1个点，直接返回该点的值
        if (n == 1) {
            return triangle.get(0).get(0);
        }

        //从第1层开始遍历，跳过第0层,这样可以保证必定有上一层
        for (int i = 1; i < n; i++) {
            for (int k = 0; k < triangle.get(i).size(); k++) {
                int fromLeft = Integer.MAX_VALUE;
                int fromRight = Integer.MAX_VALUE;
                int currVal = triangle.get(i).get(k);
                //如果左父节点存在
                if (k - 1 >= 0) {
                    fromLeft = triangle.get(i - 1).get(k - 1);
                }
                //如果右父节点存在
                if (k < triangle.get(i - 1).size()) {
                    fromRight = triangle.get(i - 1).get(k);
                }
                currVal += Math.min(fromLeft, fromRight);
                triangle.get(i).set(k, currVal);
            }
        }

        //遍历最后一行，找出最小值
        int temp = triangle.get(n - 1).get(0);
        for (int k = 1; k < triangle.get(n - 1).size(); k++) {
            temp = Math.min(temp, triangle.get(n - 1).get(k));
        }
        return temp;
    }
}
