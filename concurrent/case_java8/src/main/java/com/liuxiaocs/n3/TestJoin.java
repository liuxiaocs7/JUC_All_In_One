package com.liuxiaocs.n3;

import lombok.extern.slf4j.Slf4j;

import static com.liuxiaocs.n2.util.Sleeper.sleep;

/**
 * 测试等待多个线程的Join
 *
 * test2：等待2000ms
 * 13:12:26.863 [main] DEBUG c.TestJoin - join begin
 * 13:12:27.873 [main] DEBUG c.TestJoin - t1 join end
 * 13:12:28.878 [main] DEBUG c.TestJoin - t2 join end
 * 13:12:28.878 [main] DEBUG c.TestJoin - r1: 10 r2: 20 cost: 2017
 *
 * test3: 最多等待1500ms
 * 13:22:59.148 [main] DEBUG c.TestJoin - join begin
 * 13:23:00.662 [main] DEBUG c.TestJoin - r1: 0 r2: 0 cost: 1515
 */
@Slf4j(topic = "c.TestJoin")
public class TestJoin {
    static int r = 0;
    static int r1 = 0;
    static int r2 = 0;

    public static void main(String[] args) throws InterruptedException {
        // 多个join方法等待
        // test2();

        // join(long time)方法
        test3();
    }

    /**
     * 测试等待多个结果
     *
     * @throws InterruptedException
     */
    private static void test2() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            sleep(1);
            r1 = 10;
        });
        Thread t2 = new Thread(() -> {
            sleep(2);
            r2 = 20;
        });
        long start = System.currentTimeMillis();
        t1.start();
        t2.start();
        log.debug("join begin");
        t2.join();
        log.debug("t2 join end");
        t1.join();
        log.debug("t1 join end");
        long end = System.currentTimeMillis();
        log.debug("r1: {} r2: {} cost: {}", r1, r2, end - start);
    }

    /**
     * 测试有时效的等待
     */
    public static void test3() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            // 线程睡眠时间
            sleep(2);
            r1 = 10;
        });

        long start = System.currentTimeMillis();
        t1.start();

        // 线程执行结束会导致join结束
        log.debug("join begin");
        // 主线程等待t1线程执行结束，只等待1.5s
        t1.join(3000);
        long end = System.currentTimeMillis();
        log.debug("r1: {} r2: {} cost: {}", r1, r2, end - start);
    }
}
