package com.liuxiaocs.pool;

import java.util.concurrent.*;

// 自定义线程池创建
public class ThreadPoolDemo2 {

    public static void main(String[] args) {
        ExecutorService threadPool = new ThreadPoolExecutor(
                2,
                5,
                2L,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(3),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy()
        );
        try {
            // 10个顾客请求
            for (int i = 1; i <= 20; i++) {
                // 执行(参数是一个Runnable)
                threadPool.execute(() -> {
                    System.out.println(Thread.currentThread().getName() + " 办理业务");
                });
            }
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭
            threadPool.shutdown();
        }
    }
}
