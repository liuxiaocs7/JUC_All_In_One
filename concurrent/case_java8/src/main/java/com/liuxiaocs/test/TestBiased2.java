package com.liuxiaocs.test;

import lombok.extern.slf4j.Slf4j;
import org.openjdk.jol.info.ClassLayout;

import java.util.Vector;

/**
 * 测试偏向锁
 *
 * 批量重偏向
 */
@Slf4j(topic = "c.TestBiased2")
public class TestBiased2 {
    public static void main(String[] args) throws InterruptedException {
        Vector<Dog2> list = new Vector<>();
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 30; i++) {
                Dog2 d = new Dog2();
                list.add(d);
                // 先让这30个对象都偏向于线程t1
                synchronized (d) {
                    log.debug(i + "\t" + ClassLayout.parseInstance(d).toPrintable());
                }
            }
            synchronized (list) {
                list.notify();
            }
        }, "t1");
        t1.start();

        Thread t2 = new Thread(() -> {
            synchronized (list) {
                try {
                    list.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            log.debug("=====================> ");
            for (int i = 0; i < 30; i++) {
                Dog2 d = list.get(i);
                log.debug(i + "\t" + ClassLayout.parseInstance(d).toPrintable());
                // 一开始偏向于线程t1，这里撤销偏向锁，升级为轻量级锁
                // 但是撤销的次数比较多了，默认是20，JVM就认为偏向错了，会让剩余的线程偏向到t2上
                synchronized (d) {
                    log.debug(i + "\t" + ClassLayout.parseInstance(d).toPrintable());
                }
                log.debug(i + "\t" + ClassLayout.parseInstance(d).toPrintable());
            }
        }, "t2");
        t2.start();
    }
}

class Dog2 {

}
