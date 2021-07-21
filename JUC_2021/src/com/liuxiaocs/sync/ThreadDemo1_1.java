package com.liuxiaocs.sync;

// 第一步  创建资源类，定义属性和操作方法
class Share1 {
    // 初始值
    private int number = 0;

    // +1
    public synchronized void increment() throws InterruptedException {
        // 第二步 判断，干活，通知
        // 判断number值是否是0，如果不是0，等待
        while(number != 0) {
            // wait()方法在哪里睡，就在哪里醒
            this.wait();
        }
        // 干活
        // 如果number值是0，就执行+1操作
        this.number++;
        System.out.println(Thread.currentThread().getName() + " :: " + number);

        // 通知其他线程
        this.notifyAll();
    }

    // -1
    public synchronized void decrement() throws InterruptedException {
        while(number != 1) {
            this.wait();
        }
        // 干活
        this.number--;
        System.out.println(Thread.currentThread().getName() + " :: " + number);
        // 通知其他线程
        this.notifyAll();
    }
}

public class ThreadDemo1_1 {
    // 第三步 创建多个线程，调用资源类的操作方法
    public static void main(String[] args) {
        Share1 share = new Share1();
        // 创建线程
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    // +1线程
                    share.increment();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "AA").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    // -1线程
                    share.decrement();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "BB").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    // +1线程
                    share.increment();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "CC").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    // -1线程
                    share.decrement();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "DD").start();
    }
}
