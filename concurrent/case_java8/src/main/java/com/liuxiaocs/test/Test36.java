package com.liuxiaocs.test;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicReference;

import static com.liuxiaocs.n2.util.Sleeper.sleep;

/**
 * 原子引用 ABA问题
 *
 * 主线程无法感知到其他线程对变量的修改
 */
@Slf4j(topic = "c.Test36")
public class Test36 {
    static AtomicReference<String> ref = new AtomicReference<>("A");
    public static void main(String[] args) {
        log.debug("main start");
        // 获取值A
        // 这个共享变量被其他线程修改过
        String prev = ref.get();
        other();
        sleep(1);
        // 尝试改为C
        log.debug("change A->C {}", ref.compareAndSet(prev, "C"));
    }

    private static void other() {
        new Thread(() -> {
            log.debug("change A->B {}", ref.compareAndSet(ref.get(), "B"));
        }, "t1").start();
        new Thread(() -> {
            log.debug("change B->A {}", ref.compareAndSet(ref.get(), "A"));
        }, "t2").start();
    }
}
