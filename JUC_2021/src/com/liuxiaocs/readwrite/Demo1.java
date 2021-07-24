package com.liuxiaocs.readwrite;

import java.util.concurrent.locks.ReentrantReadWriteLock;

// 演示读写锁降级
public class Demo1 {
    public static void main(String[] args) {
        test2();
    }

    static void test2() {
        // 可重入读写锁对象
        ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
        // 读锁
        ReentrantReadWriteLock.ReadLock readLock = rwLock.readLock();
        // 写锁
        ReentrantReadWriteLock.WriteLock writeLock = rwLock.writeLock();

        // 1.获取读锁
        // 在读的时候是不能写的，只有在读释放时候才能写
        readLock.lock();
        System.out.println("---read");

        // 2.获取写锁
        writeLock.lock();
        System.out.println("liuxiaocs");

        // 3.释放写锁
        // writeLock.unlock();
        // 4.释放读锁
        // readLock.unlock();
    }

    static void test1() {
        // 可重入读写锁对象
        ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
        // 读锁
        ReentrantReadWriteLock.ReadLock readLock = rwLock.readLock();
        // 写锁
        ReentrantReadWriteLock.WriteLock writeLock = rwLock.writeLock();

        // 在进行写的时候也可以读
        // 锁降级(将写锁降级为读锁)，读锁不能升级为写锁，只有读完之后才可以写
        // 1.获取写锁
        writeLock.lock();
        System.out.println("liuxiaocs");

        // 2.获取读锁
        readLock.lock();
        System.out.println("---read");

        // 3.释放写锁
        writeLock.unlock();
        // 4.释放读锁
        readLock.unlock();
    }
}
