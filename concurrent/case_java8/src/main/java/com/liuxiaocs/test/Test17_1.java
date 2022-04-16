package com.liuxiaocs.test;

import lombok.extern.slf4j.Slf4j;

/**
 * 使用阻塞的方式解决共享问题
 *
 * 使用synchronized加锁
 *
 * 00:55:06.827 [main] DEBUG c.Test17 - 0
 */
@Slf4j(topic = "c.Test17")
public class Test17_1 {

    static int counter = 0;
    static Object lock = new Object();

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 5000; i++) {
                synchronized (lock) {
                    counter++;
                }
            }
        }, "t1");

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 5000; i++) {
                synchronized (lock) {
                    counter--;
                }
            }
        }, "t2");

        t1.start();
        t2.start();
        t1.join();
        t2.join();
        log.debug("{}", counter);
    }
}
