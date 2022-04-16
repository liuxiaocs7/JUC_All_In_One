package com.liuxiaocs.test;

import lombok.extern.slf4j.Slf4j;

/**
 * 测试共享问题
 *
 * 00:36:41.020 [main] DEBUG c.Test17 - 1537
 */
@Slf4j(topic = "c.Test17")
public class Test17 {

    static int counter = 0;

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            // 多线程在访问这个临界区的时候发生了竞态条件
            for (int i = 0; i < 5000; i++) {
                counter++;
            }
        }, "t1");

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 5000; i++) {
                counter--;
            }
        }, "t2");

        t1.start();
        t2.start();
        t1.join();
        t2.join();
        log.debug("{}", counter);
    }
}
