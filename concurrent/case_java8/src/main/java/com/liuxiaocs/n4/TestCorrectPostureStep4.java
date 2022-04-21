package com.liuxiaocs.n4;

import lombok.extern.slf4j.Slf4j;

import static com.liuxiaocs.n2.util.Sleeper.sleep;

/**
 * wait/notify step4
 *
 * 01:07:49.566 [小南] DEBUG c.TestCorrectPosture4 - 有烟没？[false]
 * 01:07:49.569 [小南] DEBUG c.TestCorrectPosture4 - 没烟，先歇会！
 * 01:07:49.569 [小女] DEBUG c.TestCorrectPosture4 - 外卖送到没？[false]
 * 01:07:49.569 [小女] DEBUG c.TestCorrectPosture4 - 没外卖，先歇会！
 * 01:07:50.569 [送外卖的] DEBUG c.TestCorrectPosture4 - 外卖到了噢!
 * 01:07:50.569 [小女] DEBUG c.TestCorrectPosture4 - 外卖送到没？[true]
 * 01:07:50.569 [小女] DEBUG c.TestCorrectPosture4 - 可以开始干活了
 * 01:07:50.570 [小南] DEBUG c.TestCorrectPosture4 - 没烟，先歇会！
 */
@Slf4j(topic = "c.TestCorrectPosture4")
public class TestCorrectPostureStep4 {
    static final Object room = new Object();
    static boolean hasCigarette = false;  // 有没有烟
    static boolean hasTakeout = false;   // 有没有外卖

    public static void main(String[] args) {
        new Thread(() -> {
            synchronized (room) {
                log.debug("有烟没？[{}]", hasCigarette);
                // 即使唤醒之后也要重新检查，条件不满足继续进入waitSet阻塞
                while (!hasCigarette) {
                    log.debug("没烟，先歇会！");
                    try {
                        // 进入waitSet阻塞
                        room.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                log.debug("有烟没？[{}]", hasCigarette);
                if (hasCigarette) {
                    log.debug("可以开始干活了");
                } else {
                    log.debug("没干成活...");
                }
            }
        }, "小南").start();

        new Thread(() -> {
            synchronized (room) {
                Thread thread = Thread.currentThread();
                log.debug("外卖送到没？[{}]", hasTakeout);
                while (!hasTakeout) {
                    log.debug("没外卖，先歇会！");
                    try {
                        // 进入waitSet阻塞
                        room.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                log.debug("外卖送到没？[{}]", hasTakeout);
                if (hasTakeout) {
                    log.debug("可以开始干活了");
                } else {
                    log.debug("没干成活...");
                }
            }
        }, "小女").start();

        sleep(1);
        new Thread(() -> {
            synchronized (room) {
                hasTakeout = true;
                log.debug("外卖到了噢!");
                // 随机挑选一个线程唤醒
                // room.notify();

                room.notifyAll();
            }
        }, "送外卖的").start();
    }
}
