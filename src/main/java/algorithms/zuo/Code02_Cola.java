package algorithms.zuo;

public class Code02_Cola {
    /*
     * ������ ʱ�����ƣ� 3000MS �ڴ����ƣ� 589824KB ��Ŀ������
     * ���ν����Ҫ��ҵ�ˣ���ͬѧ����Я���϶������ձ���ҵ���С�����һ���г̽������һص��˾Ƶ귿�䣬��ʱ��ͬѧ�Ƕ��ܿڿʣ�
     * ʯͷ������ѡ������ȥ¥�µ��Զ����������������֡� ������ֻ֧��Ӳ��֧���������˶�ֻ֧��10 ��50��100
     * ������һ�ι�����Ϊֻ�ܳ�һƿ���֣���ÿ�ι��������������Сö����Ӳ�ҡ�������Ͷ��100Բ������30Բ��������50Բһö��10Բ��ö��
     * ������Ҫ����Ŀ��������� m��������ͷӵ�е� 10,50,100 ���Ӳ�ҵ�ö���ֱ��� a,b,c�����ֵļ۸���x(x��10�ı���)��
     * �����������ʹ�ô�������Ǯ�ǹ��������,��������ҪͶ��Ӳ�Ҵ����� �������� �������룬 ��Ҫ���ֵ�����Ϊ m 10Ԫ������Ϊ a 50Ԫ������Ϊ b
     * 100Ԫ������Ϊ c 1ƿ���ֵļ۸�Ϊ x ������� �����ǰ�������ҪͶ��Ӳ�ҵĴ���
     * ������Ҫ����2ƿ���֣�ÿƿ����250Բ��������100Բ3ö��50Բ4ö��10Բ1ö�� �����1ƿͶ��100Բ3ö����50Բ �����2ƿͶ��50Բ5ö
     * �������ܹ���Ҫ����8�ν��Ͷ�ݲ��� �������� 2 1 4 3 250 ������� 8
     */

    // �������ԣ�Ϊ����֤��ʽ��������
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
        System.out.println("���������ӡ�������ݣ����������ȷ");
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
                System.out.println("correct �� " + ans2);
                System.out.println("yours �� " + ans1);
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
    //preLeft ��ʾ��i�Ż���֮ǰ��ʣ���ܶ preLeftCount��ʾi�Ż���֮ǰ��ʣ���������
    int preLeft, preLeftCount, puts, target;

    public Buyer(int ten, int fifty, int hundred, int target) {
        this.amount = new int[]{100, 50, 10};
        this.count = new int[]{hundred, fifty, ten};
        this.target = target;
    }

    public int buy(int colaPrice) {
        for (int i = 0; i < 3 && target > 0; i++) {
            //���i��֮ǰ�����л������ + ���е�ǰi�Ż��ҵ���� ������1ƿ����
            if (!isAbleToBuyFirst(colaPrice, i)) {
                continue;
            }
            buyMore(colaPrice, i);
        }
        return target == 0 ? puts : -1;
    }

    private void buyMore(int colaPrice, int i) {
        //�������ʱ��preLeft���������״̬�����Ե�ǰi�����Ļ��Ҿ��������ֵ�Ļ���
        //����Ҫ��Ӧ�󾡴�����ֻ������i�Ż��������
        //��1ƿ������Ҫ������i�Ż���
        int cntForOne = (colaPrice + amount[i] - 1) / amount[i];
        //ֻ��i�Ż��ң����ñ�i��С��ֵ�Ļ��ң�����ƿ����
        int colaCnt = count[i] / cntForOne;
        //�������ܶ࣬��ôֻ��targetƿ���ɴ�Ŀ��
        colaCnt = Math.min(colaCnt, target);
        //ֻ��i�Ż���ÿ��1ƿ���ֻ������������
        int changeForOne = cntForOne * amount[i] - colaPrice;
        //�ܹ������colaCnt������
        spreadChange(changeForOne, i+1, colaCnt);
        //����Ͷ�Ҵ���
        int curPut = cntForOne * colaCnt;
        puts += curPut;
        target -= colaCnt;
        count[i] -= curPut;
        preLeftCount = count[i];
        preLeft = count[i] * amount[i];
    }

    private boolean isAbleToBuyFirst(int colaPrice, int i) {
        //���������1ƿ����Ҫ�����ŵ�i�����Ļ���
        int firstCount = (colaPrice - preLeft + amount[i] - 1) / amount[i];
        //�����Ҫ������ > ��i�Ż��ҵ����������ʾ����
        if (firstCount > count[i]) {
            preLeft += amount[i] * count[i];
            preLeftCount += count[i];
            return false;
        }
        //����������1ƿ
        target--;
        //�����1ƿ���ֵ����� = ��� + firstCount * amount[i] - 1ƿ���ֵļ۸�
        int change = preLeft + firstCount * amount[i] - colaPrice;
        //�������·���Ǯ��,��������ȻС��amount[i]�����Դ�i+1����ֵ��ʼ�·�
        spreadChange(change, i+1, 1);
        //������Ͷ�Ҵ���
        puts += preLeftCount + firstCount;
        //��ʱ��i������һ��ȫ��������
        preLeft = preLeftCount =  0;
        //���µ�ǰ��������
        count[i] -= firstCount;
        return true;
    }

    //����1�����㣬��change�ӵ�i����ʼ�ַ�
    private void spreadChangeForOneTime(int change, int i) {
        while (i < 3) {
            int cnt = change / amount[i];
            count[i] += cnt;
            change -= cnt * amount[i];
            i++;
        }
    }

    //����times�����㣬���spreadChangeForOneTime���
    private void spreadChange(int change, int i, int times) {
        while (i < 3) {
            int cnt = change / amount[i];
            count[i] += cnt * times;
            change -= cnt * amount[i];
            i++;
        }
    }
}
