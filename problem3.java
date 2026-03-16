import java.util.*;

public class Problem3 {

    static class DNSEntry {
        String domain;
        String ipAddress;
        long expiryTime;

        DNSEntry(String domain, String ipAddress, int ttlSeconds) {
            this.domain = domain;
            this.ipAddress = ipAddress;
            this.expiryTime = System.currentTimeMillis() + (ttlSeconds * 1000);
        }

        boolean isExpired() {
            return System.currentTimeMillis() > expiryTime;
        }
    }

    private static LinkedHashMap<String, DNSEntry> cache =
            new LinkedHashMap<String, DNSEntry>(5, 0.75f, true) {
                protected boolean removeEldestEntry(Map.Entry<String, DNSEntry> eldest) {
                    return size() > 5;
                }
            };

    private static int hits = 0;
    private static int misses = 0;

    public static String resolve(String domain) {

        DNSEntry entry = cache.get(domain);

        if (entry != null && !entry.isExpired()) {
            hits++;
            return "Cache HIT → " + entry.ipAddress;
        }

        if (entry != null && entry.isExpired()) {
            cache.remove(domain);
        }

        misses++;

        String newIP = queryUpstreamDNS(domain);

        cache.put(domain, new DNSEntry(domain, newIP, 5));

        return "Cache MISS → Query upstream → " + newIP;
    }

    private static String queryUpstreamDNS(String domain) {

        Random r = new Random();
        return "172.217.14." + r.nextInt(255);
    }

    public static void getCacheStats() {

        int total = hits + misses;
        double hitRate = (total == 0) ? 0 : ((double) hits / total) * 100;

        System.out.println("Cache Hits: " + hits);
        System.out.println("Cache Misses: " + misses);
        System.out.println("Hit Rate: " + String.format("%.2f", hitRate) + "%");
    }

    public static void main(String[] args) throws InterruptedException {

        System.out.println(resolve("google.com"));
        System.out.println(resolve("google.com"));

        Thread.sleep(6000); // wait for TTL expiry

        System.out.println(resolve("google.com"));

        getCacheStats();
    }
}
