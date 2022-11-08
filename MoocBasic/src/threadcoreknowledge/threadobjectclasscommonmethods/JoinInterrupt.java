package threadcoreknowledge.threadobjectclasscommonmethods;

/**
 * 演示join期间被中断的效果
 *
 * 等待子线程运行完毕
 * main主线程中断了
 * 子线程已经运行完毕
 * 子线程中断
 */
public class JoinInterrupt {
    public static void main(String[] args) {
        // 获取主线程的引用
        Thread mainThread = Thread.currentThread();

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // 中断主线程，此时主线程已经给子线程让位了
                    mainThread.interrupt();
                    Thread.sleep(5000);
                    System.out.println("Thread1 finished");
                } catch (InterruptedException e) {
                    System.out.println("子线程中断");
                    // e.printStackTrace();
                }
            }
        });
        // 启动子线程
        thread.start();
        System.out.println("等待子线程运行完毕");
        // 子线程来插队了，实际上是主线程被中断，主线程抛出异常
        try {
            thread.join();
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getName() + "主线程中断了");
            // 主线程被中断的时候必须还要中断子线程，否则会出现不一致的情况
            thread.interrupt();
            // e.printStackTrace();
        }
        System.out.println("子线程已经运行完毕");
    }
}
