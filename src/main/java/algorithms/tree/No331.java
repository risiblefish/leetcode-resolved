package algorithms.tree;

/**
 * 331. 验证二叉树的前序序列化
 * @author Sean Yu
 */
public class No331 {
    public static void main(String[] args) {
        new Solution331().isValidSerialization("1##1");
    }
}


/**
 * 思路：
 * 题目要求在不构造树的前提下实现验证。
 * 利用二叉树的性质（或者找规律），记不住就完蛋。
 * 合法的二叉树，总出度 = 总入度
 * 每个节点的出度，可以理解为指向几个子节点，出度就为几
 * 同理，某个节点，同时被几个节点所指，入度就是几，由于是二叉树，子节点的入度只可能为1，根节点的入度为0
 * 题目把空的叶子节点（即#）也算做了子节点，所以指向#也会计算出度，即#的入度为1，出度为0
 * 前序遍历： 根，左，右
 *
 * 约定： 出度用正数表示，入度用负数表示， 所以有： 根节点（-0 + 2），非空子节点（-1+2），空子节点（-1+0）
 * 当把所有的未遍历的节点遍历完成之后，应当有度数为0，二叉树才合法
 * 如果还未遍历完成，就出现度数为0，这样也是不合法的，因为只要有非空子节点挂载，那么出度是必然大于入度的。
 *
 * 前序遍历 根 - 左 - 右 的顺序性质，可以看到，永远先遍历根，所以遍历过程中出度是大于入度的
 * 思考：如何验证中序和后序序列化？
 *
 */
class Solution331 {
    public boolean isValidSerialization(String preorder) {
        int count = 1;
        int n = preorder.length();
        int i = 0;
        boolean isDigit = false;
        while (i < n) {
            if (count <= 0) {
                return false;
            }
            char c = preorder.charAt(i);
            if (c == ',') {
                i++;
                isDigit = false;
                continue;
            }
            if (Character.isDigit(c)) {
                if (!isDigit) {
                    isDigit = true;
                    count++;
                }
                i++;
            }
            if (c == '#') {
                isDigit = false;
                count--;
                i++;
            }
        }
        return count == 0;
    }
}
