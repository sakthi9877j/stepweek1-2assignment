import java.util.*;

public class Problem6 {

    static class TokenBucket {

        int tokens;
        int maxTokens;
        long lastRefillTime;

        public TokenBucket(int maxTokens) {
            this.tokens = maxTokens;
            this.maxTokens = maxTokens;
            this.lastRefillTime = System.currentTimeMillis();
        }

        public void refill() {

            long now = System.currentTimeMillis();
            long diff = now - lastRefillTime;

            if (diff >= 3600000) { // 1 hour
                tokens = maxTokens;
                lastRefillTime = now;
            }
        }
    }

    private static HashMap<String, TokenBucket> clients = new HashMap<>();
    private static final int LIMIT = 1000;

    public static synchronized String checkRateLimit(String clientId) {

        clients.putIfAbsent(clientId, new TokenBucket(LIMIT));

        TokenBucket bucket = clients.get(clientId);

        bucket.refill();

        if (bucket.tokens > 0) {

            bucket.tokens--;

            return "Allowed (" + bucket.tokens + " requests remaining)";
        }

        return "Denied (0 requests remaining, retry later)";
    }

    public static void getRateLimitStatus(String clientId) {

        TokenBucket bucket = clients.get(clientId);

        if (bucket == null) {
            System.out.println("Client not found");
            return;
        }

        int used = bucket.maxTokens - bucket.tokens;

        System.out.println("{used: " + used +
                ", limit: " + bucket.maxTokens +
                ", remaining: " + bucket.tokens + "}");
    }

    public static void main(String[] args) {

        System.out.println(checkRateLimit("abc123"));
        System.out.println(checkRateLimit("abc123"));
        System.out.println(checkRateLimit("abc123"));

        getRateLimitStatus("abc123");
    }
}p
