package com.liuxiaocs.n4.deadlock.v1;

import com.liuxiaocs.n2.util.Sleeper;
import lombok.extern.slf4j.Slf4j;

/**
 * 哲学家就餐问题测试死锁
 *
 * 23:39:56.816 [赫拉克利特] DEBUG c.Philosopher - eating...
 * 23:39:56.816 [苏格拉底] DEBUG c.Philosopher - eating...
 * 23:39:57.829 [赫拉克利特] DEBUG c.Philosopher - eating...
 * 23:39:57.829 [苏格拉底] DEBUG c.Philosopher - eating...
 * 23:39:58.834 [赫拉克利特] DEBUG c.Philosopher - eating...
 */
public class TestDeadLock {
    public static void main(String[] args) {
        ChopStick c1 = new ChopStick("1");
        ChopStick c2 = new ChopStick("2");
        ChopStick c3 = new ChopStick("3");
        ChopStick c4 = new ChopStick("4");
        ChopStick c5 = new ChopStick("5");
        new Philosopher("苏格拉底", c1, c2).start();
        new Philosopher("柏拉图", c2, c3).start();
        new Philosopher("亚里士多德", c3, c4).start();
        new Philosopher("赫拉克利特", c4, c5).start();
        new Philosopher("阿基米德", c5, c1).start();
        // 将这里修改顺序可以避免死锁，但是会有线程饥饿问题(某些人抢不到锁)
        // new Philosopher("阿基米德", c1, c5).start();
    }
}

// 哲学家类
@Slf4j(topic = "c.Philosopher")
class Philosopher extends Thread {
    // 哲学家希望同时持有左边和右边筷子两个资源
    ChopStick left;
    ChopStick right;

    public Philosopher(String name, ChopStick left, ChopStick right) {
        super(name);
        this.left = left;
        this.right = right;
    }

    @Override
    public void run() {
        // 哲学家不断地思考和吃饭
        while(true) {
            // 尝试获得左手筷子
            synchronized (left) {
                // 尝试获得右手筷子
                synchronized (right) {
                    eat();
                }
            }
        }
    }

    private void eat() {
        log.debug("eating...");
        Sleeper.sleep(1);
    }
}

// 筷子类
class ChopStick {
    String name;

    public ChopStick(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "筷子{" + name + '}';
    }
}
