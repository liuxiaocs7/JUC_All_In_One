package com.liuxiaocs.n4;

import com.liuxiaocs.n2.util.Sleeper;
import lombok.extern.slf4j.Slf4j;

/**
 * 测试多把锁
 *
 * 锁住整个房间：
 * 13:25:02.545 [小南] DEBUG c.BigRoom - study 1小时
 * 13:25:03.562 [小女] DEBUG c.BigRoom - sleeping 2小时
 *
 * 多把锁：
 * 13:26:56.762 [小女] DEBUG c.BigRoom - sleeping 2小时
 * 13:26:56.762 [小南] DEBUG c.BigRoom - study 1小时
 */
@Slf4j(topic = "c.TestMultiLock")
public class TestMultiLock {
    public static void main(String[] args) {
        BigRoom bigRoom = new BigRoom();
        new Thread(() -> {
            bigRoom.study();
        }, "小南").start();

        new Thread(() -> {
            bigRoom.sleep();
        }, "小女").start();
    }

}

@Slf4j(topic = "c.BigRoom")
class BigRoom {

    // 将大房间细分
    private final Object studyRoom = new Object();
    private final Object bedRoom = new Object();

    public void sleep() {
        synchronized (bedRoom) {
            log.debug("sleeping 2小时");
            Sleeper.sleep(2);
        }
    }

    public void study() {
        synchronized (studyRoom) {
            log.debug("study 1小时");
            Sleeper.sleep(1);
        }
    }

}
