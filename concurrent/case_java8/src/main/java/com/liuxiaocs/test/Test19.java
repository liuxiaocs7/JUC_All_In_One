package com.liuxiaocs.test;

import com.liuxiaocs.n2.util.Sleeper;
import lombok.extern.slf4j.Slf4j;

/**
 * 测试sleep和wait
 *
 * Thread.sleep(20000);
 * 00:31:27.368 [t1] DEBUG c.Test19 - 获得锁     -> t1线程获得锁
 * 00:31:47.372 [main] DEBUG c.Test19 - 获得锁   -> 主线程一直等待锁
 *
 * lock.wait();
 * 00:34:14.663 [t1] DEBUG c.Test19 - 获得锁
 * 00:34:15.672 [main] DEBUG c.Test19 - 获得锁   -> 主线程1s之后就能获得锁
 */
@Slf4j(topic = "c.Test19")
public class Test19 {
    static final Object lock = new Object();

    public static void main(String[] args) {
        new Thread(() -> {
            synchronized (lock) {
                log.debug("获得锁");
                try {
                    // 20s内其他线程无法获取锁
                    // Thread.sleep(20000);

                    // 进入休息室等待，释放锁
                    lock.wait(20000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "t1").start();

        Sleeper.sleep(1);
        synchronized (lock) {
            log.debug("获得锁");
        }
    }
}
