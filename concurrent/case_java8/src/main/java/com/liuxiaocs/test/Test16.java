package com.liuxiaocs.test;

import lombok.extern.slf4j.Slf4j;

import static com.liuxiaocs.n2.util.Sleeper.sleep;

/**
 * 应用之统筹(烧水泡茶)
 *
 * 使用两个线程实现
 *
 * 22:23:41.408 [小王] DEBUG c.Test16 - 洗茶壶
 * 22:23:41.408 [老王] DEBUG c.Test16 - 洗水壶
 * 22:23:42.422 [老王] DEBUG c.Test16 - 烧开水
 * 22:23:42.422 [小王] DEBUG c.Test16 - 洗茶杯
 * 22:23:44.432 [小王] DEBUG c.Test16 - 拿茶叶
 * 22:23:47.436 [小王] DEBUG c.Test16 - 泡茶
 */
@Slf4j(topic = "c.Test16")
public class Test16 {
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            log.debug("洗水壶");
            sleep(1);
            log.debug("烧开水");
            sleep(5);
        }, "老王");

        Thread t2 = new Thread(() -> {
            log.debug("洗茶壶");
            sleep(1);
            log.debug("洗茶杯");
            sleep(2);
            log.debug("拿茶叶");
            sleep(1);

            // 等到t1线程结束
            try {
                t1.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.debug("泡茶");
        }, "小王");
        t1.start();
        t2.start();
    }
}
