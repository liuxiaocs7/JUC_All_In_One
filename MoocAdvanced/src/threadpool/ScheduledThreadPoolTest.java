package threadpool;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduledThreadPoolTest {
    public static void main(String[] args) {
        ScheduledExecutorService threadPool = Executors.newScheduledThreadPool(10);
        // 延迟5s执行
        // threadPool.schedule(new Task(), 5, TimeUnit.SECONDS);
        // 以固定速率执行: 最开始延时1s执行，每隔3s执行一次
        threadPool.scheduleAtFixedRate(new Task(), 1, 3, TimeUnit.SECONDS);

    }
}
