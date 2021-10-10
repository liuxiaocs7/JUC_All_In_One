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
 */
@Slf4j(topic = "c2.Test")
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