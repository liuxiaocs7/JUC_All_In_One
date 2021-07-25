package com.liuxiaocs.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// 演示线程池三种常用分类
public class ThreadPoolDemo1 {
    public static void main(String[] args) {
        test3();
    }

    private static void test3() {
        // 一池可扩容线程(使用工具类Executors创建)
        ExecutorService threadPool = Executors.newCachedThreadPool();
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

    private static void test2() {
        // 一池一线程(使用工具类Executors创建)
        ExecutorService threadPool = Executors.newSingleThreadExecutor();
        try {
            // 10个顾客请求
            for (int i = 1; i <= 10; i++) {
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

    private static void test1() {
        // 一池五线程(使用工具类Executors创建)。好比有5个窗口开放了
        ExecutorService threadPool = Executors.newFixedThreadPool(5);
        try {
            // 10个顾客请求
            for (int i = 1; i <= 10; i++) {
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
