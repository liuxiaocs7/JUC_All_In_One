package com.liuxiaocs.n4;

import lombok.extern.slf4j.Slf4j;

import static com.liuxiaocs.n2.util.Sleeper.sleep;

/**
 * 测试线程8锁
 *
 * 5.this为锁对象
 *   结果为 (2 1s后1)
 *
 * 11:50:26.715 [Thread-1] DEBUG c.Test8Locks5 - begin
 * 11:50:26.715 [Thread-0] DEBUG c.Test8Locks5 - begin
 * 11:50:26.716 [Thread-1] DEBUG c.Number4 - 2
 * 11:50:27.718 [Thread-0] DEBUG c.Number4 - 1
 */
@Slf4j(topic = "c.Test8Locks5")
public class Test8Locks5 {
    public static void main(String[] args) {
        Number5 n1 = new Number5();
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

@Slf4j(topic = "c.Number5")
class Number5 {
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
