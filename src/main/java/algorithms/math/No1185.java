package algorithms.math;

/**
 * 1185. 一周中的第几天
 * @author Sean Yu
 * @date 3/1/2022 下午11:06
 */
public class No1185 {
    public static void main(String[] args) {
        System.out.println(new Solution1185().dayOfTheWeek(18, 7, 1999));
    }
}

/**
 * 题目保证年份在1971年开始
 * 这里需要提前知道 1970年最后一天（1970-12-31）是周四
 * 我们需要计算 输入的日期 和 1970-12-31 之间所差的天数， 再+4（因为是周四），然后对7取模
 *
 * 思路：
 *  初始化res = 4
 * （1）计算 year 贡献的天数 ， 即 (year - 1970) * 365，其中闰年的年份 天数需要再 + 1
 * （2）计算 month 贡献的天数，即 从1月 到 month-1 贡献的天数 , 比如 month = 7， 那么1-6月的天数需要计算出来， 如果year是闰年，则2月的天数需要+1
 * （3）计算 day 贡献的天数
 *  res = 4 + (1) + (2) + (3)
 *  res % 7 的值，就是 对应的星期几， 0-6 分别是 周日，周1,2,3,4,5,6
 */
class Solution1185 {
    public String dayOfTheWeek(int day, int month, int year) {
        String[] week
                = new String[]{ "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};


        int res = 4;
        res += byYear(year) + byMonth(month,year) + day;
        return week[res % 7];
    }

    private boolean isLeap(int year){
        return year % 4 == 0 && year % 100 != 0 || year % 400 == 0;
    }

    private int byYear(int curr){
        int i = 1971;
        int res = 0;
        while(i < curr){
            res += 365;
            if(isLeap(i)){
                res++;
            }
            i++;
        }
        return res;
    }

    private int byMonth(int curr, int year){
        int[] Month = new int[]{31,28,31,30,31,30,31,31,30,31,30,31};
        int i = 1;
        int res = 0;
        while(i < curr){
            res += Month[i-1];
            if(i == 2 && isLeap(year)){
                res++;
            }
            i++;
        }
        return res;
    }
}