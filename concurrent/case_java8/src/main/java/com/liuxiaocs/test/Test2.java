package com.liuxiaocs.test;

import lombok.extern.slf4j.Slf4j;

/**
 * 使用 Runnable 配合 Thread
 * 任务和线程分开了
 * <p>
 * 运行结果：
 * 23:35:24.452 [t2] DEBUG c2.Test - running
 * <p>
 * 注意Runnable接口中只有一个抽象方法run(),在JDK中将只有一个抽象方法的接口加上 @FunctionalInterface 注解表示可以使用lambda表达式来进行简化
 * 如果一个接口中有多个抽象方法是无法使用lambda来进行简化的
 * <p>
 * 简化方法：复制小括号，写死右箭头，落地大括号
 *
 * 两种不同方式的比较：
 * 1.第一种方式就相当于重写了Thread类中的方法，子类中自然执行的是子类中的run方法 (多态)
 * 2.第二种方式，将传进去的runnable对象赋值给this.target，进而执行Thread中的run方法，实际执行的是target的run方法 (静态代理)
 *     @Override
 *     public void run() {
 *         if (target != null) {
 *             target.run();
 *         }
 *     }
 */
@Slf4j(topic = "c2.Test2")
public class Test2 {
    public static void main(String[] args) {

        // 任务
        // Runnable r = new Runnable() {
        //     @Override
        //     public void run() {
        //         log.debug("running");
        //     }
        // };

        // 简化写法:
        Runnable r = () -> {
            log.debug("running");
        };

        // 线程
        // 使用runnable对象和Thread构建一个线程，线程名为t2
        Thread t = new Thread(r, "t2");
        t.start();

        // 一行搞定的写法
        new Thread(() -> { log.debug("running"); }, "t3").start();
    }
}