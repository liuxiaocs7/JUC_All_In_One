package threadcoreknowledge.threadobjectclasscommonmethods;

/**
 * 通过讲解join原理，分析出join的代替写法
 */
public class JoinPrinciple {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    System.out.println("Thread0 finished");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        System.out.println("开始等待子线程运行完毕");
        // thread.join();
        // 主线程拿到锁(没有竞争)
        synchronized (thread) {
            // 主线程执行完就进入了休眠状态，直到有人唤醒它
            // 子线程的run方法执行完毕主线程会继续回到这里执行
            // More Detail: https://stackoverflow.com/questions/71247244/understanding-wait-in-thread-java
            thread.wait();
        }
        System.out.println("所有子线程执行完毕");
    }
}
