package algorithms.zuo;

public class Code02_Cola {
    /*
     * 买饮料 时间限制： 3000MS 内存限制： 589824KB 题目描述：
     * 游游今年就要毕业了，和同学们在携程上定制了日本毕业旅行。愉快的一天行程结束后大家回到了酒店房间，这时候同学们都很口渴，
     * 石头剪刀布选出游游去楼下的自动贩卖机给大家买可乐。 贩卖机只支持硬币支付，且收退都只支持10 ，50，100
     * 三种面额。一次购买行为只能出一瓶可乐，且每次购买后总是找零最小枚数的硬币。（例如投入100圆，可乐30圆，则找零50圆一枚，10圆两枚）
     * 游游需要购买的可乐数量是 m，其中手头拥有的 10,50,100 面额硬币的枚数分别是 a,b,c，可乐的价格是x(x是10的倍数)。
     * 如果游游优先使用大面额购买且钱是够的情况下,请计算出需要投入硬币次数？ 输入描述 依次输入， 需要可乐的数量为 m 10元的张数为 a 50元的张数为 b
     * 100元的张树为 c 1瓶可乐的价格为 x 输出描述 输出当前金额下需要投入硬币的次数
     * 例如需要购买2瓶可乐，每瓶可乐250圆，手里有100圆3枚，50圆4枚，10圆1枚。 购买第1瓶投递100圆3枚，找50圆 购买第2瓶投递50圆5枚
     * 所以是总共需要操作8次金额投递操作 样例输入 2 1 4 3 250 样例输出 8
     */

    // 暴力尝试，为了验证正式方法而已
    public static int right(int m, int a, int b, int c, int x) {
        int[] qian = {100, 50, 10};
        int[] zhang = {c, b, a};
        int puts = 0;
        while (m != 0) {
            int cur = buy(qian, zhang, x);
            if (cur == -1) {
                return -1;
            }
            puts += cur;
            m--;
        }
        return puts;
    }

    public static int buy(int[] qian, int[] zhang, int rest) {
        int first = -1;
        for (int i = 0; i < 3; i++) {
            if (zhang[i] != 0) {
                first = i;
                break;
            }
        }
        if (first == -1) {
            return -1;
        }
        if (qian[first] >= rest) {
            zhang[first]--;
            giveRest(qian, zhang, first + 1, qian[first] - rest, 1);
            return 1;
        } else {
            zhang[first]--;
            int next = buy(qian, zhang, rest - qian[first]);
            if (next == -1) {
                return -1;
            }
            return 1 + next;
        }
    }

    public static void giveRest(int[] qian, int[] zhang, int i, int oneTimeRest, int times) {
        for (; i < 3; i++) {
            zhang[i] += (oneTimeRest / qian[i]) * times;
            oneTimeRest %= qian[i];
        }
    }

    public static void main(String[] args) {
        int testTime = 1000;
        int zhangMax = 10;
        int colaMax = 10;
        int priceMax = 20;
        System.out.println("如果错误会打印错误数据，否则就是正确");
        System.out.println("test begin");
        for (int i = 0; i < testTime; i++) {
            int colaCount = (int) (Math.random() * colaMax);
            int ten = (int) (Math.random() * zhangMax);
            int fifty = (int) (Math.random() * zhangMax);
            int hundred = (int) (Math.random() * zhangMax);
            int colaPrice = ((int) (Math.random() * priceMax) + 1) * 10;
            int ans1 = new Buyer(ten, fifty, hundred, colaCount).buy(colaPrice);
            int ans2 = right(colaCount, ten, fifty, hundred, colaPrice);
            if (ans1 != ans2) {
                System.out.println("int colaCount = " + colaCount + ";");
                System.out.println("int ten = " + ten + ";");
                System.out.println("int fifty = " + fifty + ";");
                System.out.println("int hundred = " + hundred + ";");
                System.out.println("int colaPrice = " + colaPrice + ";");
                System.out.println("correct ： " + ans2);
                System.out.println("yours ： " + ans1);
                break;
            }
        }
        System.out.println("test end");
//        int hundred = 4;
//        int fifty = 2;
//        int ten = 9;
//        int colaCount = 6;
//        int colaPrice = 70;
//        int my = new Buyer(ten, fifty, hundred, colaCount).buy(colaPrice);
//        System.out.println(my);
//        System.out.println(right(colaCount,ten,fifty,hundred,70));
    }

}

class Buyer {
    int[] amount;
    int[] count;
    //preLeft 表示在i号货币之前的剩余总额， preLeftCount表示i号货币之前的剩余货币数量
    int preLeft, preLeftCount, puts, target;

    public Buyer(int ten, int fifty, int hundred, int target) {
        this.amount = new int[]{100, 50, 10};
        this.count = new int[]{hundred, fifty, ten};
        this.target = target;
    }

    public int buy(int colaPrice) {
        for (int i = 0; i < 3 && target > 0; i++) {
            //如果i号之前的所有货币余额 + 所有当前i号货币的面额 都买不起1瓶可乐
            if (!isAbleToBuyFirst(colaPrice, i)) {
                continue;
            }
            buyMore(colaPrice, i);
        }
        return target == 0 ? puts : -1;
    }

    private void buyMore(int colaPrice, int i) {
        //继续买的时候，preLeft都是用完的状态，所以当前i号面额的货币就是最大面值的货币
        //由于要求应大尽大，所以只考虑用i号货币买可乐
        //买1瓶可乐需要多少张i号货币
        int cntForOne = (colaPrice + amount[i] - 1) / amount[i];
        //只用i号货币，不用比i更小面值的货币，能买几瓶可乐
        int colaCnt = count[i] / cntForOne;
        //如果能买很多，那么只买target瓶即可打到目的
        colaCnt = Math.min(colaCnt, target);
        //只用i号货币每买1瓶可乐会产生多少找零
        int changeForOne = cntForOne * amount[i] - colaPrice;
        //总共会产生colaCnt次找零
        spreadChange(changeForOne, i+1, colaCnt);
        //本次投币次数
        int curPut = cntForOne * colaCnt;
        puts += curPut;
        target -= colaCnt;
        count[i] -= curPut;
        preLeftCount = count[i];
        preLeft = count[i] * amount[i];
    }

    private boolean isAbleToBuyFirst(int colaPrice, int i) {
        //如果买的起第1瓶，需要多少张第i号面额的货币
        int firstCount = (colaPrice - preLeft + amount[i] - 1) / amount[i];
        //如果需要的数量 > 第i号货币的总数，则表示买不起
        if (firstCount > count[i]) {
            preLeft += amount[i] * count[i];
            preLeftCount += count[i];
            return false;
        }
        //否则买得起第1瓶
        target--;
        //买完第1瓶可乐的找零 = 余额 + firstCount * amount[i] - 1瓶可乐的价格
        int change = preLeft + firstCount * amount[i] - colaPrice;
        //将找零下发到钱包,这个找零必然小于amount[i]，所以从i+1号面值开始下发
        spreadChange(change, i+1, 1);
        //更新总投币次数
        puts += preLeftCount + firstCount;
        //此时比i大的面额一定全部用完了
        preLeft = preLeftCount =  0;
        //更新当前面额的数量
        count[i] -= firstCount;
        return true;
    }

    //进行1次找零，将change从第i号面额开始分发
    private void spreadChangeForOneTime(int change, int i) {
        while (i < 3) {
            int cnt = change / amount[i];
            count[i] += cnt;
            change -= cnt * amount[i];
            i++;
        }
    }

    //进行times次找零，结合spreadChangeForOneTime理解
    private void spreadChange(int change, int i, int times) {
        while (i < 3) {
            int cnt = change / amount[i];
            count[i] += cnt * times;
            change -= cnt * amount[i];
            i++;
        }
    }
}
