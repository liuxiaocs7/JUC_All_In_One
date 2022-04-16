package com.liuxiaocs.test;

import lombok.extern.slf4j.Slf4j;

/**
 * 测试守护线程
 *
 * 只要还有线程在运行，整个Java进程就不会结束
 */
@Slf4j(topic = "c.Test15")
public class Test15 {
    public static void main(String[] args) throws InterruptedException {

        // t1线程一直运行
        Thread t1 = new Thread(() -> {
            while(true) {
                if(Thread.currentThread().isInterrupted()) {
                    break;
                }
            }
            log.debug("结束");
        }, "t1");
        // 设置t1为守护线程
        t1.setDaemon(true);
        t1.start();

        // 主线程睡眠1s
        Thread.sleep(1000);
        log.debug("结束");
    }
}
