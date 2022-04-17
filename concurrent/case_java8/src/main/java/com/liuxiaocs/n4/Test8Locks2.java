package com.liuxiaocs.n4;

import lombok.extern.slf4j.Slf4j;

import static com.liuxiaocs.n2.util.Sleeper.sleep;

/**
 * 测试线程8锁
 *
 * 2.this为锁对象
 *   结果为 (1s后12) 或者 (2 1s后1)
 *
 * 01:36:09.731 [Thread-0] DEBUG c.Test8Locks2 - begin
 * 01:36:09.731 [Thread-1] DEBUG c.Test8Locks2 - begin
 * 01:36:10.733 [Thread-0] DEBUG c.Number2 - 1
 * 01:36:10.733 [Thread-1] DEBUG c.Number2 - 2
 *
 * 01:37:12.768 [Thread-1] DEBUG c.Test8Locks2 - begin
 * 01:37:12.768 [Thread-0] DEBUG c.Test8Locks2 - begin
 * 01:37:12.770 [Thread-1] DEBUG c.Number2 - 2
 * 01:37:13.775 [Thread-0] DEBUG c.Number2 - 1
 */
@Slf4j(topic = "c.Test8Locks2")
public class Test8Locks2 {
    public static void main(String[] args) {
        Number2 n1 = new Number2();
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

@Slf4j(topic = "c.Number2")
class Number2 {
    public synchronized void a() {
        // 在这里加上了一个sleep()
        // 不释放锁
        sleep(1);
        log.debug("1");
    }

    public synchronized void b() {
        log.debug("2");
    }
}
