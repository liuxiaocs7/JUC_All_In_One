package threadcoreknowledge.stopthread;

/**
 * 最佳实践2：在catch子语句中调用Thread.currentThread().interrupt()
 * 来恢复设置中断状态，回到刚才RightWayStopThreadInProd2补上中断，让它跳出
 */
public class RightWayStopThreadInProd2 implements Runnable {

    // 较高层次
    @Override
    public void run() {
        while (true) {
            // 可以获取到reInterrupt()方法中抛出的中断信息
            if (Thread.currentThread().isInterrupted()) {
                System.out.println("Interrupted，程序运行结束");
                break;
            }
            reInterrupt();
        }
    }

    private void reInterrupt() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            // 获取到中断信息并重新抛出来
            // Thread.currentThread().interrupt();
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new RightWayStopThreadInProd2());
        thread.start();
        Thread.sleep(1000);
        thread.interrupt();
    }
}
