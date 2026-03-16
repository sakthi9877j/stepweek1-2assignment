import java.util.*;

public class Problem1 {

    private static HashMap<String, Integer> usernameMap = new HashMap<>();
    private static HashMap<String, Integer> attemptFrequency = new HashMap<>();

    public static boolean checkAvailability(String username) {

        attemptFrequency.put(username,
                attemptFrequency.getOrDefault(username, 0) + 1);

        return !usernameMap.containsKey(username);
    }

    public static List<String> suggestAlternatives(String username) {

        List<String> suggestions = new ArrayList<>();

        for (int i = 1; i <= 5; i++) {

            String suggestion = username + i;

            if (!usernameMap.containsKey(suggestion)) {
                suggestions.add(suggestion);
            }
        }

        String dotVersion = username.replace("_", ".");

        if (!usernameMap.containsKey(dotVersion)) {
            suggestions.add(dotVersion);
        }

        return suggestions;
    }

    public static String getMostAttempted() {

        String mostAttempted = "";
        int max = 0;

        for (Map.Entry<String, Integer> entry : attemptFrequency.entrySet()) {

            if (entry.getValue() > max) {
                max = entry.getValue();
                mostAttempted = entry.getKey();
            }
        }

        return mostAttempted + " (" + max + " attempts)";
    }

    public static void main(String[] args) {

        usernameMap.put("john_doe", 101);
        usernameMap.put("admin", 102);

        System.out.println(checkAvailability("john_doe"));
        System.out.println(checkAvailability("jane_smith"));

        System.out.println(suggestAlternatives("john_doe"));

        System.out.println(getMostAttempted());
    }
}
