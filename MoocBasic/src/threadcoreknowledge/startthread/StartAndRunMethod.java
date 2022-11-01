package threadcoreknowledge.startthread;

/**
 * 对比start和run两种启动线程的方式
 */
public class StartAndRunMethod {
    public static void main(String[] args) {
        Runnable runnable = () -> {
            System.out.println(Thread.currentThread().getName());
        };
        // main
        runnable.run();

        // false
        System.out.println(runnable instanceof Thread);

        // Thread-0
        new Thread(runnable).start();
    }
}
