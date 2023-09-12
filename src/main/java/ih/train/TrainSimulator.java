package ih.train;

import java.util.*;

/**
 * @author Sean
 */
public class TrainSimulator {
    final static Random RAND = new Random();
    final static String COLOR_PINK = "pink";
    final static String COLOR_ORANGE = "orange";
    final static String COLOR_RED = "red";
    final static String COLOR_GREEN = "green";
    final static String COLOR_PURPLE = "purple";

    static Map<String, Integer> countMap;

    static Map<String, Treasure> tMap;

    static Map<String, Integer> targetMap;
    static Map<String, Integer> pieceMap;

    static Map<String, Integer> dismantleMap;

    static int drop;

    /**
     *  ��ǰδ��ҵ���䱦����
     */
    static Set<String> set;

    static int totalPaidCnt, totalFreeCnt, month, curCnt;


    /**
     * ���������
     * @param args
     */
    public static void main(String[] args) {
        TrainSimulator sim = new TrainSimulator();
        int n = 10000;
        long paid = 0, free = 0, mon = 0;

        for (int i = 0; i < n; i++) {
            int[] res = sim.simulate();
            paid += res[0];
            free += res[1];
            mon += res[2];
        }

        String msg = String.format("����%s��ģ�⣬ƽ����ҵ��Ҫ�г���%s�Σ���ѳ�%s��, ƽ����%s��", n, paid / n, free / n, mon / n);
        System.out.println(msg);
    }

    private int[] simulate() {
        init();
        while (!set.isEmpty()) {
            //�г���
            simulateOneShot();
            totalPaidCnt++;
            curCnt++;
            if (curCnt == 715) {
                month++;
                drop += 4000;
                curCnt = 0;
            }
            int freeCnt = drop / 1000;
            totalFreeCnt += freeCnt;
            //���µ���
            drop -= freeCnt * 1000;
            //��ѳ�
            for (int i = 0; i < freeCnt; i++) {
                simulateOneShot();
            }
        }
        return new int[]{totalPaidCnt, totalFreeCnt, month};
    }


    /**
     * ģ��һ�γ齱
     */
    private void simulateOneShot() {
        //���������ɫ
        String color = rollColor();
        //�����������ɫ����һ�����������
        String id = rollId(color);
        //����������������λ�ü�����Ƭ
        int piece = rollPieceCount(color);
        //�����������Ƭ�Ѿ������������Ĳ�����ƴ��1�������������Ϊ��Ӧ�����ĵ�����
        updateDrop(id, piece);
    }

    /**
     * ���µ�����
     *
     * @param id
     * @param receivedPiece
     */
    private void updateDrop(String id, int receivedPiece) {
        Treasure t = tMap.get(id);
        t.curPiece += receivedPiece;
        if (t.curPiece >= t.targetPiece) {
            //���֮�󣬴�set�Ƴ�
            set.remove(id);
            //�����������
            t.overflowPiece += t.curPiece - t.targetPiece;
            int cnt = 0;
            int pieceCount = pieceMap.get(t.color);
            if (t.overflowPiece > pieceCount) {
                cnt++;
                t.overflowPiece -= pieceCount;
            }
            //���¸��䱦��ǰ��Ƭ����
            t.curPiece = t.targetPiece;
            //���µ�����Ƭ
            drop += cnt * dismantleMap.get(t.color);
        }
    }


    /**
     *  ����ɫȷ��������£���4�ֿ��ܣ� ����3�飬 2�飬1�飬 ����
     */
    private int rollPieceCount(String color) {
        switch (RAND.nextInt(4)) {
            case 0:
                return 3;
            case 1:
                return 2;
            case 2:
                return 1;
            default:
                return pieceMap.get(color);
        }
    }


    private void init() {
        totalPaidCnt = totalFreeCnt = month = drop = 0;
        curCnt = 1;

        //ÿ����ɫ�䱦��
        countMap = new HashMap<>();
        countMap.put(COLOR_PINK, 6);
        countMap.put(COLOR_ORANGE, 6);
        countMap.put(COLOR_RED, 15);
        countMap.put(COLOR_GREEN, 15);
        countMap.put(COLOR_PURPLE, 15);

        //ÿ����ɫ�䱦��Ӧ������Ƭ
        pieceMap = new HashMap<>();
        pieceMap.put(COLOR_PINK, 50);
        pieceMap.put(COLOR_ORANGE, 50);
        pieceMap.put(COLOR_RED, 40);
        pieceMap.put(COLOR_GREEN, 30);
        pieceMap.put(COLOR_PURPLE, 20);

        //ÿ����ɫ�ֽ��ܻ��ĵ�������
        dismantleMap = new HashMap<>();
        dismantleMap.put(COLOR_PINK, 21000);
        dismantleMap.put(COLOR_ORANGE, 14000);
        dismantleMap.put(COLOR_RED, 2800);
        dismantleMap.put(COLOR_GREEN, 1500);
        dismantleMap.put(COLOR_PURPLE, 1000);

        //ÿ����ɫ������ҵ��������Ƭ��
        targetMap = new HashMap<>();
        targetMap.put(COLOR_PINK, 7 * pieceMap.get(COLOR_PINK));
        targetMap.put(COLOR_ORANGE, 15 * pieceMap.get(COLOR_ORANGE));
        targetMap.put(COLOR_RED, 15 * pieceMap.get(COLOR_RED));
        targetMap.put(COLOR_GREEN, 15 * pieceMap.get(COLOR_GREEN));
        targetMap.put(COLOR_PURPLE, 15 * pieceMap.get(COLOR_PURPLE));

        tMap = new TreeMap<>();
        set = new HashSet<>();
        for (Map.Entry<String, Integer> entry : countMap.entrySet()) {
            init(entry.getValue(), entry.getKey());
        }
    }

    private void init(int times, String color) {
        for (int i = 0; i < times; i++) {
            String id = color + i;
            tMap.put(id, new Treasure(id, color, targetMap.get(color)));
            set.add(id);
        }
    }

    private String rollId(String color) {
        return color + RAND.nextInt(countMap.get(color));
    }

    private String rollColor() {
        int r = RAND.nextInt(1000);
        if (r < 48) {
            return COLOR_PINK;
        } else if (r < 120) {
            return COLOR_ORANGE;
        } else if (r < 280) {
            return COLOR_RED;
        } else if (r < 520) {
            return COLOR_GREEN;
        } else {
            return COLOR_PURPLE;
        }
    }
}

class Treasure {
    /**
     * �䱦id�� ����ɫ + �����ɣ�
     * �����ɫ��6�֣���ž���0��1��2��3��4��5 , ��Ӧ��id ���� pink0, pink1,...pink5
     */
    String id;

    String color;

    /**
     * ���䱦��ҵ����Ƭ����
     */
    int targetPiece;

    /**
     * ���䱦��ǰ����Ƭ����
     */
    int curPiece;

    /**
     * ���䱦��ǰ�������Ƭ����
     */
    int overflowPiece;

    public Treasure(String id, String color, int targetPiece) {
        this.id = id;
        this.color = color;
        this.targetPiece = targetPiece;
    }
}


