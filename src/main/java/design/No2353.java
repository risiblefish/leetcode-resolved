package design;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * 2353. 设计食物评分系统
 * @author Sean Yu
 * @create 2022/7/24 11:13
 */
public class No2353 {
    public static void main(String[] args) {
        FoodRatings foodRatings = new FoodRatings(
                new String[]{"kimchi", "miso", "sushi", "moussaka", "ramen", "bulgogi"},
                new String[]{"korean", "japanese", "japanese", "greek", "japanese", "korean"},
                new int[]{9, 12, 8, 15, 14, 7});
        System.out.println(foodRatings.highestRated("korean")); // 返回 "kimchi"
        // "kimchi" 是分数最高的韩式料理，评分为 9 。
        System.out.println(foodRatings.highestRated("japanese")); // 返回 "ramen"
        // "ramen" 是分数最高的日式料理，评分为 14 。
        foodRatings.changeRating("sushi", 16); // "sushi" 现在评分变更为 16 。
        System.out.println(foodRatings.highestRated("japanese")); // 返回 "sushi"
        // "sushi" 是分数最高的日式料理，评分为 16 。
        foodRatings.changeRating("ramen", 16); // "ramen" 现在评分变更为 16 。
        System.out.println(foodRatings.highestRated("japanese"));
    }
}

/**
 * 思路：
 * 维护2个集合： 食物名 - 食物对象， 烹饪方式 - 优先队列
 */
class FoodRatings {
    Map<String, Food> foodMap;
    Map<String, PriorityQueue<Food>> idxMap;

    public FoodRatings(String[] foods, String[] cuisines, int[] ratings) {
        foodMap = new HashMap();
        idxMap = new HashMap();
        int n = foods.length;
        for (int i = 0; i < n; i++) {
            String name = foods[i];
            String cuisine = cuisines[i];
            int rating = ratings[i];
            Food f = new Food(name, cuisine, rating);
            foodMap.put(name, f);
            if (!idxMap.containsKey(cuisine)) {
                PriorityQueue<Food> q = new PriorityQueue<>((f1, f2) -> {
                    if (f1.rating == f2.rating) {
                        return f1.name.compareTo(f2.name);
                    }
                    return f2.rating - f1.rating;
                });
                q.add(f);
                idxMap.put(cuisine, q);
            } else {
                idxMap.get(cuisine).add(f);
            }
        }
    }

    public void changeRating(String food, int newRating) {
        Food f = foodMap.get(food);
        f.rating = newRating;
        PriorityQueue<Food> q = idxMap.get(f.cuisine);
        //需要原地移除，并再次加入，才会触发堆的自动调整
        q.remove(f);
        q.add(f);
    }

    public String highestRated(String cuisine) {
        return idxMap.get(cuisine).peek().name;
    }

    class Food {
        String name;
        String cuisine;
        int rating;

        public Food(String n, String c, int r) {
            name = n;
            cuisine = c;
            rating = r;
        }
    }
}

/**
 * Your FoodRatings object will be instantiated and called as such:
 * FoodRatings obj = new FoodRatings(foods, cuisines, ratings);
 * obj.changeRating(food,newRating);
 * String param_2 = obj.highestRated(cuisine);
 */
