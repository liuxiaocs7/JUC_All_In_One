package threadcoreknowledge.threadobjectclasscommonmethods;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 每隔1秒钟输出当前时间，被中断，观察。
 * Thread.sleep()
 * TimeUnit.SECONDS.sleep()
 *
 * Tue Nov 08 22:46:23 CST 2022
 * Tue Nov 08 22:46:24 CST 2022
 * Tue Nov 08 22:46:25 CST 2022
 * Tue Nov 08 22:46:26 CST 2022
 * Tue Nov 08 22:46:27 CST 2022
 * Tue Nov 08 22:46:28 CST 2022
 * Tue Nov 08 22:46:29 CST 2022
 * 我被中断了！
 * Tue Nov 08 22:46:29 CST 2022
 * java.lang.InterruptedException: sleep interrupted
 * 	at java.lang.Thread.sleep(Native Method)
 * 	at java.lang.Thread.sleep(Thread.java:340)
 * 	at java.util.concurrent.TimeUnit.sleep(TimeUnit.java:386)
 * 	at threadcoreknowledge.threadobjectclasscommonmethods.SleepInterrupted.run(SleepInterrupted.java:28)
 * 	at java.lang.Thread.run(Thread.java:748)
 * Tue Nov 08 22:46:30 CST 2022
 * Tue Nov 08 22:46:31 CST 2022
 */
public class SleepInterrupted implements Runnable {

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new SleepInterrupted());
        thread.start();
        Thread.sleep(6500);
        thread.interrupt();
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println(new Date());
            try {
                // 1. 不需要转化时间
                // TimeUnit.HOURS.sleep()
                // 2. 如果传参小于0，忽略处理
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                System.out.println("我被中断了！");
                e.printStackTrace();
            }
        }
    }
}
