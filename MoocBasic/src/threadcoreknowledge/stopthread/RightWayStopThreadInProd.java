package threadcoreknowledge.stopthread;

/**
 * 最佳实践：catch了InterruptedException之后的优先选择：
 * 在方法签名中抛出异常，那么在run()方法中就会强制try/catch
 */
public class RightWayStopThreadInProd implements Runnable {

    // 较高层次
    @Override
    public void run() {
        while (true && !Thread.currentThread().isInterrupted()) {
            System.out.println("go");
            try {
                throwInMethod();
            } catch (InterruptedException e) {
                // 保存日志、停止程序
                System.out.println("保存日志");
                e.printStackTrace();
            }
        }
    }

    private void throwInMethod() throws InterruptedException {
        Thread.sleep(2000);
    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new RightWayStopThreadInProd());
        thread.start();
        Thread.sleep(1000);
        thread.interrupt();
    }
}
