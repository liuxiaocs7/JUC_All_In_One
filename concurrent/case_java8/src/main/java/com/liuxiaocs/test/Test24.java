package com.liuxiaocs.test;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import static com.liuxiaocs.n2.util.Sleeper.sleep;

/**
 * ReentrantLock条件变量
 */
@Slf4j(topic = "c.Test24")
public class Test24 {
    static final Object room = new Object();
    static boolean hasCigarette = false;  // 有没有烟
    static boolean hasTakeout = false;   // 有没有外卖
    static ReentrantLock ROOM = new ReentrantLock();
    // 等待烟的休息室
    static Condition waitCigarette = ROOM.newCondition();
    // 等外卖的休息室
    static Condition waitTakeout = ROOM.newCondition();

    public static void main(String[] args) {
        new Thread(() -> {
            ROOM.lock();
            try {
                log.debug("有烟没？[{}]", hasCigarette);
                // 即使唤醒之后也要重新检查，条件不满足继续进入waitSet阻塞
                while (!hasCigarette) {
                    log.debug("没烟，先歇会！");
                    try {
                        // 进入waitCigarette休息室等待，阻塞
                        waitCigarette.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                log.debug("可以开始干活了");
            } finally {
                ROOM.unlock();
            }
        }, "小南").start();

        new Thread(() -> {
            ROOM.lock();
            try {
                log.debug("外卖送到没？[{}]", hasTakeout);
                while (!hasTakeout) {
                    log.debug("没外卖，先歇会！");
                    try {
                        // 进入waitTakeout阻塞
                        waitTakeout.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                log.debug("可以开始干活了");
            } finally {
                ROOM.unlock();
            }
        }, "小女").start();

        sleep(1);
        new Thread(() -> {
            ROOM.lock();
            try {
                hasTakeout = true;
                log.debug("外卖到了噢!");
                waitTakeout.signal();
            } finally {
                ROOM.unlock();
            }
        }, "送外卖的").start();

        sleep(1);
        new Thread(() -> {
            ROOM.lock();
            try {
                hasCigarette = true;
                log.debug("烟到了噢!");
                waitCigarette.signal();
            } finally {
                ROOM.unlock();
            }
        }, "送烟的").start();
    }
}
