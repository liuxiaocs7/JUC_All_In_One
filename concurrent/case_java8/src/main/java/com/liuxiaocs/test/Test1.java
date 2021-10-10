package com.liuxiaocs.test;

import lombok.extern.slf4j.Slf4j;

/**
 * 测试一：直接使用 Thread() 创建线程
 *
 * 运行结果：
 * 23:26:29.275 [main] DEBUG c.Test1 - running
 * 23:26:29.281 [Thread-0] DEBUG c.Test1 - running
 *
 * 注意：
 * Thread-0和main内部的打印是两个不同的指令序列，在底层上CPU有可能是并行去执行的
 *
 * 改名之后：
 * 23:30:15.193 [main] DEBUG c.Test1 - running
 * 23:30:15.193 [t1] DEBUG c.Test1 - running
 */
@Slf4j(topic = "c.Test1")
public class Test1 {
    public static void main(String[] args) {

        // 新建一个线程t
        Thread t = new Thread() {
            @Override
            public void run() {
                // 子线程执行打印
                log.debug("running");
            }
        };

        // 创建线程时最好指定一个名字
        t.setName("t1");

        // 开启线程t
        t.start();

        // 主线程执行打印
        log.debug("running");
    }
}
