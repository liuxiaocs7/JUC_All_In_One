package com.liuxiaocs.n3;

import lombok.extern.slf4j.Slf4j;

/**
 * 测试线程状态
 *
 * 22:10:35.875 [t3] DEBUG c.TestState - running...
 * 22:10:36.375 [main] DEBUG c.TestState - t1 state NEW
 * 22:10:36.376 [main] DEBUG c.TestState - t2 state RUNNABLE
 * 22:10:36.376 [main] DEBUG c.TestState - t3 state TERMINATED
 * 22:10:36.376 [main] DEBUG c.TestState - t4 state TIMED_WAITING
 * 22:10:36.376 [main] DEBUG c.TestState - t5 state WAITING
 * 22:10:36.376 [main] DEBUG c.TestState - t6 state BLOCKED
 */
@Slf4j(topic = "c.TestState")
public class TestState {
    public static void main(String[] args) {
        // 线程t1是就绪状态，也即NEW
        Thread t1 = new Thread("t1") {
            @Override
            public void run() {
                log.debug("running...");
            }
        };

        // 线程t2是运行状态，也即RUNNING
        Thread t2 = new Thread("t2") {
            @Override
            public void run() {
                while(true) {  // RUNNABLE 既可能分到了时间片，也可能没有分到时间片，还有可能遇到了操作系统的IO阻塞

                }
            }
        };
        t2.start();

        // 线程t3是结束状态，也即TERMINATED
        Thread t3 = new Thread("t3") {
            @Override
            public void run() {
                log.debug("running...");
            }
        };
        t3.start();

        // 线程t4一直在睡着，应该是阻塞状态，Java中表示为TIMED_WAITING状态
        // 表示这是一个有时限的等待
        Thread t4 = new Thread("t4") {
            @Override
            public void run() {
                synchronized (TestState.class) {
                    try {
                        Thread.sleep(1000000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        t4.start();

        // 线程t5要等待线程t2运行结束，因为t2是一个死循环，因此t2会一直等待，Java中表示为WAITING，不会被任务调度器调度
        Thread t5 = new Thread("t5") {
            @Override
            public void run() {
                try {
                    // 线程t2来插队
                    t2.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        t5.start();

        // 线程t6拿不到锁，因此会陷入BLOCKED状态
        Thread t6 = new Thread("t6") {
            @Override
            public void run() {
                // 由于t4已经将这个对象锁起来了，因此这里拿不到锁，
                synchronized (TestState.class) {
                    try {
                        Thread.sleep(1000000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        t6.start();

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.debug("t1 state {}", t1.getState());
        log.debug("t2 state {}", t2.getState());
        log.debug("t3 state {}", t3.getState());
        log.debug("t4 state {}", t4.getState());
        log.debug("t5 state {}", t5.getState());
        log.debug("t6 state {}", t6.getState());
    }
}
