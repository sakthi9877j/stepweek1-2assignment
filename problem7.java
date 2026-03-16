import java.util.*;

public class Problem7 {

    private static HashMap<String, Integer> queryFreq = new HashMap<>();

    public static void addQuery(String query) {

        queryFreq.put(query,
                queryFreq.getOrDefault(query, 0) + 1);
    }

    public static void search(String prefix) {

        PriorityQueue<Map.Entry<String, Integer>> pq =
                new PriorityQueue<>((a, b) ->
                        b.getValue() - a.getValue());

        for (Map.Entry<String, Integer> entry : queryFreq.entrySet()) {

            if (entry.getKey().startsWith(prefix)) {
                pq.add(entry);
            }
        }

        System.out.println("Suggestions:");

        int count = 0;

        while (!pq.isEmpty() && count < 10) {

            Map.Entry<String, Integer> e = pq.poll();

            System.out.println(
                    (count + 1) + ". " +
                    e.getKey() +
                    " (" + e.getValue() + " searches)"
            );

            count++;
        }
    }

    public static void main(String[] args) {

        addQuery("java tutorial");
        addQuery("javascript");
        addQuery("java download");
        addQuery("java tutorial");
        addQuery("java 21 features");

        search("jav");

        addQuery("java 21 features");

        search("java");
    }
}
