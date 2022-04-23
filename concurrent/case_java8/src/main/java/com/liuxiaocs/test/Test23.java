package com.liuxiaocs.test;

import com.liuxiaocs.n2.util.Sleeper;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 哲学家就餐问题测试死锁
 *
 */
public class Test23 {
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
            if (left.tryLock()) {
                try {
                    // 尝试获得右手筷子
                    if(right.tryLock()) {
                        try {
                            eat();
                        } finally {
                            right.unlock();
                        }
                    }
                } finally {
                    left.unlock();  // 释放自己手里的筷子
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
class ChopStick extends ReentrantLock {
    String name;

    public ChopStick(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "筷子{" + name + '}';
    }
}
