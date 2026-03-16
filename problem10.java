import java.util.*;

public class Problem10 {

    static LinkedHashMap<String,String> L1 =
            new LinkedHashMap<String,String>(10000,0.75f,true){

        protected boolean removeEldestEntry(Map.Entry e){
            return size() > 5;
        }
    };

    static HashMap<String,String> L2 = new HashMap<>();

    static HashMap<String,String> L3 = new HashMap<>();

    static {

        L3.put("video_123","VideoData123");
        L3.put("video_999","VideoData999");
    }

    public static String getVideo(String id){

        if(L1.containsKey(id)){

            System.out.println("L1 HIT");
            return L1.get(id);
        }

        if(L2.containsKey(id)){

            System.out.println("L2 HIT → Promoted to L1");

            String data = L2.get(id);
            L1.put(id,data);

            return data;
        }

        if(L3.containsKey(id)){

            System.out.println("L3 Database HIT");

            String data = L3.get(id);

            L2.put(id,data);

            return data;
        }

        return "Video not found";
    }

    public static void main(String[] args){

        System.out.println(getVideo("video_123"));

        System.out.println(getVideo("video_123"));

        System.out.println(getVideo("video_999"));
    }
}
