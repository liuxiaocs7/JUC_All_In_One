package com.liuxiaocs.n4;

import lombok.extern.slf4j.Slf4j;

import static com.liuxiaocs.n2.util.Sleeper.sleep;

/**
 * wait/notify step2
 *
 * 00:55:59.972 [小南] DEBUG c.TestCorrectPosture - 有烟没？[false]
 * 00:55:59.975 [小南] DEBUG c.TestCorrectPosture - 没烟，先歇会！
 * 00:55:59.975 [其它人] DEBUG c.TestCorrectPosture - 可以开始干活了
 * 00:55:59.975 [其它人] DEBUG c.TestCorrectPosture - 可以开始干活了
 * 00:55:59.975 [其它人] DEBUG c.TestCorrectPosture - 可以开始干活了
 * 00:55:59.975 [其它人] DEBUG c.TestCorrectPosture - 可以开始干活了
 * 00:55:59.976 [其它人] DEBUG c.TestCorrectPosture - 可以开始干活了
 * 00:56:00.979 [送烟的] DEBUG c.TestCorrectPosture - 烟到了噢!
 * 00:56:00.979 [小南] DEBUG c.TestCorrectPosture - 有烟没？[true]
 * 00:56:00.979 [小南] DEBUG c.TestCorrectPosture - 可以开始干活了
 *
 * 小南休息的时候不会影响其他线程的运行。
 * 不会占用锁，可以让其他线程同时运行了。并发效率得到大大提升。
 */
@Slf4j(topic = "c.TestCorrectPosture2")
public class TestCorrectPostureStep2 {
    static final Object room = new Object();
    static boolean hasCigarette = false;  // 有没有烟
    static boolean hasTakeout = false;

    public static void main(String[] args) {
        new Thread(() -> {
            synchronized (room) {
                log.debug("有烟没？[{}]", hasCigarette);
                if(!hasCigarette) {
                    log.debug("没烟，先歇会！");
                    try {
                        // 进入waitSet阻塞
                        room.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                log.debug("有烟没？[{}]", hasCigarette);
                if(hasCigarette) {
                    log.debug("可以开始干活了");
                }
            }
        }, "小南").start();

        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                synchronized (room) {
                    log.debug("可以开始干活了");
                }
            }, "其它人").start();
        }
        sleep(1);
        new Thread(() -> {
            synchronized (room) {
                hasCigarette = true;
                log.debug("烟到了噢!");
                // 唤醒小南线程
                room.notify();
            }
        }, "送烟的").start();
    }
}
