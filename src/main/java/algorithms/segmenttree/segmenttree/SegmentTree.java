package algorithms.segmenttree.segmenttree;

/**
 * lazyXXX[i]表示节点i是否有积攒的XXX任务
 *
 * @author Sean Yu
 * @create 2022/4/18 08:43
 */
public class SegmentTree {
    private int MAXN;
    private int[] arr;
    private int[] sum;
    private int[] lazyAdd;
    private int[] lazyChange;
    private boolean[] lazyUpdate;

    /**
     * 线段树的表示方法，假设下标从1开始
     * root表示arr[1] ~ arr[n]的和
     * root的左孩子，表示arr[1] ~ arr[n/2]的和
     * root的右孩子，表示arr[n/2]+1 ~ arr[n]的和
     * 以此类推，
     * 叶子节点，则是arr[1] , arr[2], .... , arr[n] 的值
     * <p>
     * 注：
     * 二叉线段树用数组的表示方法：
     * 对于数组下标为i的节点, 其左孩子在数组的下标是2*i, 其右孩子的数组下标是2 * i + 1
     *
     * @param origin
     */
    public SegmentTree(int[] origin) {
        MAXN = origin.length + 1;
        arr = new int[MAXN];
        for (int i = 1; i < MAXN; i++) {
            arr[i] = origin[i - 1];
        }
        sum = new int[MAXN << 2];
        lazyAdd = new int[MAXN << 2];
        lazyChange = new int[MAXN << 2];
        lazyUpdate = new boolean[MAXN << 2];
        build(1, origin.length, 1);
    }

    /**
     * 搜集rootIdx节点的信息
     * 此处是获取左右孩子的和
     *
     * @param rootIdx
     */
    private void collectChildInfo(int rootIdx) {
        sum[rootIdx] = sum[rootIdx << 1] + sum[rootIdx << 1 | 1];
    }

    /**
     * 初始化的时候，需要构建线段树
     *
     * @param l
     * @param r
     * @param rootIdx
     */
    private void build(int l, int r, int rootIdx) {
        if (l == r) {
            sum[rootIdx] = arr[l];
        } else {
            int mid = l + ((r - l) >> 1);
            //构建左子树
            build(l, mid, rootIdx << 1);
            //构建右子树
            build(mid + 1, r, rootIdx << 1 | 1);
            collectChildInfo(rootIdx);
        }
    }

    private long query(int missionL, int missionR, int rootL, int rootR, int rootIdx) {
        if (missionL <= rootL && missionR >= rootR) {
            return sum[rootIdx];
        }
        int mid = rootL + ((rootR - rootL) >> 1);
        spreadLazy(rootIdx, mid - rootL + 1, rootR - mid);
        long res = 0;
        if (missionL <= mid) {
            int lChildIdx = rootIdx << 1;
            res += query(missionL, missionR, rootL, mid, lChildIdx);
        }
        if (missionR > mid) {
            int rChildIdx = rootIdx << 1 | 1;
            res += query(missionL, missionR, mid + 1, rootR, rChildIdx);
        }
        return res;
    }

    public void add(int l, int r, int num){
        add(l, r, num, 1, MAXN, 1);
    }

    public void update(int l, int r, int num){
        update(l, r, num, 1, MAXN, 1);
    }

    public long query(int l, int r){
        return query(l, r, 1, MAXN, 1);
    }

    /**
     * 表示在[missionL, missionR]上所有的数进行+num操作
     * rootIdx表示当前来到的节点坐标，[rootL,rootR] 表示当前节点包含的范围
     *
     * @param missionL
     * @param missionR
     * @param num
     * @param rootIdx
     * @param rootL
     * @param rootR
     */
    private void add(int missionL, int missionR, int num, int rootL, int rootR, int rootIdx) {
        // 如果是missionL <= rootL <= rootR <= missionR, 说明当前节点覆盖的范围需要全部更新
        if (missionL <= rootL && rootR <= missionR) {
            sum[rootIdx] += num * (rootR - rootL + 1);
            lazyAdd[rootIdx] += num;
        }
        //否则说明rootIdx节点覆盖的范围只有一部分需要被更新
        else {
            int mid = rootL + ((rootR - rootL) >> 1);
            //在更新左右子树之前，先将此前积攒的懒任务进行下发
            //比如当前节点代表[1,5],mid=2, 左子树范围为[1,2]共2个数,右子树范围为[3,5]共3个数
            spreadLazy(rootIdx, mid - rootL + 1, rootR - mid);

            //如果任务在左子树里，则对左子树进行add
            if (missionL <= mid) {
                int lChildIdx = rootIdx << 1;
                //左子树包含的范围是[rootL , mid]
                add(missionL, missionR, num, rootL, mid, lChildIdx);
            }

            //如果任务在右子树里，则对右子树进行add
            if (missionR > mid) {
                int rChildIdx = rootIdx << 1 | 1;
                //右子树包含的范围是[mid+1, rootR]
                add(missionL, missionR, num, mid + 1, rootR, rChildIdx);
            }
            collectChildInfo(rootIdx);
        }
    }

    private void update(int missionL, int missionR, int num, int rootL, int rootR, int rootIdx) {
        if (missionL <= rootL && rootR <= missionR) {
            lazyChange[rootIdx] = num;
            lazyUpdate[rootIdx] = true;
            sum[rootIdx] = num * (rootR - rootL + 1);
            lazyAdd[rootIdx] = 0;
        } else {
            int mid = rootL + ((rootR - rootL) >> 1);
            spreadLazy(rootIdx, mid - rootL + 1, rootR - mid);

            if (missionL <= mid) {
                int lChildIdx = rootIdx << 1;
                update(missionL, missionR, num, rootL, mid, lChildIdx);
            }

            if (missionR > mid) {
                int rChildIdx = rootIdx << 1 | 1;
                update(missionL, missionR, num, mid + 1, rootR, rChildIdx);
            }

            collectChildInfo(rootIdx);
        }
    }

    /**
     * 将此前积攒的还未处理的懒任务下发给左右孩子
     *
     * @param rootIdx
     * @param lCount  左孩子的范围里有几个数
     * @param rCount  右孩子的范围里有几个数
     */
    private void spreadLazy(int rootIdx, int lCount, int rCount) {
        int lChildIdx = rootIdx << 1;
        int rChildIdx = rootIdx << 1 | 1;

        if (lazyUpdate[rootIdx]) {
            //更新左子树
            lazyUpdate[lChildIdx] = true;
            lazyChange[lChildIdx] = lazyChange[rootIdx];
            lazyAdd[lChildIdx] = 0;
            sum[lChildIdx] = lCount * lazyChange[rootIdx];
            //更新右子树
            lazyUpdate[rChildIdx] = true;
            lazyChange[rChildIdx] = lazyChange[rootIdx];
            lazyAdd[rChildIdx] = 0;
            sum[rChildIdx] = rCount * lazyChange[rootIdx];

            lazyUpdate[rootIdx] = false;
        }
        if (lazyAdd[rootIdx] != 0) {
            //更新左孩子的和，并且更新左孩子的lazy
            lazyAdd[lChildIdx] += lazyAdd[rootIdx];
            sum[lChildIdx] += lCount * lazyAdd[rootIdx];

            //更新有孩子的和，并且更新右孩子的lazy
            lazyAdd[rChildIdx] += lazyAdd[rootIdx];
            sum[rChildIdx] += rCount * lazyAdd[rootIdx];

            //将懒任务下发给孩子后，自己的懒任务清0
            lazyAdd[rootIdx] = 0;
        }
    }
}
