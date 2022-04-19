package algorithms.segmenttree.segmenttree;

/**
 * @author Sean Yu
 * @create 2022/4/18 11:28
 */

public class Right {
    public static void main(String[] args) {
        int[] origin = { 2, 1, 1, 2, 3, 4, 5 };
        SegmentTree seg = new SegmentTree(origin);
        int S = 1; // ��������Ŀ�ʼλ�ã��涨��1��ʼ������0��ʼ -> �̶�
        int N = origin.length; // ��������Ľ���λ�ã��涨�ܵ�N������N-1 -> �̶�
        int root = 1; // ��������ͷ�ڵ�λ�ã��涨��1������0 -> �̶�
        int L = 2; // ��������Ŀ�ʼλ�� -> �ɱ�
        int R = 5; // ��������Ľ���λ�� -> �ɱ�
        int C = 4; // Ҫ�ӵ����ֻ���Ҫ���µ����� -> �ɱ�
        // �������ɣ�������[S,N]������Χ��build
//        seg.build(S, N, root);
        // �����޸ģ����Ըı�L��R��C��ֵ������ֵ���ɸı�
        seg.add(L, R, C);
        // ������£����Ըı�L��R��C��ֵ������ֵ���ɸı�
        seg.update(L, R, C);
        // �����ѯ�����Ըı�L��R��ֵ������ֵ���ɸı�
        long sum = seg.query(L, R);
        System.out.println(sum);
        System.out.println("���������Կ�ʼ...");
        System.out.println("���Խ�� : " + (test() ? "ͨ��" : "δͨ��"));

    }
    public int[] arr;

    public Right(int[] origin) {
        arr = new int[origin.length + 1];
        for (int i = 0; i < origin.length; i++) {
            arr[i + 1] = origin[i];
        }
    }

    public void update(int L, int R, int C) {
        for (int i = L; i <= R; i++) {
            arr[i] = C;
        }
    }

    public void add(int L, int R, int C) {
        for (int i = L; i <= R; i++) {
            arr[i] += C;
        }
    }

    public long query(int L, int R) {
        long ans = 0;
        for (int i = L; i <= R; i++) {
            ans += arr[i];
        }
        return ans;
    }

    public static int[] genarateRandomArray(int len, int max) {
        int size = (int) (Math.random() * len) + 1;
        int[] origin = new int[size];
        for (int i = 0; i < size; i++) {
            origin[i] = (int) (Math.random() * max) - (int) (Math.random() * max);
        }
        return origin;
    }

    public static boolean test() {
        int len = 100;
        int max = 1000;
        int testTimes = 5000;
        int addOrUpdateTimes = 1000;
        int queryTimes = 500;
        for (int i = 0; i < testTimes; i++) {
            int[] origin = genarateRandomArray(len, max);
            SegmentTree seg = new SegmentTree(origin);
            int S = 1;
            int N = origin.length;
            int root = 1;
//            seg.build(S, N, root);
            Right rig = new Right(origin);
            for (int j = 0; j < addOrUpdateTimes; j++) {
                int num1 = (int) (Math.random() * N) + 1;
                int num2 = (int) (Math.random() * N) + 1;
                int L = Math.min(num1, num2);
                int R = Math.max(num1, num2);
                int C = (int) (Math.random() * max) - (int) (Math.random() * max);
                if (Math.random() < 0.5) {
                    seg.add(L, R, C);
                    rig.add(L, R, C);
                } else {
                    seg.update(L, R, C);
                    rig.update(L, R, C);
                }
            }
            for (int k = 0; k < queryTimes; k++) {
                int num1 = (int) (Math.random() * N) + 1;
                int num2 = (int) (Math.random() * N) + 1;
                int L = Math.min(num1, num2);
                int R = Math.max(num1, num2);
                long ans1 = seg.query(L, R);
                long ans2 = rig.query(L, R);
                if (ans1 != ans2) {
                    return false;
                }
            }
        }
        return true;
    }
}



