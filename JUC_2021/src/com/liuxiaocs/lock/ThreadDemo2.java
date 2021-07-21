package com.liuxiaocs.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

// 第一步 创建资源类，定义属性和操作方法
class Share {
    private int number = 0;
    // 创建Lock
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public void increment() throws InterruptedException {
        // 上锁
        lock.lock();
        try {
            // 判断
            // number != 0则+1
            while(number != 0) {
                condition.await();
            }
            // 干活
            number++;
            System.out.println(Thread.currentThread().getName() + " :: " + number);
            // 通知
            condition.signalAll();
        } finally {
            // 解锁
            lock.unlock();
        }
    }

    // -1
    public void decrement() throws InterruptedException {
        lock.lock();
        try {
            while(number != 1) {
                condition.await();
            }
            number--;
            System.out.println(Thread.currentThread().getName() + " :: " + number);
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }
}

public class ThreadDemo2 {

    public static void main(String[] args) {
        Share share = new Share();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    share.increment();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "AA").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    share.decrement();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "BB").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    share.increment();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "CC").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    share.decrement();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "DD").start();
    }
}
