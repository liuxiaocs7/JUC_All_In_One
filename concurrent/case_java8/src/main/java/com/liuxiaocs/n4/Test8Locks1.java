package com.liuxiaocs.n4;

import lombok.extern.slf4j.Slf4j;

/**
 * 测试线程8锁
 *
 * 1.this为锁对象
 *   结果为12或者21
 *
 * 01:32:42.058 [Thread-0] DEBUG c.Test8Locks - begin
 * 01:32:42.058 [Thread-1] DEBUG c.Test8Locks - begin
 * 01:32:42.060 [Thread-0] DEBUG c.Number - 1
 * 01:32:42.060 [Thread-1] DEBUG c.Number - 2
 *
 * 01:37:53.969 [Thread-1] DEBUG c.Test8Locks1 - begin
 * 01:37:53.969 [Thread-0] DEBUG c.Test8Locks1 - begin
 * 01:37:53.971 [Thread-1] DEBUG c.Number - 2
 * 01:37:53.971 [Thread-0] DEBUG c.Number - 1
 */
@Slf4j(topic = "c.Test8Locks1")
public class Test8Locks1 {
    public static void main(String[] args) {
        Number n1 = new Number();
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

@Slf4j(topic = "c.Number")
class Number {
    public synchronized void a() {
        log.debug("1");
    }

    public synchronized void b() {
        log.debug("2");
    }
}
