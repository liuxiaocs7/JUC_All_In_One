package com.liuxiaocs.test;

import lombok.extern.slf4j.Slf4j;

/**
 * 测试sleep()
 * 打断睡眠线程
 *
 * 13:22:19.315 [t1] DEBUG c.Test7 - enter sleep...
 * 13:22:20.319 [main] DEBUG c.Test7 - interrupt...
 * 13:22:20.319 [t1] DEBUG c.Test7 - wake up...
 * java.lang.InterruptedException: sleep interrupted
 * 	at java.lang.Thread.sleep(Native Method)
 * 	at com.liuxiaocs.test.Test7$1.run(Test7.java:20)
 */
@Slf4j(topic = "c.Test7")
public class Test7 {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread("t1") {
            @Override
            public void run() {
                log.debug("enter sleep...");
                try {
                    // t1线程睡眠2s
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    log.debug("wake up...");
                    e.printStackTrace();
                }
            }
        };

        t1.start();

        // 主线程睡眠1s之后唤醒t1线程
        Thread.sleep(1000);
        log.debug("interrupt...");
        // 打断线程t1(叫醒)
        t1.interrupt();
    }
}
