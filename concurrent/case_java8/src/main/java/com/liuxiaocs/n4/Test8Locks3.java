package com.liuxiaocs.n4;

import lombok.extern.slf4j.Slf4j;

import static com.liuxiaocs.n2.util.Sleeper.sleep;

/**
 * 测试线程8锁
 *
 * 3.this为锁对象
 *   结果为 (3 1s后12) 或者 (23 1s后1) 或者 (32 1s后1)
 *
 * 11:38:01.838 [Thread-1] DEBUG c.Test8Locks3 - begin
 * 11:38:01.838 [Thread-2] DEBUG c.Test8Locks3 - begin
 * 11:38:01.838 [Thread-0] DEBUG c.Test8Locks3 - begin
 * 11:38:01.841 [Thread-1] DEBUG c.Number3 - 2
 * 11:38:01.841 [Thread-2] DEBUG c.Number3 - 3
 * 11:38:02.845 [Thread-0] DEBUG c.Number3 - 1
 *
 * 11:39:18.109 [Thread-0] DEBUG c.Test8Locks3 - begin
 * 11:39:18.109 [Thread-1] DEBUG c.Test8Locks3 - begin
 * 11:39:18.109 [Thread-2] DEBUG c.Test8Locks3 - begin
 * 11:39:18.112 [Thread-2] DEBUG c.Number3 - 3
 * 11:39:19.115 [Thread-0] DEBUG c.Number3 - 1
 * 11:39:19.115 [Thread-1] DEBUG c.Number3 - 2
 *
 * 11:40:01.140 [Thread-2] DEBUG c.Test8Locks3 - begin
 * 11:40:01.140 [Thread-1] DEBUG c.Test8Locks3 - begin
 * 11:40:01.140 [Thread-0] DEBUG c.Test8Locks3 - begin
 * 11:40:01.143 [Thread-2] DEBUG c.Number3 - 3
 * 11:40:01.143 [Thread-1] DEBUG c.Number3 - 2
 * 11:40:02.155 [Thread-0] DEBUG c.Number3 - 1
 *
 */
@Slf4j(topic = "c.Test8Locks3")
public class Test8Locks3 {
    public static void main(String[] args) {
        Number3 n1 = new Number3();
        new Thread(() -> {
            log.debug("begin");
            n1.a();
        }).start();

        new Thread(() -> {
            log.debug("begin");
            n1.b();
        }).start();

        new Thread(() -> {
            log.debug("begin");
            n1.c();
        }).start();
    }
}

@Slf4j(topic = "c.Number3")
class Number3 {
    public synchronized void a() {
        // 在这里加上了一个sleep()
        // 不释放锁
        sleep(1);
        log.debug("1");
    }

    public synchronized void b() {
        log.debug("2");
    }

    // 这里添加一个不加锁的方法
    // 没有互斥效果，并行执行
    public void c() {
        log.debug("3");
    }
}
