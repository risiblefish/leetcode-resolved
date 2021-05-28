package algorithms.search.no690;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 690. 员工的重要性
 * @author Sean Yu
 */
public class No690 {
}

/*
// Definition for Employee.
class Employee {
    public int id;
    public int importance;
    public List<Integer> subordinates;
};
*/

/**
 * 思路：
 * 先把员工id和员工存入map便于搜索
 * 然后做dfs即可
 */
class Solution690 {
    Map<Integer, Employee> map = new HashMap();
    public int getImportance(List<Employee> employees, int id) {
        for(Employee e : employees){
            map.put(e.id, e);
        }
        Employee curr = map.get(id);
        return curr.importance + count(curr.subordinates);
    }

    private int count(List<Integer> subordinates){
        int sum = 0;
        for(Integer id : subordinates){
            Employee curr = map.get(id);
            sum += curr.importance;
            if(curr.subordinates == null || curr.subordinates.isEmpty()){
                continue;
            }else {
                sum += count(curr.subordinates);
            }
        }
        return sum;
    }
}

class Employee {
    public int id;
    public int importance;
    public List<Integer> subordinates;
};
