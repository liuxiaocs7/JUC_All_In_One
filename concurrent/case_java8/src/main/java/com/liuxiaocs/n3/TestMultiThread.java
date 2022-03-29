package com.liuxiaocs.n3;

import lombok.extern.slf4j.Slf4j;

/**
 * 测试多个线程并发交替执行
 *
 * CPU不断交替执行这两个线程里面的代码
 * 底层是由多个核心来并行执行或者一个核心交替执行并发处理这个我们是控制不了的，这个是由底层的任务调度器来控制的
 */
@Slf4j(topic = "c.TestMultiThread")
public class TestMultiThread {
    public static void main(String[] args) {
        new Thread(() -> {
            while (true) {
                log.debug("running");
            }
        }, "t1").start();

        new Thread(() -> {
            while (true) {
                log.debug("running");
            }
        }, "t2").start();
    }
}
