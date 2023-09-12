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
     *  当前未毕业的珍宝集合
     */
    static Set<String> set;

    static int totalPaidCnt, totalFreeCnt, month, curCnt;


    /**
     * 入口在这里
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

        String msg = String.format("经过%s次模拟，平均毕业需要有偿抽%s次，免费抽%s次, 平均需%s月", n, paid / n, free / n, mon / n);
        System.out.println(msg);
    }

    private int[] simulate() {
        init();
        while (!set.isEmpty()) {
            //有偿抽
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
            //更新掉落
            drop -= freeCnt * 1000;
            //免费抽
            for (int i = 0; i < freeCnt; i++) {
                simulateOneShot();
            }
        }
        return new int[]{totalPaidCnt, totalFreeCnt, month};
    }


    /**
     * 模拟一次抽奖
     */
    private void simulateOneShot() {
        //先随机出颜色
        String color = rollColor();
        //再随机出该颜色下哪一个具体的神器
        String id = rollId(color);
        //再随机出该神器本次获得几个碎片
        int piece = rollPieceCount(color);
        //如果该神器碎片已经溢出，且溢出的部分能拼出1个整件，则更新为相应数量的掉落物
        updateDrop(id, piece);
    }

    /**
     * 更新掉落物
     *
     * @param id
     * @param receivedPiece
     */
    private void updateDrop(String id, int receivedPiece) {
        Treasure t = tMap.get(id);
        t.curPiece += receivedPiece;
        if (t.curPiece >= t.targetPiece) {
            //溢出之后，从set移除
            set.remove(id);
            //计算溢出数量
            t.overflowPiece += t.curPiece - t.targetPiece;
            int cnt = 0;
            int pieceCount = pieceMap.get(t.color);
            if (t.overflowPiece > pieceCount) {
                cnt++;
                t.overflowPiece -= pieceCount;
            }
            //更新该珍宝当前碎片总数
            t.curPiece = t.targetPiece;
            //更新掉落碎片
            drop += cnt * dismantleMap.get(t.color);
        }
    }


    /**
     *  在颜色确定的情况下，有4种可能： 开出3碎， 2碎，1碎， 整件
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

        //每种颜色珍宝数
        countMap = new HashMap<>();
        countMap.put(COLOR_PINK, 6);
        countMap.put(COLOR_ORANGE, 6);
        countMap.put(COLOR_RED, 15);
        countMap.put(COLOR_GREEN, 15);
        countMap.put(COLOR_PURPLE, 15);

        //每种颜色珍宝对应所需碎片
        pieceMap = new HashMap<>();
        pieceMap.put(COLOR_PINK, 50);
        pieceMap.put(COLOR_ORANGE, 50);
        pieceMap.put(COLOR_RED, 40);
        pieceMap.put(COLOR_GREEN, 30);
        pieceMap.put(COLOR_PURPLE, 20);

        //每种颜色分解能换的掉落物数
        dismantleMap = new HashMap<>();
        dismantleMap.put(COLOR_PINK, 21000);
        dismantleMap.put(COLOR_ORANGE, 14000);
        dismantleMap.put(COLOR_RED, 2800);
        dismantleMap.put(COLOR_GREEN, 1500);
        dismantleMap.put(COLOR_PURPLE, 1000);

        //每种颜色单个毕业所需总碎片数
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
     * 珍宝id， 由颜色 + 编号组成，
     * 比如粉色有6种，编号就是0，1，2，3，4，5 , 对应的id 就是 pink0, pink1,...pink5
     */
    String id;

    String color;

    /**
     * 该珍宝毕业的碎片数量
     */
    int targetPiece;

    /**
     * 该珍宝当前的碎片数量
     */
    int curPiece;

    /**
     * 该珍宝当前溢出的碎片数量
     */
    int overflowPiece;

    public Treasure(String id, String color, int targetPiece) {
        this.id = id;
        this.color = color;
        this.targetPiece = targetPiece;
    }
}


