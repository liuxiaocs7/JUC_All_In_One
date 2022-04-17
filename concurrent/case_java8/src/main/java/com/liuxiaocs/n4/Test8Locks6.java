package com.liuxiaocs.n4;

import lombok.extern.slf4j.Slf4j;

import static com.liuxiaocs.n2.util.Sleeper.sleep;

/**
 * 测试线程8锁
 *
 * 6.类对象为锁对象
 *   结果为 (1s后12) 或者 (2 1s后1)
 *
 * 11:51:40.438 [Thread-1] DEBUG c.Test8Locks6 - begin
 * 11:51:40.438 [Thread-0] DEBUG c.Test8Locks6 - begin
 * 11:51:40.440 [Thread-1] DEBUG c.Number6 - 2
 * 11:51:41.451 [Thread-0] DEBUG c.Number6 - 1
 *
 * 11:52:08.242 [Thread-0] DEBUG c.Test8Locks6 - begin
 * 11:52:08.242 [Thread-1] DEBUG c.Test8Locks6 - begin
 * 11:52:09.260 [Thread-0] DEBUG c.Number6 - 1
 * 11:52:09.260 [Thread-1] DEBUG c.Number6 - 2
 */
@Slf4j(topic = "c.Test8Locks6")
public class Test8Locks6 {
    public static void main(String[] args) {
        Number6 n1 = new Number6();
        new Thread(() -> {
            log.debug("begin");
            n1.a();
        }).start();

        new Thread(() -> {
            log.debug("begin");
            n1.b();
        }).start();
    }
}

@Slf4j(topic = "c.Number6")
class Number6 {
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
