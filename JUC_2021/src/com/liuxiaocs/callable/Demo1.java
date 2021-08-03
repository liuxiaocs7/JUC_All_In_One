package com.liuxiaocs.callable;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

// 比较两个接口
// 实现Runnable接口
class MyThread1 implements Runnable {
    @Override
    public void run() {

    }
}
// 实现Callable接口
class MyThread2 implements Callable {
    @Override
    public Integer call() throws Exception {
        System.out.println(Thread.currentThread().getName() + " come in callable");
        return 200;
    }
}

public class Demo1 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // Runnable接口创建线程
        new Thread(new MyThread1(), "AA").start();

        // Callable接口，直接这样替换会报错，因为构造中没有
        // new Thread(new MyThread2(), "BB").start();

        // 找一个Runnable的实现类，找一个中间人
        // 找一个类，既和Runnable有关系，又和Callable有关系
        // Runnable接口有实现类FutureTask，FutureTask构造可以传递Callable
        // FutureTask
        FutureTask<Integer> futureTask1 = new FutureTask<>(new MyThread2());

        // Lambda表达式简化
        FutureTask<Integer> futureTask2 = new FutureTask<>(() -> {
            System.out.println(Thread.currentThread().getName() + " come in callable");
            return 1024;
        });

        // 创建一个线程
        new Thread(futureTask2, "lucy").start();
        new Thread(futureTask1, "mary").start();

        // 计算是否完成
        // while(!futureTask2.isDone()) {
        //    System.out.println("wait......");
        // }

        // 调用FutureTask的get方法
        System.out.println(futureTask2.get());
        System.out.println(futureTask1.get());
        // 只会计算1次，第二次直接返回结果(表现为没有输出lucy come in callable)
        System.out.println(futureTask2.get());
        System.out.println(Thread.currentThread().getName() + " come over");

        // FutureTask 原理 未来任务
        /**
         * 1. 老师正在上课，口渴了，去买水不合适，讲课线程继续进行。
         *    单开启一个线程找班上的班长帮我买水
         *    把水买回来，需要时候直接get
         *    在不影响主线程的情况下单开一个线程做一些其他的事情
         *
         * 2. 4个同学，第一个同学 1 + 2 + ... + 5，第二个同学 10 + 11 + ... + 50，
         *           第三个同学 60 + 61 + 62, 第四个同学 100 + 200.
         *           第二个同学计算量比较大，依次到每个同学那，给第二个线程单独开一个线程让它在那算，最后再来进行汇总。
         *           FutureTask单开启线程给2同学计算，先汇总1 3 4，最后等2同学计算完成，统一汇总。
         *
         *  3. 考试，先做会做的题目，最后看不会做的题目
         *
         *  只需要汇总一次，直接返回结果而不需要再次计算了
         */
    }
}
