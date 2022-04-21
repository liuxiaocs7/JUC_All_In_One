package com.liuxiaocs.n4;

import lombok.extern.slf4j.Slf4j;

import static com.liuxiaocs.n2.util.Sleeper.sleep;

/**
 * wait/notify step1
 *
 * 00:46:10.975 [小南] DEBUG c.TestCorrectPosture - 有烟没？[false]
 * 00:46:10.975 [小南] DEBUG c.TestCorrectPosture - 没烟，先歇会！
 * 00:46:11.980 [送烟的] DEBUG c.TestCorrectPosture - 烟到了噢!
 * 00:46:12.975 [小南] DEBUG c.TestCorrectPosture - 有烟没？[true]
 * 00:46:12.975 [小南] DEBUG c.TestCorrectPosture - 可以开始干活了
 * 00:46:12.975 [其它人] DEBUG c.TestCorrectPosture - 可以开始干活了
 * 00:46:12.975 [其它人] DEBUG c.TestCorrectPosture - 可以开始干活了
 * 00:46:12.975 [其它人] DEBUG c.TestCorrectPosture - 可以开始干活了
 * 00:46:12.975 [其它人] DEBUG c.TestCorrectPosture - 可以开始干活了
 * 00:46:12.975 [其它人] DEBUG c.TestCorrectPosture - 可以开始干活了
 *
 * 存在的问题：
 *      1.现在小南线程会睡2s，在这个过程中有可能hasCigarette已经被修改为true了，这个可以通过中断来解决
 *      2.小南线程在睡眠的时候其他的线程并没有开始干活，因为小南锁住了这个房间并且不会释放对象锁。这样效率较低
 *
 *
 * 送烟的锁住房间：
 * 00:51:53.868 [小南] DEBUG c.TestCorrectPosture - 有烟没？[false]
 * 00:51:53.870 [小南] DEBUG c.TestCorrectPosture - 没烟，先歇会！
 * 00:51:55.875 [小南] DEBUG c.TestCorrectPosture - 有烟没？[false]
 * 00:51:55.875 [送烟的] DEBUG c.TestCorrectPosture - 烟到了噢!
 * 00:51:55.875 [其它人] DEBUG c.TestCorrectPosture - 可以开始干活了
 * 00:51:55.875 [其它人] DEBUG c.TestCorrectPosture - 可以开始干活了
 * 00:51:55.875 [其它人] DEBUG c.TestCorrectPosture - 可以开始干活了
 * 00:51:55.875 [其它人] DEBUG c.TestCorrectPosture - 可以开始干活了
 * 00:51:55.875 [其它人] DEBUG c.TestCorrectPosture - 可以开始干活了
 */
@Slf4j(topic = "c.TestCorrectPosture1")
public class TestCorrectPostureStep1 {
    static final Object room = new Object();
    static boolean hasCigarette = false;  // 有没有烟
    static boolean hasTakeout = false;

    public static void main(String[] args) {
        new Thread(() -> {
            // 成为room的主人，锁住门
            synchronized (room) {
                log.debug("有烟没？[{}]", hasCigarette);
                if(!hasCigarette) {
                    log.debug("没烟，先歇会！");
                    sleep(2);
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
            // 这里能不能加 synchronized (room) ?  这样送烟的也无法进入房间
            synchronized (room) {
                hasCigarette = true;
                log.debug("烟到了噢!");
            }
        }, "送烟的").start();
    }
}
