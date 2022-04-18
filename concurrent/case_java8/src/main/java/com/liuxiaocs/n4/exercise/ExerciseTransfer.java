package com.liuxiaocs.n4.exercise;

import lombok.extern.slf4j.Slf4j;

import java.util.Random;

/**
 * 测试转账问题
 *
 * 有两个需要保护的共享变量
 */
@Slf4j(topic = "c.ExerciseTransfer")
public class ExerciseTransfer {
    public static void main(String[] args) throws InterruptedException {
        Account a = new Account(1000);
        Account b = new Account(1000);
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                a.transfer(b, randomAmount());
            }
        }, "t1");

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                b.transfer(a, randomAmount());
            }
        }, "t2");
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        // 查看转账2000次以后的总金额
        log.debug("total:{}", (a.getMoney() + b.getMoney()));
    }

    // Random为线程安全
    static Random random = new Random();

    // 随机 1~5
    public static int randomAmount() {
        return random.nextInt(5) + 1;
    }
}

// 账户
class Account {
    // 共享变量
    private int money;

    public Account(int money) {
        this.money = money;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    // 转账
    // 直接将synchronized加在成员方法上是不行的，因为这等价于加在this这个对象上，只能保护this.money
    // 无法保护target这个共享变量对象
    public void transfer(Account target, int amount) {
        if(this.money >= amount) {
            // synchronized (this) {
            //     this.setMoney(this.getMoney() - amount);
            // }
            // synchronized (target) {
            //     target.setMoney(target.getMoney() + amount);
            // }

            // this和target共享了Account.class，直接锁住Account类
            // 这种方法的实现效率并不高
            synchronized (Account.class) {
                this.setMoney(this.getMoney() - amount);
                target.setMoney(target.getMoney() + amount);
            }
        }
    }
}
