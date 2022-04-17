package com.liuxiaocs.n4;

import lombok.extern.slf4j.Slf4j;

import static com.liuxiaocs.n2.util.Sleeper.sleep;

/**
 * 测试线程8锁
 *
 * 8.类对象为锁对象
 *   结果为 (1s后12) 或者 (2 1s后1)
 *
 * 11:54:38.732 [Thread-1] DEBUG c.Test8Locks8 - begin
 * 11:54:38.732 [Thread-0] DEBUG c.Test8Locks8 - begin
 * 11:54:38.734 [Thread-1] DEBUG c.Number8 - 2
 * 11:54:39.738 [Thread-0] DEBUG c.Number8 - 1
 *
 * 11:55:01.439 [Thread-0] DEBUG c.Test8Locks8 - begin
 * 11:55:01.439 [Thread-1] DEBUG c.Test8Locks8 - begin
 * 11:55:02.452 [Thread-0] DEBUG c.Number8 - 1
 * 11:55:02.452 [Thread-1] DEBUG c.Number8 - 2
 */
@Slf4j(topic = "c.Test8Locks8")
public class Test8Locks8 {
    public static void main(String[] args) {
        Number8 n1 = new Number8();
        Number8 n2 = new Number8();
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

@Slf4j(topic = "c.Number8")
class Number8 {
    public static synchronized void a() {
        // 在这里加上了一个sleep()
        // 不释放锁
        sleep(1);
        log.debug("1");
    }

    public static synchronized void b() {
        log.debug("2");
    }
}
