package com.liuxiaocs.test;

import lombok.extern.slf4j.Slf4j;
import org.openjdk.jol.info.ClassLayout;

/**
 * 测试偏向锁
 */
@Slf4j(topic = "c.TestBiased")
public class TestBiased {
    public static void main(String[] args) throws InterruptedException {
        Dog d = new Dog();
        // 产生哈希码并填充(因为31位的hashcode存不下了)
        d.hashCode();  // 会禁用这个对象的偏向锁，撤销偏向状态，变成normal状态
        // 重量级锁hashcode会存在Monitor对象中，轻量级锁hashcode会存在线程栈帧的锁记录中
        log.debug(ClassLayout.parseInstance(d).toPrintable());

        // 4s之后偏向锁生效了
        // Thread.sleep(4000);
        // log.debug(ClassLayout.parseInstance(new Dog()).toPrintable());
        // 以后这个d对象就从属与主线程，就给主线程用了，MarkWord头中始终存储的是主线程的线程ID(这个52位的线程ID是操作系统层面的)
        synchronized (d) {
            log.debug(ClassLayout.parseInstance(d).toPrintable());
        }

        log.debug(ClassLayout.parseInstance(d).toPrintable());
    }
}

class Dog {

}
