package com.liuxiaocs.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

// 第一步，创建资源类
class ShareResource {
    // 定义标志位
    // 1AA 2BB 3CC
    private int flag = 1;
    // 创建Lock锁
    private Lock lock = new ReentrantLock();

    // 创建三个Condition，对应三个线程，好比三把钥匙
    private Condition c1 = lock.newCondition();
    private Condition c2 = lock.newCondition();
    private Condition c3 = lock.newCondition();

    // 打印5次，参数loop表示是第几轮
    public void print5(int loop) throws InterruptedException {
        lock.lock();
        try {
            // 判断，根据标志位进行判断，如果不是1需要进行等待，如果是1才可以往下执行
            while(flag != 1) {
                c1.await();
            }
            // 干活
            for (int i = 1; i <= 5; i++) {
                System.out.println(Thread.currentThread().getName() + " :: " + i + " :轮数: " + loop);
            }
            // 通知
            // 这里是定制化操作，需要通知特定的线程
            // c1通知c2
            // 1. 修改标志位为2
            flag = 2;
            // 2. 通知BB线程
            c2.signal();
        } finally {
            lock.unlock();
        }
    }

    public void print10(int loop) throws InterruptedException {
        lock.lock();
        try {
            while(flag != 2) {
                c2.await();
            }
            for (int i = 1; i <= 10; i++) {
                System.out.println(Thread.currentThread().getName() + " :: " + i + " :轮数: " + loop);
            }
            // 修改标志位为3并通知CC线程
            flag = 3;
            c3.signal();
        } finally {
            lock.unlock();
        }
    }

    public void print15(int loop) throws InterruptedException {
        lock.lock();
        try {
            while(flag != 3) {
                c3.await();
            }
            for (int i = 1; i <= 15; i++) {
                System.out.println(Thread.currentThread().getName() + " :: " + i + " :轮数: " + loop);
            }
            // 修改标志位
            flag = 1;
            // 通知AA线程
            c1.signal();
        } finally {
            lock.unlock();
        }
    }
}

public class ThreadDemo3 {

    public static void main(String[] args) {
        ShareResource shareResource = new ShareResource();

        new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                try {
                    shareResource.print5(i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "AA").start();

        new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                try {
                    shareResource.print10(i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "BB").start();

        new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                try {
                    shareResource.print15(i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "CC").start();
    }
}
