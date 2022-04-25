package com.liuxiaocs.test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 保护共享资源 -> 加锁实现 和 原子类实现
 *
 * 180 cost: 55 ms
 *
 * 解决方案：
 * 0 cost: 55 ms    加锁
 * 0 cost: 49 ms    无锁
 */
public class TestAccount {
    public static void main(String[] args) {
        // 使用加锁方式解决
        Account account1 = new AccountUnsafe(10000);
        Account.demo(account1);

        Account account2 = new AccountCas(10000);
        Account.demo(account2);
    }
}

class AccountCas implements Account {

    private AtomicInteger balance;

    public AccountCas(int balance) {
        this.balance = new AtomicInteger(balance);
    }

    @Override
    public Integer getBalance() {
        return this.balance.get();
    }

    @Override
    public void withdraw(Integer amount) {
        // while (true) {
        //     // 局部变量存储在线程的工作内存中，并没有同步到主存中
        //     // 获取余额的最新值
        //     int prev = balance.get();
        //     // 要修改后的余额
        //     int next = prev - amount;
        //     // 真正修改，如果修改成功返回true则可以退出循环
        //     if (balance.compareAndSet(prev, next)) {
        //         break;
        //     }
        // }

        // 简化代码
        balance.getAndAdd(-amount);
    }
}

class AccountUnsafe implements Account {

    private Integer balance;

    public AccountUnsafe(Integer balance) {
        this.balance = balance;
    }

    @Override
    public Integer getBalance() {
        synchronized (this) {
            return this.balance;
        }
    }

    // 临界区
    @Override
    public void withdraw(Integer amount) {
        // 加锁保护
        synchronized (this) {
            this.balance -= amount;
        }
    }
}

interface Account {
    // 获取余额
    Integer getBalance();

    // 取款
    void withdraw(Integer amount);

    /**
     * 方法内会启动1000个线程，每个线程做-10元的操作
     * 如果初始余额为10000那么正确的结果应当是0
     */
    static void demo(Account account) {
        List<Thread> ts = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            ts.add(new Thread(() -> {
                account.withdraw(10);
            }));
        }
        long start = System.nanoTime();
        ts.forEach(Thread::start);
        ts.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        long end = System.nanoTime();
        System.out.println(account.getBalance() + " cost: " + (end - start) / 1000_000 + " ms");
    }
}
