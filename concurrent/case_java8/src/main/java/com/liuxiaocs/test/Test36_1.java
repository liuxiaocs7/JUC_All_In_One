package com.liuxiaocs.test;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

import static com.liuxiaocs.n2.util.Sleeper.sleep;

/**
 * 原子引用 ABA问题
 *
 * AtomicStampedReference加一个版本号  乐观锁的思想解决
 *
 * 15:55:25.475 [main] DEBUG c.Test36_1 - main start
 * 15:55:25.477 [main] DEBUG c.Test36_1 - stamp: 0
 * 15:55:25.507 [t1] DEBUG c.Test36_1 - stamp: 0
 * 15:55:25.507 [t1] DEBUG c.Test36_1 - change A->B true
 * 15:55:25.507 [t2] DEBUG c.Test36_1 - stamp: 1
 * 15:55:25.507 [t2] DEBUG c.Test36_1 - change B->A true
 * 15:55:26.515 [main] DEBUG c.Test36_1 - stamp: 0
 * 15:55:26.515 [main] DEBUG c.Test36_1 - new stamp: 2
 * 15:55:26.515 [main] DEBUG c.Test36_1 - change A->C false
 */
@Slf4j(topic = "c.Test36_1")
public class Test36_1 {
    static AtomicStampedReference<String> ref = new AtomicStampedReference<>("A", 0);
    public static void main(String[] args) {
        log.debug("main start");
        // 获取值A
        // 这个共享变量被其他线程修改过
        String prev = ref.getReference();
        // 获取版本号
        int stamp = ref.getStamp();
        log.debug("stamp: {}", stamp);
        other();
        sleep(1);
        // 尝试改为C
        // 获取到的版本号，期待的下一个版本号
        log.debug("stamp: {}", stamp);
        log.debug("new stamp: {}", ref.getStamp());
        log.debug("change A->C {}", ref.compareAndSet(prev, "C", stamp, stamp + 1));
    }

    private static void other() {
        new Thread(() -> {
            int stamp = ref.getStamp();
            log.debug("stamp: {}", stamp);
            log.debug("change A->B {}", ref.compareAndSet(ref.getReference(), "B", stamp, stamp + 1));
        }, "t1").start();
        new Thread(() -> {
            int stamp = ref.getStamp();
            log.debug("stamp: {}", stamp);
            log.debug("change B->A {}", ref.compareAndSet(ref.getReference(), "A", stamp, stamp + 1));
        }, "t2").start();
    }
}
