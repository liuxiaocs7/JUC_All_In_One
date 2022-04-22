package com.liuxiaocs.n4;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.LockSupport;

import static com.liuxiaocs.n2.util.Sleeper.sleep;

/**
 * 测试Park和Unpark
 *
 * Park时线程状态是WAIT
 *
 * 子线程1s后调用Park()，主线程2s后调用UnPark()
 * 12:20:25.137 [t1] DEBUG c.TestParkUnpark - start
 * 12:20:26.152 [t1] DEBUG c.TestParkUnpark - park...
 * 12:20:27.149 [main] DEBUG c.TestParkUnpark - unpark...
 * 12:20:27.149 [t1] DEBUG c.TestParkUnpark - resume...
 *
 * 子线程2s后调用Park()，主线程1s后调用UnPark()
 * 12:28:10.083 [t1] DEBUG c.TestParkUnpark - start
 * 12:28:11.093 [main] DEBUG c.TestParkUnpark - unpark...
 * 12:28:12.098 [t1] DEBUG c.TestParkUnpark - park...
 * 12:28:12.098 [t1] DEBUG c.TestParkUnpark - resume...
 */
@Slf4j(topic = "c.TestParkUnpark")
public class TestParkUnpark {
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            log.debug("start");
            sleep(2);
            log.debug("park...");
            LockSupport.park();
            log.debug("resume...");
        }, "t1");
        t1.start();

        sleep(1);
        log.debug("unpark...");
        // 2s后主线程恢复t1线程的运行
        LockSupport.unpark(t1);
    }
}
