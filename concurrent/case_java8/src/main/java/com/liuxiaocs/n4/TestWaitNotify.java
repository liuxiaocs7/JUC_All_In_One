package com.liuxiaocs.n4;

import lombok.extern.slf4j.Slf4j;

import static com.liuxiaocs.n2.util.Sleeper.sleep;

/**
 * 测试线程协作
 *
 * notify/notifyAll区别
 *
 * notify:
 * 23:29:51.861 [t1] DEBUG c.TestWaitNotify - 执行...
 * 23:29:51.863 [t2] DEBUG c.TestWaitNotify - 执行...
 * 23:29:53.874 [main] DEBUG c.TestWaitNotify - 唤醒obj上其它线程
 * 23:29:53.874 [t1] DEBUG c.TestWaitNotify - 其它代码....
 *
 * notifyAll:
 * 23:34:08.723 [t1] DEBUG c.TestWaitNotify - 执行...
 * 23:34:08.725 [t2] DEBUG c.TestWaitNotify - 执行...
 * 23:34:10.735 [main] DEBUG c.TestWaitNotify - 唤醒obj上其它线程
 * 23:34:10.735 [t2] DEBUG c.TestWaitNotify - 其它代码....
 * 23:34:10.735 [t1] DEBUG c.TestWaitNotify - 其它代码....
 *
 * 有时限等待：
 * 23:43:42.793 [t1] DEBUG c.TestWaitNotify - 执行...
 * 23:43:42.795 [t2] DEBUG c.TestWaitNotify - 执行...
 * 23:43:43.797 [t1] DEBUG c.TestWaitNotify - 其它代码....
 * 23:43:44.798 [main] DEBUG c.TestWaitNotify - 唤醒obj上其它线程
 * 23:43:44.798 [t2] DEBUG c.TestWaitNotify - 其它代码....
 */
@Slf4j(topic = "c.TestWaitNotify")
public class TestWaitNotify {
    final static Object obj = new Object();

    public static void main(String[] args) {
        new Thread(() -> {
            synchronized (obj) {
                log.debug("执行...");
                try {
                    // 有时限的等待，最多等待1s，如果过了1s还没有人来叫醒我那么就继续向下执行
                    // 在等待时限内可以被唤醒
                    obj.wait(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.debug("其它代码....");
            }
        }, "t1").start();

        new Thread(() -> {
            synchronized (obj) {
                log.debug("执行...");
                try {
                    // 让线程在obj上一直等待下去
                    obj.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.debug("其它代码....");
            }
        }, "t2").start();

        // 主线程两秒后执行
        sleep(2);
        log.debug("唤醒obj上其它线程");
        // 主线程获得锁
        synchronized (obj) {
            // obj.notify();  // 唤醒obj上的一个线程；随机挑选休息室中的一个线程并唤醒
            obj.notifyAll(); // 唤醒obj上的所有等待线程
        }
        // 主线程释放锁之后子线程t1/t2获得锁obj继续执行wait()后面的代码
    }
}
