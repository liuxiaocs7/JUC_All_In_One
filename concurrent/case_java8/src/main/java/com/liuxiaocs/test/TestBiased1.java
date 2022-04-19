package com.liuxiaocs.test;

import lombok.extern.slf4j.Slf4j;
import org.openjdk.jol.info.ClassLayout;

/**
 * 测试偏向锁
 *
 * 多个线程访问同一个对象导致偏向锁被撤销
 */
@Slf4j(topic = "c.TestBiased1")
public class TestBiased1 {
    public static void main(String[] args) throws InterruptedException {
        Dog1 d = new Dog1();

        new Thread(() -> {
            log.debug(ClassLayout.parseInstance(d).toPrintable());
            synchronized (d) {
                log.debug(ClassLayout.parseInstance(d).toPrintable());
            }
            log.debug(ClassLayout.parseInstance(d).toPrintable());

            synchronized (TestBiased1.class) {
                TestBiased1.class.notify();
            }
        }, "t1").start();

        new Thread(() -> {
            synchronized (TestBiased1.class) {
                try {
                    // t2线程进入等待状态
                    TestBiased1.class.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            log.debug(ClassLayout.parseInstance(d).toPrintable());
            synchronized (d) {
                log.debug(ClassLayout.parseInstance(d).toPrintable());
            }
            log.debug(ClassLayout.parseInstance(d).toPrintable());
        }, "t2").start();
    }
}

class Dog1 {

}
