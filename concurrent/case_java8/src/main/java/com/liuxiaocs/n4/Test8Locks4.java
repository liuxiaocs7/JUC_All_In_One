package com.liuxiaocs.n4;

import lombok.extern.slf4j.Slf4j;

import static com.liuxiaocs.n2.util.Sleeper.sleep;

/**
 * 测试线程8锁
 *
 * 4.this为锁对象
 *   结果为 (2 1s后1)
 *
 * 11:48:41.058 [Thread-0] DEBUG c.Test8Locks4 - begin
 * 11:48:41.058 [Thread-1] DEBUG c.Test8Locks4 - begin
 * 11:48:41.060 [Thread-1] DEBUG c.Number4 - 2
 * 11:48:42.062 [Thread-0] DEBUG c.Number4 - 1
 */
@Slf4j(topic = "c.Test8Locks4")
public class Test8Locks4 {
    public static void main(String[] args) {
        Number4 n1 = new Number4();
        Number4 n2 = new Number4();
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

@Slf4j(topic = "c.Number4")
class Number4 {
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
