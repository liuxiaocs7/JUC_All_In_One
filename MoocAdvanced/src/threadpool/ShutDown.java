package threadpool;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 延时关闭线程池
 */
public class ShutDown {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 100; i++) {
            executorService.execute(new ShutDownTask());
        }
        Thread.sleep(1500);
        List<Runnable> runnableList = executorService.shutdownNow();
    }

    private static void method2() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 100; i++) {
            executorService.execute(new ShutDownTask());
        }
        Thread.sleep(1500);
        executorService.shutdown();
        // 已经过去了3秒，但是发现并没有完全终止  --->  false
        // boolean b = executorService.awaitTermination(3L, TimeUnit.SECONDS);
        // 7秒之内可以运行完毕，返回true
        // 这个方法是阻塞的
        boolean b = executorService.awaitTermination(7L, TimeUnit.SECONDS);
        System.out.println("b = " + b);
    }

    private static void method1() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 100; i++) {
            executorService.execute(new ShutDownTask());
        }
        Thread.sleep(1500);
        System.out.println(executorService.isShutdown());  // false
        // 不会立刻停止，而是会将存量的任务执行完毕
        executorService.shutdown();
        System.out.println(executorService.isShutdown());  // true
        System.out.println(executorService.isTerminated());  // false 还有很多线程没有执行完
        Thread.sleep(10000);
        System.out.println(executorService.isTerminated());  // true 所有任务执行完毕
        // 已经停止了新任务无法提交
        // executorService.execute(new ShutDownTask());
    }
}

class ShutDownTask implements Runnable {
    @Override
    public void run() {
        try {
            Thread.sleep(500);
            System.out.println(Thread.currentThread().getName());
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getName() + "被中断了");
            // e.printStackTrace();
        }
    }
}
