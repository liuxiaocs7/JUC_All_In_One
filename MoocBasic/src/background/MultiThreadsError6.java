package background;

import java.util.HashMap;
import java.util.Map;

/**
 * Exception in thread "main" java.lang.NullPointerException
 * 	at background.MultiThreadsError6.main(MultiThreadsError6.java:28)
 */
public class MultiThreadsError6 {
    private Map<String, String> states;

    public MultiThreadsError6() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                states = new HashMap<>();
                states.put("1", "周一");
                states.put("2", "周二");
                states.put("3", "周三");
                states.put("4", "周四");
            }
        }).start();
    }

    public Map<String, String> getStates() {
        return states;
    }

    public static void main(String[] args) {
        MultiThreadsError6 multiThreadsError6 = new MultiThreadsError6();
        System.out.println(multiThreadsError6.getStates().get("1"));
    }
}
