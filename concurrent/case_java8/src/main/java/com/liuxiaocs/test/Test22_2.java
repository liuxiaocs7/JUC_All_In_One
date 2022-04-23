package com.liuxiaocs.test;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import static com.liuxiaocs.n2.util.Sleeper.sleep;

/**
 * 测试ReentrantLock锁超时
 *
 * 没有竞争的情况
 * 01:03:47.119 [t1] DEBUG c.Test22_2 - 尝试获得锁
 * 01:03:47.121 [t1] DEBUG c.Test22_2 - 获得到锁
 *
 *
 * 主线程先获得锁
 * 01:04:36.642 [main] DEBUG c.Test22_2 - 获得到锁
 * 01:04:36.644 [t1] DEBUG c.Test22_2 - 尝试获得锁
 * 01:04:36.644 [t1] DEBUG c.Test22_2 - 获取不到锁
 *
 * 防止无限制等待，避免死锁
 *
 *
 * 主线程释放锁：
 * 01:09:57.721 [main] DEBUG c.Test22_2 - 获得到锁
 * 01:09:57.723 [t1] DEBUG c.Test22_2 - 尝试获得锁
 * 01:09:58.727 [main] DEBUG c.Test22_2 - 释放了锁
 * 01:09:58.727 [t1] DEBUG c.Test22_2 - 获得到锁
 *
 */
@Slf4j(topic = "c.Test22_2")
public class Test22_2 {

    private static ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            // 尝试获取锁，如果成功了就可以获取锁
            // 如果失败了不会进入阻塞队列
            log.debug("尝试获得锁");
            try {
                // 如果1s内主线程仍然不释放锁，那么就返回false
                if(!lock.tryLock(2, TimeUnit.SECONDS)) {
                    log.debug("获取不到锁");
                    return;
                }
            } catch (InterruptedException e) {
                // 在tryLock的过程中被打断
                e.printStackTrace();
                log.debug("获取不到锁2");
                return;
            }
            try {
                log.debug("获得到锁");
            } finally {
                lock.unlock();
            }
        }, "t1");

        lock.lock();
        log.debug("获得到锁");
        t1.start();

        // 测试主线程释放锁
        sleep(1);
        lock.unlock();
        log.debug("释放了锁");

        // 测试打断
        // t1.interrupt();
    }
}
