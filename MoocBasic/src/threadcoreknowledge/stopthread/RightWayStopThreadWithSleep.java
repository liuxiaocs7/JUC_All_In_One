package threadcoreknowledge.stopthread;

/**
 * 带有sleep的中断线程的写法
 */
public class RightWayStopThreadWithSleep {

    public static void main(String[] args) throws InterruptedException {
        // 在等待期间线程被中断了
        Runnable runnable = () -> {
            int num = 0;
            try {
                // 循环可能一直在转，这里需要加上isInterrupted判断
                while(num <= 300 && !Thread.currentThread().isInterrupted()) {
                    if(num % 100 == 0) {
                        System.out.println(num + "是100的倍数");
                    }
                    num++;
                }
                // 线程睡眠1s
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
        Thread.sleep(500);
        // 在sleep的过程中被打断了
        thread.interrupt();
    }
}
