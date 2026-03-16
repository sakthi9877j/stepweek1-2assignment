import java.util.*;

public class Problem2 {

    private static HashMap<String, Integer> inventory = new HashMap<>();

    private static HashMap<String, LinkedHashMap<Integer, Integer>> waitingList = new HashMap<>();

    static {
        inventory.put("IPHONE15_256GB", 100);
        waitingList.put("IPHONE15_256GB", new LinkedHashMap<>());
    }

    public static int checkStock(String productId) {

        return inventory.getOrDefault(productId, 0);
    }

    public static synchronized String purchaseItem(String productId, int userId) {

        int stock = inventory.getOrDefault(productId, 0);

        if (stock > 0) {

            inventory.put(productId, stock - 1);

            return "Success, " + (stock - 1) + " units remaining";
        }

        LinkedHashMap<Integer, Integer> queue = waitingList.get(productId);

        int position = queue.size() + 1;
        queue.put(userId, position);

        return "Added to waiting list, position #" + position;
    }

    public static void main(String[] args) {

        System.out.println("Stock: " + checkStock("IPHONE15_256GB") + " units available");

        System.out.println(purchaseItem("IPHONE15_256GB", 12345));
        System.out.println(purchaseItem("IPHONE15_256GB", 67890));

        inventory.put("IPHONE15_256GB", 0);

        System.out.println(purchaseItem("IPHONE15_256GB", 99999));
    }
}
