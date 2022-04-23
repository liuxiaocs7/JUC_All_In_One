package com.liuxiaocs.test;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantLock;

import static com.liuxiaocs.n2.util.Sleeper.sleep;

/**
 * 测试ReentrantLock可打断(被动避免死等，在其他线程中调用这个线程的interrupt()方法)
 *
 * 等待锁的过程中其他线程可以使用interrupt()终止我的等待。
 *
 * 主线程不上锁：
 * 00:29:20.770 [t1] DEBUG c.Test22 - 尝试获取锁
 * 00:29:20.772 [t1] DEBUG c.Test22 - 获取到锁了
 *
 * 主线程上锁：
 * 00:31:36.328 [t1] DEBUG c.Test22_1 - 尝试获取锁
 *
 * 打断t1线程：
 * 00:32:44.906 [t1] DEBUG c.Test22_1 - 尝试获取锁
 * 00:32:45.907 [main] DEBUG c.Test22_1 - 打断t1线程       主线程打断t1线程
 * 00:32:45.908 [t1] DEBUG c.Test22_1 - 没有获得锁，返回
 * java.lang.InterruptedException
 * 	at java.util.concurrent.locks.AbstractQueuedSynchronizer.doAcquireInterruptibly(AbstractQueuedSynchronizer.java:898)
 * 	at java.util.concurrent.locks.AbstractQueuedSynchronizer.acquireInterruptibly(AbstractQueuedSynchronizer.java:1222)
 * 	at java.util.concurrent.locks.ReentrantLock.lockInterruptibly(ReentrantLock.java:335)
 * 	at com.liuxiaocs.test.Test22_1.lambda$main$0(Test22_1.java:32)
 *
 *
 * 	如果使用lock.lock()而不是lock.lockInterruptibly()方法无法被打断
 * 	可以打断，避免死等，防止死锁
 */
@Slf4j(topic = "c.Test22_1")
public class Test22_1 {

    private static ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            try {
                // 如果没有竞争那么此方法就会获取lock对象锁
                // 如果有竞争就进入阻塞队列，可以被其他线程用interrupt方法打断，不要继续向下等了
                log.debug("尝试获取锁");
                lock.lockInterruptibly();
            } catch (InterruptedException e) {
                // 在等待的期间被打印了
                e.printStackTrace();
                log.debug("没有获得锁，返回");
                return;
            }

            try {
                log.debug("获取到锁了");
            } finally {
                lock.unlock();
            }
        }, "t1");

        // 主线程在lock上加锁
        lock.lock();
        t1.start();

        sleep(1);
        log.debug("打断t1线程");
        t1.interrupt();
    }
}
