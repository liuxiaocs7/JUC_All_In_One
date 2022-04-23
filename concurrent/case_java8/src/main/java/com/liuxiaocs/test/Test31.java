package com.liuxiaocs.test;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.LockSupport;

/**
 * 使用Park/Unpark实现交替打印
 */
@Slf4j(topic = "c.Test31")
public class Test31 {

    static Thread t1;
    static Thread t2;
    static Thread t3;

    public static void main(String[] args) {
        ParkUnpark pu = new ParkUnpark(5);
        t1 = new Thread(() -> {
            pu.print("a", t2);
        });

        t2 = new Thread(() -> {
            pu.print("b", t3);
        });

        t3 = new Thread(() -> {
            pu.print("c", t1);
        });

        t1.start();
        t2.start();
        t3.start();

        // 主线程是发起者
        LockSupport.unpark(t1);
    }
}

class ParkUnpark {
    public void print(String str, Thread next) {
        for (int i = 0; i < loopNumber; i++) {
            // 阻塞当前线程
            LockSupport.park();
            System.out.print(str);
            // 唤醒下一个线程
            LockSupport.unpark(next);
        }
    }

    private int loopNumber;

    public ParkUnpark(int loopNumber) {
        this.loopNumber = loopNumber;
    }
}
