import java.util.*;

public class Problem5 {

    private static HashMap<String, Integer> pageViews = new HashMap<>();

    private static HashMap<String, Set<String>> uniqueVisitors = new HashMap<>();

    private static HashMap<String, Integer> trafficSources = new HashMap<>();

    public static void processEvent(String url, String userId, String source) {

        pageViews.put(url, pageViews.getOrDefault(url, 0) + 1);

        uniqueVisitors.putIfAbsent(url, new HashSet<>());
        uniqueVisitors.get(url).add(userId);

        trafficSources.put(source,
                trafficSources.getOrDefault(source, 0) + 1);
    }

    public static void getDashboard() {

        List<Map.Entry<String, Integer>> pages =
                new ArrayList<>(pageViews.entrySet());

        pages.sort((a, b) -> b.getValue() - a.getValue());

        System.out.println("Top Pages:");

        int count = 0;

        for (Map.Entry<String, Integer> entry : pages) {

            if (count == 10)
                break;

            String page = entry.getKey();

            System.out.println(
                (count + 1) + ". " +
                page +
                " - " +
                entry.getValue() +
                " views (" +
                uniqueVisitors.get(page).size() +
                " unique)"
            );

            count++;
        }

        System.out.println("\nTraffic Sources:");

        for (String src : trafficSources.keySet()) {

            System.out.println(src +
                " → " + trafficSources.get(src));
        }
    }

    public static void main(String[] args) {

        processEvent("/article/breaking-news","user123","google");
        processEvent("/article/breaking-news","user456","facebook");
        processEvent("/sports/championship","user123","google");
        processEvent("/article/breaking-news","user789","direct");

        getDashboard();
    }
}
