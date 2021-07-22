package com.liuxiaocs.lock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 第一步 创建资源类，定义属性和操作方法
 */
class LTicket {
    // 票数量
    private int number = 30;
    // 创建可重入锁(默认是一个非公平锁)
    // private final ReentrantLock lock = new ReentrantLock();
    // 公平锁
    private final ReentrantLock lock = new ReentrantLock(true);

    // 卖票方法
    public void sale() {
        // 上锁
        lock.lock();
        // 为了避免异常最好使用try-finally写法，保证一定会解锁
        try {
            // 判断：当前是否还有票
            if(number > 0) {
                System.out.println(Thread.currentThread().getName() + " ---> 卖出: " + number-- + " 剩下: " + number);
            }
        } finally {
            // 解锁
            lock.unlock();
        }
    }
}

public class LSaleTicket {

    public static void main(String[] args) {
        // 第二步，创建多个线程，调用资源类的操作方法
        LTicket ticket = new LTicket();

        // 创建三个线程
        new Thread(() -> {
            for (int i = 0; i < 40; i++) {
                ticket.sale();
            }
        }, "AA").start();

        new Thread(() -> {
            for (int i = 0; i < 40; i++) {
                ticket.sale();
            }
        }, "BB").start();

        new Thread(() -> {
            for (int i = 0; i < 40; i++) {
                ticket.sale();
            }
        }, "CC").start();
    }
}
