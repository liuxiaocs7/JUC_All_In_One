package com.liuxiaocs.test;

import lombok.extern.slf4j.Slf4j;

/**
 * 测试打断正常运行的线程
 *
 * t1线程知道其他线程想打断/干扰我，但是并不会影响这个线程的正常运行
 *
 * 实验现象：
 *      1s之后打印了interrupt，但是程序并没有终止!!!
 *      可见线程t1实际上并没有被打断，还在不断地运行
 *      可以通过打断标记来让这个线程停下来
 *
 * 使用interrupt()方法和isInterrupted()检测提供了一种很优雅的停止线程的方法
 * 是否停止线程的决定权在线程自己手上，并且可以做一些善后处理工作，不会因为被强制停掉导致错误
 */
@Slf4j(topic = "c.Test12")
public class Test12 {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            while(true) {
                // 在死循环中检测线程的打断状态，如果被打断了则退出
                boolean interrupted = Thread.currentThread().isInterrupted();
                if (interrupted) {
                    log.debug("被打断了，退出循环");
                    break;
                }
            }
        }, "t1");

        t1.start();

        // 主线程睡眠1s，之后打断t1线程
        Thread.sleep(1000);
        log.debug("interrupt");
        t1.interrupt();
    }
}
