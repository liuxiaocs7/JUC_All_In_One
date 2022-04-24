package com.liuxiaocs.test;

import lombok.extern.slf4j.Slf4j;

import static com.liuxiaocs.n2.util.Sleeper.sleep;

/**
 * 测试可见性
 *
 * 1.volatile     推荐使用
 * 2.synchronized
 */
@Slf4j(topic = "c.Test32")
public class Test32 {
    // 保证共享变量在多个线程之间的可见性
    // static volatile boolean run = true;
    // 测试synchronized
    static boolean run = true;

    // 锁对象，加锁也可以保证可见性
    final static Object lock = new Object();

    public static void main(String[] args) {
        // test1();

        Thread t = new Thread(() -> {
            while(true) {
                // ...
                synchronized (lock) {
                    if(!run) {
                        break;
                    }
                }
            }
        });
        t.start();

        sleep(1);
        log.debug("停止 t");
        synchronized (lock) {
            run = false;  // 线程t不会如预想的停下来
        }
    }

    /**
     * 使用volatile关键字 -> 可见性
     */
    private static void test1() {
        Thread t = new Thread(() -> {
            while(run) {
                //...
            }
        });
        t.start();

        sleep(1);
        log.debug("停止t");
        run = false;  // 线程t不会如预想的停下来
    }
}
