package com.liuxiaocs.test;

import lombok.extern.slf4j.Slf4j;

/**
 * 固定顺序运行
 *
 * 使用wait/notify实现
 *
 * 有两种执行情况，但是结果都是先2后1
 *
 * 10:32:05.898 [t2] DEBUG c.Test25 - 2
 * 10:32:05.900 [t1] DEBUG c.Test25 - 1
 */
@Slf4j(topic = "c.Test25")
public class Test25 {
    static final Object lock = new Object();
    // 表示t2是否运行过
    static boolean t2runned = false;

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            synchronized (lock) {
                while (!t2runned) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            log.debug("1");
        }, "t1");

        Thread t2 = new Thread(() -> {
            synchronized (lock) {
                log.debug("2");
                t2runned = true;
                lock.notify();
            }
        }, "t2");

        t1.start();
        t2.start();
    }
}
