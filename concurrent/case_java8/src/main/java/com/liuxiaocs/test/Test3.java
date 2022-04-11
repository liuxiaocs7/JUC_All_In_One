package com.liuxiaocs.test;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 使用FutureTask
 *
 * FutureTask 实现了 RunnableFuture<V> 接口，而RunnableFuture<V> 接口同时继承了 Runnable【任务】 接口和 Future<V>【返回值】 接口
 * Runnable和Callable都表示任务，但是Callable可以有返回值和抛出异常
 * 任务执行完了可以将执行结果传递给其他线程
 *
 * FutureTask的泛型是指返回值结果的类型
 * FutureTask的构造方法需要一个Callable类型的参数
 *
 * 运行结果：
 * 00:44:02.891 [t1] DEBUG c.Test2 - running
 * 00:44:04.907 [main] DEBUG c.Test2 - 100
 */
@Slf4j(topic = "c.Test3")
public class Test3 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<Integer> task = new FutureTask<>(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                log.debug("running");
                Thread.sleep(2000);
                return 100;
            }
        });

        Thread t = new Thread(task, "t1");
        t.start();

        // 当主线程运行到这个get()的时候就会一直阻塞等待，等待task执行完成并返回结果
        // task.get();
        // {}表示占位符，每个{}对应后面的一个值
        log.debug("{}", task.get());
    }
}
