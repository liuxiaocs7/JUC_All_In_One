package com.liuxiaocs.test;

import lombok.extern.slf4j.Slf4j;

/**
 * 测试wait/notify使用
 *
 * 必须获得此对象的锁才能调用这些方法
 */
@Slf4j(topic = "c.Test18")
public class Test18 {
    // 锁对象
    static final Object lock = new Object();

    public static void main(String[] args) {
        synchronized (lock) {
            try {
                lock.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
