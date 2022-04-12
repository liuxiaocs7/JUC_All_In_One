package com.liuxiaocs.test;

import lombok.extern.slf4j.Slf4j;

/**
 * 测试线程状态
 *
 * 线程启动前: NEW
 * 线程启动后: RUNNABLE
 * 13:09:04.648 [t1] DEBUG c.Test5 - running...
 */
@Slf4j(topic = "c.Test5")
public class Test5 {
    public static void main(String[] args) {
        Thread t1 = new Thread("t1") {
            @Override
            public void run() {
                log.debug("running...");
            }
        };

        // 打印线程状态信息
        System.out.println("线程启动前: " + t1.getState());
        t1.start();
        // 多次调用会产生IllegalThreadStateException异常
        // t1.start();
        System.out.println("线程启动后: " + t1.getState());
    }
}
