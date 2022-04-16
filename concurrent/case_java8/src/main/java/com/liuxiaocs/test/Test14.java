package com.liuxiaocs.test;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.LockSupport;

import static com.liuxiaocs.n2.util.Sleeper.sleep;

/**
 * 测试打断park线程
 *
 * 14:20:39.444 [t1] DEBUG c.Test14 - park...
 * 14:20:40.454 [t1] DEBUG c.Test14 - unpark...
 * 14:20:40.454 [t1] DEBUG c.Test14 - 打断状态: true
 */
@Slf4j(topic = "c.Test14")
public class Test14 {
    public static void main(String[] args) {
        test3();
    }

    private static void test3() {
        Thread t1 = new Thread(() -> {
            log.debug("park...");
            // 停下来
            LockSupport.park();
            log.debug("unpark...");
            // log.debug("打断状态: {}", Thread.currentThread().isInterrupted());
            // 返回并清除打断标记，即返回true，但是随即改为false，park()生效
            log.debug("打断状态: {}", Thread.interrupted());

            // 再次执行park会失效因为此时的打断标记已经为true了
            // 如果需要再次生效，可以手动清除打断标记，使用Thread.interrupted()方法
            LockSupport.park();
            log.debug("unpark...");
        }, "t1");
        t1.start();

        sleep(1);
        // 打断正在park的线程
        t1.interrupt();
    }
}
