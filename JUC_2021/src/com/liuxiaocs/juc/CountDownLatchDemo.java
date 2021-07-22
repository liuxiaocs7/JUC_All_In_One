package com.liuxiaocs.juc;

import java.util.concurrent.CountDownLatch;

// 演示CountDownLatch
public class CountDownLatchDemo {

    // 6个同学陆续离开教室之后，班长锁门
    public static void main(String[] args) throws InterruptedException {

        // 创建CountDownLatch对象，设置初始值为6
        CountDownLatch countDownLatch = new CountDownLatch(6);

        // 6个同学陆续离开教室之后
        for (int i = 0; i < 6; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + " 号同学离开了教室");

                // 每个同学离开之后
                // 计数器 - 1
                countDownLatch.countDown();
            }, String.valueOf(i)).start();
        }

        // 等待
        countDownLatch.await();

        // 6个同学都离开教室之后班长才可以锁门
        System.out.println(Thread.currentThread().getName() + "班长锁门走人了");
    }
}
