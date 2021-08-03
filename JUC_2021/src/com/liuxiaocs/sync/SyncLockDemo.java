package com.liuxiaocs.sync;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

// 可重入锁，在外面获取了锁可以自由进入里面的区域
public class SyncLockDemo {

    public synchronized void add() {
        add();
    }

    public static void main(String[] args) {

        // Lock演示可重入锁
        Lock lock = new ReentrantLock(true);
        // 创建线程
        new Thread(() -> {
            try {
                // 上锁，获取了外面的锁在里面也就畅通无阻了
                lock.lock();
                System.out.println(Thread.currentThread().getName() + " 外层");
                try {
                    // 上锁 lock和unlock必须是配对使用的
                    // lock.lock();
                    System.out.println(Thread.currentThread().getName() + " 内层");
                } finally {
                    // 释放锁
                    // lock.unlock();
                }
            } finally {
                // 释放锁
                lock.unlock();
            }
        }).start();

        // 创建新线程
        new Thread(() -> {
            lock.lock();
            System.out.println("aaaa");
            lock.unlock();
        }, "aa").start();

        // java.lang.StackOverflowError(循环递归调用，栈溢出异常)
        // new SyncLockDemo().add();

        // synchronized
        // Object o = new Object();
        // new Thread(() -> {
        //    synchronized (o) {
        //        System.out.println(Thread.currentThread().getName() + " 外层");
        //        synchronized (o) {
        //            System.out.println(Thread.currentThread().getName() + " 中层");
        //            synchronized (o) {
        //                System.out.println(Thread.currentThread().getName() + " 内层");
        //            }
        //        }
        //    }
        // }, "t1").start();
    }
}
