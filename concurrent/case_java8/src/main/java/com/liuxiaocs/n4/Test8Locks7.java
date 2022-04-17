package com.liuxiaocs.n4;

import lombok.extern.slf4j.Slf4j;

import static com.liuxiaocs.n2.util.Sleeper.sleep;

/**
 * 测试线程8锁
 *
 * 7.类对象为锁对象
 *   结果为 (2 1s后1)
 *
 * 11:53:38.069 [Thread-0] DEBUG c.Test8Locks7 - begin
 * 11:53:38.069 [Thread-1] DEBUG c.Test8Locks7 - begin
 * 11:53:38.071 [Thread-1] DEBUG c.Number7 - 2
 * 11:53:39.080 [Thread-0] DEBUG c.Number7 - 1
 */
@Slf4j(topic = "c.Test8Locks7")
public class Test8Locks7 {
    public static void main(String[] args) {
        Number7 n1 = new Number7();
        Number7 n2 = new Number7();
        new Thread(() -> {
            log.debug("begin");
            n1.a();
        }).start();

        new Thread(() -> {
            log.debug("begin");
            n2.b();
        }).start();
    }
}

@Slf4j(topic = "c.Number7")
class Number7 {
    public static synchronized void a() {
        // 在这里加上了一个sleep()
        // 不释放锁
        sleep(1);
        log.debug("1");
    }

    public synchronized void b() {
        log.debug("2");
    }
}
