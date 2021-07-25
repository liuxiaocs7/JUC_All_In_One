package com.liuxiaocs.completable;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

// 异步调用和同步调用
public class CompletableFutureDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 同步调用(Void表示没有返回值)
        CompletableFuture<Void> completableFuture1 = CompletableFuture.runAsync(() -> {
            System.out.println(Thread.currentThread().getName() + " : CompletableFuture1");
        });
        completableFuture1.get();

        // 异步调用
        CompletableFuture<Integer> completableFuture2 = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + " : CompletableFuture2");
            // 模拟异常
            int i = 10 / 0;
            return 1024;
        });
        // 当完成之后才调用获取结果
        completableFuture2.whenComplete((t, u) -> {
            // t是返回结果
            System.out.println("-------t = " + t);
            // u表示失败，异常信息
            System.out.println("-------u = " + u);
        }).get();
    }
}
