package com.liuxiaocs.test;

import lombok.extern.slf4j.Slf4j;

/**
 * 测试interrupt()方法
 *
 * 打断后会有一个打断标记，表示这个线程是不是被其他地线程所干扰，所打断过
 * 打断标记是一个布尔值，如果打断过，这个值为true，否则取值为false
 *
 * 00:05:31.379 [t1] DEBUG c.Test11 - sleep...
 * 00:05:32.376 [main] DEBUG c.Test11 - interrupt
 * 00:05:32.376 [main] DEBUG c.Test11 - 打断标记: false
 * java.lang.InterruptedException: sleep interrupted
 * 	at java.lang.Thread.sleep(Native Method)
 * 	at com.liuxiaocs.test.Test11.lambda$main$0(Test11.java:18)
 * 	at java.lang.Thread.run(Thread.java:748)
 */
@Slf4j(topic = "c.Test11")
public class Test11 {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            log.debug("sleep...");
            try {
                // 进入阻塞状态
                Thread.sleep(5000);  // sleep, wait，join在被打断之后会将打断标记清空，即重置为false
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t1");

        t1.start();
        Thread.sleep(1000);
        log.debug("interrupt");

        // t1线程被打断之后会抛出InterruptedException异常
        t1.interrupt();
        log.debug("打断标记: {}", t1.isInterrupted());
    }
}
