package threadcoreknowledge.stopthread;

/**
 * 如果在执行过程中，每次循环都会调用sleep或wait等方法，那么不需要每次迭代都检查是否已中断
 */
public class RightWayStopThreadWithSleepEveryLoop {

    public static void main(String[] args) throws InterruptedException {
        // 在等待期间线程被中断了
        Runnable runnable = () -> {
            int num = 0;
            try {
                // 耗时主要在sleep上，不需要加上isInterrupted判断
                while(num <= 10000) {
                    if(num % 100 == 0) {
                        System.out.println(num + "是100的倍数");
                    }
                    num++;
                    // 线程睡眠10ms，每次循环都阻塞
                    Thread.sleep(10);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
        Thread.sleep(5000);
        // 在sleep的过程中被打断了
        thread.interrupt();
    }
}
