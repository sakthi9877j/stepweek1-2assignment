import java.util.*;

public class Problem4 {

    private static HashMap<String, Set<String>> ngramIndex = new HashMap<>();

    private static HashMap<String, List<String>> documentNgrams = new HashMap<>();

    private static int N = 5; // 5-gram

    public static List<String> extractNgrams(String text) {

        List<String> ngrams = new ArrayList<>();
        String[] words = text.toLowerCase().split("\\s+");

        for (int i = 0; i <= words.length - N; i++) {

            StringBuilder gram = new StringBuilder();

            for (int j = 0; j < N; j++)
                gram.append(words[i + j]).append(" ");

            ngrams.add(gram.toString().trim());
        }

        return ngrams;
    }

    public static void addDocument(String docId, String text) {

        List<String> ngrams = extractNgrams(text);
        documentNgrams.put(docId, ngrams);

        for (String gram : ngrams) {

            ngramIndex.putIfAbsent(gram, new HashSet<>());
            ngramIndex.get(gram).add(docId);
        }
    }

    public static void analyzeDocument(String docId) {

        List<String> target = documentNgrams.get(docId);

        HashMap<String, Integer> matchCount = new HashMap<>();

        for (String gram : target) {

            Set<String> docs = ngramIndex.get(gram);

            for (String d : docs) {

                if (!d.equals(docId)) {

                    matchCount.put(d,
                        matchCount.getOrDefault(d, 0) + 1);
                }
            }
        }

        for (String doc : matchCount.keySet()) {

            int matches = matchCount.get(doc);
            double similarity =
                (matches * 100.0) / target.size();

            System.out.println(
                "Matches with " + doc +
                " → " + matches +
                " n-grams (" +
                String.format("%.2f", similarity) + "%)"
            );
        }
    }

    public static void main(String[] args) {

        addDocument("essay_089",
            "machine learning improves search engines using large datasets");

        addDocument("essay_092",
            "machine learning improves search engines using large datasets");

        addDocument("essay_123",
            "machine learning improves search engines using large datasets today");

        analyzeDocument("essay_123");
    }
}
