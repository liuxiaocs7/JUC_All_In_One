package com.liuxiaocs.n4;

import lombok.extern.slf4j.Slf4j;

import static com.liuxiaocs.n2.util.Sleeper.sleep;

/**
 * wait/notify step3
 *
 * 01:03:00.204 [小南] DEBUG c.TestCorrectPosture3 - 有烟没？[false]
 * 01:03:00.207 [小南] DEBUG c.TestCorrectPosture3 - 没烟，先歇会！
 * 01:03:00.207 [小女] DEBUG c.TestCorrectPosture3 - 外卖送到没？[false]
 * 01:03:00.207 [小女] DEBUG c.TestCorrectPosture3 - 没外卖，先歇会！
 * 01:03:01.213 [送外卖的] DEBUG c.TestCorrectPosture3 - 外卖到了噢!
 * 01:03:01.213 [小南] DEBUG c.TestCorrectPosture3 - 有烟没？[false]
 * 01:03:01.213 [小南] DEBUG c.TestCorrectPosture3 - 没干成活...
 *
 * 错误唤醒了小南线程？  --> 虚假唤醒，即错误唤醒了正在等待的线程
 *
 * room.notifyAll()
 * 01:04:47.217 [小南] DEBUG c.TestCorrectPosture3 - 有烟没？[false]
 * 01:04:47.220 [小南] DEBUG c.TestCorrectPosture3 - 没烟，先歇会！
 * 01:04:47.220 [小女] DEBUG c.TestCorrectPosture3 - 外卖送到没？[false]
 * 01:04:47.220 [小女] DEBUG c.TestCorrectPosture3 - 没外卖，先歇会！
 * 01:04:48.230 [送外卖的] DEBUG c.TestCorrectPosture3 - 外卖到了噢!
 * 01:04:48.230 [小女] DEBUG c.TestCorrectPosture3 - 外卖送到没？[true]
 * 01:04:48.230 [小女] DEBUG c.TestCorrectPosture3 - 可以开始干活了
 * 01:04:48.230 [小南] DEBUG c.TestCorrectPosture3 - 有烟没？[false]
 * 01:04:48.230 [小南] DEBUG c.TestCorrectPosture3 - 没干成活...
 */
@Slf4j(topic = "c.TestCorrectPosture3")
public class TestCorrectPostureStep3 {
    static final Object room = new Object();
    static boolean hasCigarette = false;  // 有没有烟
    static boolean hasTakeout = false;   // 有没有外卖

    public static void main(String[] args) {
        new Thread(() -> {
            synchronized (room) {
                log.debug("有烟没？[{}]", hasCigarette);
                if (!hasCigarette) {
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
                if (!hasTakeout) {
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
