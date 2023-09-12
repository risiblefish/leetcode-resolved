package contest;

import java.util.*;

class No6221 {
    public static void main(String[] args) {
        No6221 test = new No6221();
        /**
         * ["alice","bob","alice","chris"]
         * ["one","two","three","four"]
         * [5,10,5,4]
         */
        String[] creators = new String[]{"alice", "bob", "alice", "chris"};
        String[] ids = new String[]{"one", "two", "three", "four"};
        int[] views = new int[]{5, 10, 5, 4};
        List<List<String>> ans = test.mostPopularCreator(creators, ids, views);
        ans.forEach(System.out::println);
    }

    public List<List<String>> mostPopularCreator(String[] creators, String[] ids, int[] views) {
        int n = creators.length;
        Map<String, Info> map = new HashMap<>();
        for (int i = 0; i < n; i++) {
            String creator = creators[i];
            String id = ids[i];
            int view = views[i];
            Info info = null;
            if (!map.containsKey(creator)) {
                info = new Info(creator, id, view);
                info.id = id;
                info.popular = view;
                map.put(creator, info);
            } else {
                info = map.get(creator);
                info.add(id, view);
            }
        }
        PriorityQueue<Info> q = new PriorityQueue<>();
        for (Info info : map.values()) {
            q.add(info);
        }
        int max = q.peek().popular;
        List<List<String>> ans = new ArrayList<>();
        while (!q.isEmpty() && q.peek().popular == max) {
            Info info = q.poll();
            ans.add(Arrays.asList(info.creator, info.id));
        }
        return ans;
    }

    class Info implements Comparable {
        String creator;
        String id;
        int popular;
        int maxView;

        public Info(String creator, String id, int popular) {
            this.creator = creator;
            this.id = id;
            this.popular = popular;
            this.maxView = popular;
        }

        public void add(String vid, int view) {
            if (view > maxView) {
                this.id = vid;
                maxView = view;
            }
            else if (view == maxView) {
                this.id = this.id.compareTo(vid) < 0 ? this.id : vid;
            }
            popular += view;
        }

        @Override
        public int compareTo(Object obj) {
            Info o = (Info) obj;
            if (this.popular == o.popular) {
                //从大到小
                return o.creator.compareTo(this.creator);
            }
            return o.popular - this.popular;
        }
    }
}