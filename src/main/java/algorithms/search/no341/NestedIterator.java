package algorithms.search.no341;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 341. 扁平化嵌套列表迭代器
 *
 * @author Sean Yu
 */

/**
 * // This is the interface that allows for creating nested lists.
 * // You should not implement it, or speculate about its implementation
 * public interface NestedInteger {
 * <p>
 * // @return true if this NestedInteger holds a single integer, rather than a nested list.
 * public boolean isInteger();
 * <p>
 * // @return the single integer that this NestedInteger holds, if it holds a single integer
 * // Return null if this NestedInteger holds a nested list
 * public Integer getInteger();
 * <p>
 * // @return the nested list that this NestedInteger holds, if it holds a nested list
 * // Return null if this NestedInteger holds a single integer
 * public List<NestedInteger> getList();
 * }
 */

/**
 * 思路： dfs
 */
public class NestedIterator implements Iterator<Integer> {
    List<Integer> res = new ArrayList();
    int currIdx = 0;

    public NestedIterator(List<NestedInteger> nestedList) {
        if (nestedList != null) {
            for (int i = 0; i < nestedList.size(); i++) {
                NestedInteger curr = nestedList.get(i);
                if (curr.isInteger()) {
                    res.add(curr.getInteger());
                } else {
                    res.addAll(getInts(curr.getList()));
                }
            }
        }
    }

    private List<Integer> getInts(List<NestedInteger> list) {
        List<Integer> res = new ArrayList();
        if (list == null) {
            return res;
        }
        for (int i = 0; i < list.size(); i++) {
            NestedInteger curr = list.get(i);
            if (curr.isInteger()) {
                res.add(curr.getInteger());
            } else {
                res.addAll(getInts(curr.getList()));
            }
        }
        return res;
    }

    @Override
    public Integer next() {
        if (res.isEmpty() || currIdx == res.size()) {
            return null;
        }
        return res.get(currIdx++);
    }

    @Override
    public boolean hasNext() {
        if (res.isEmpty()) {
            return false;
        }
        return currIdx < res.size();
    }
}

/**
 * Your NestedIterator object will be instantiated and called as such:
 * NestedIterator i = new NestedIterator(nestedList);
 * while (i.hasNext()) v[f()] = i.next();
 */


