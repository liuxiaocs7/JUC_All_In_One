package threadcoreknowledge.stopthread.volatiledemo;

/**
 * 演示用volatile的局限：part1看似可行
 */
public class WrongWayVolatile implements Runnable {
    private volatile boolean canceled = false;

    @Override
    public void run() {
        int num = 0;
        try {
            // volatile 类型的变量具有可见性，修改后实时可见
            // canceled = true 的时候退出 while 循环
            while (num <= 1000000 && !canceled) {
                if (num % 100 == 0) {
                    System.out.println(num + "是100的倍数。");
                }
                num++;
            }
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        WrongWayVolatile r = new WrongWayVolatile();
        Thread thread = new Thread(r);
        thread.start();
        Thread.sleep(20);
        r.canceled = true;
    }
}
