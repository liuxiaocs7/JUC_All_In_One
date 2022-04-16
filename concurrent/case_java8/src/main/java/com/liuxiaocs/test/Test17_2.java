package com.liuxiaocs.test;

import lombok.extern.slf4j.Slf4j;

/**
 * 使用阻塞的方式解决共享问题
 *
 * 面向对象优化
 */
@Slf4j(topic = "c.Test17")
public class Test17_2 {

    public static void main(String[] args) throws InterruptedException {
        Room room = new Room();
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 5000; i++) {
                room.increment();
            }
        }, "t1");

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 5000; i++) {
                room.decrement();
            }
        }, "t2");

        t1.start();
        t2.start();
        t1.join();
        t2.join();
        log.debug("{}", room.getCounter());
    }
}

class Room {
    int counter = 0;

    public void increment() {
        synchronized (this) {
            counter++;
        }
    }

    public void decrement() {
        synchronized (this) {
            counter--;
        }
    }

    // 获取值的时候也需要加锁避免读取到中间结果
    public int getCounter() {
        synchronized (this) {
            return counter;
        }
    }
}
