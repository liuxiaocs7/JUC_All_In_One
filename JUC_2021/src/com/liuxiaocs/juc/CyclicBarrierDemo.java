package com.liuxiaocs.juc;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

// 集齐7颗龙珠就可以召唤神龙
public class CyclicBarrierDemo {

    // 创建固定值
    public static final int NUMBER = 7;

    public static void main(String[] args) {
        // 创建CyclicBarrier
        // 传入数量和Runnable接口(达到值之后要做什么事情)
        CyclicBarrier cyclicBarrier = new CyclicBarrier(NUMBER, () -> {
            // 集齐7颗龙珠之后这句话才会被输出
            System.out.println("集齐7颗龙珠就可以召唤神龙");
        });

        // 集齐七颗龙珠的过程
        for (int i = 1; i <= 7; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + " 星龙珠被收集到了");

                // 等待，没有达到NUMBER就一直等待
                try {
                    cyclicBarrier.await();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }, String.valueOf(i)).start();
        }
    }
}
