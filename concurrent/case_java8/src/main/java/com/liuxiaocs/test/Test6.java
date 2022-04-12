package com.liuxiaocs.test;

import lombok.extern.slf4j.Slf4j;

/**
 * 测试sleep()
 * 线程状态的转换，由RUNNABLE转换为TIMED_WAITING
 *
 * 13:16:46.987 [main] DEBUG c.Test6 - t1 state: RUNNABLE
 * 13:16:47.491 [main] DEBUG c.Test6 - t1 state: TIMED_WAITING
 */
@Slf4j(topic = "c.Test6")
public class Test6 {
    public static void main(String[] args) {
        Thread t1 = new Thread("t1") {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        t1.start();
        log.debug("t1 state: {}", t1.getState());

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        log.debug("t1 state: {}", t1.getState());
    }
}
