package algorithms.string;

/**
 * No.76 最小覆盖子串
 * @author Sean Yu
 */
public class No76 {

    public static void main(String[] args) {
        String s = "ADOBECODEBANC";
        String t = "ABC";
        System.out.println(new Solution76().minWindow(s, t));
    }
}

/**
 * "欠债模型"
 * 先统计目标串t的总长度total，表示"总欠款数"， 统计每个字符出现的次数owe[]，owe[x]表示欠了多少个x
 *
 * "还款规则"
 * 用一个可伸缩的窗口来括住原始串，窗口内的部分表示进行"还款"，
 * 窗口每括住一个字符x，owe[x]进行-1
 * 只有当owe[x]非负时，total才进行减1，表示此时表示"有效还款"，
 * 如果owe[x]如果已经为负，表示总欠款total里x的部分已近还清了，即欠款是由其他字符产生，此时如果再括进来一个x，表示虽然已不欠x,我们但仍"硬塞"了1个，所以给总欠款total不变
 *
 * 窗口每放开一个字符，表示再"借款"，owe[x]进行+1
 * 如果+1后，owe[x]不大于0，表示本次借的x，是之前还"硬塞"的，
 *
 * 当total为0时，表示还清了，此时进行记录
 *
 *
 * 具体操作：
 * （1）初始化total和owe[]
 * （2）初始化一个空窗口，左右边界均从0开始
 * （3）先逐渐扩大右边界r，根据"还款规则"，直到owe为0
 * （4）此时再逐渐缩小左边界l，直到欠款再次出现
 * （5）重复（3）（4），直到右边界到头
 */
class Solution76 {
    String ans = "";
    int min = Integer.MAX_VALUE;

    public String minWindow(String s, String t) {
        //优化剪枝
        if (s.length() < t.length()) return ans;

        //step1 : 初始化

        //初始化"总欠款"
        int total = t.length();
        //初始化"欠款明细"
        int[] owe = new int[Math.max('Z','z') + 1];
        for(char c : t.toCharArray()){
            owe[c]++;
        }
        //初始化窗口 [l,r)
        int l = 0, r = 0;

        //step2: 滑动窗口找解
        while(r <= s.length()){
            //如果欠款不为0，就往右继续找
            if(total > 0){
                //如果已经到头了，就跳出
                if(r == s.length()){
                    break;
                }
                char c = s.charAt(r++);
                owe[c]--;
                //"有效还款"时cnt才--
                if(owe[c] >= 0){
                    total--;
                }
            }
            //如果欠款为0，就缩小左边界，找最优解
            else {
                int curLen = r - l;
                if(curLen < min){
                    min = curLen;
                    ans = s.substring(l,r);
                }
                //继续"借款"
                char rm = s.charAt(l++);
                owe[rm]++;
                if(owe[rm] > 0){
                    total++;
                }
            }
        }
        return min == Integer.MAX_VALUE ? "" : ans;
    }
}
