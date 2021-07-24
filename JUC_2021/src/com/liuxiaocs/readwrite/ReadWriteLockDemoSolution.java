package com.liuxiaocs.readwrite;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

// 资源类
class MyCacheSolution {
    // 创建map集合
    // volatile，Map中的数据会不断发生变化
    private volatile Map<String, Object> map = new HashMap<>();

    // 创建读写锁对象
    private ReadWriteLock rwLock = new ReentrantReadWriteLock();

    // 放数据
    public void put(String key, Object value) {
        // 添加写锁
        rwLock.writeLock().lock();

        try {
            System.out.println(Thread.currentThread().getName() + " 正在写操作" + key);
            // 暂停一会
            TimeUnit.MICROSECONDS.sleep(300);
            // 放数据
            map.put(key, value);
            System.out.println(Thread.currentThread().getName() + " 写完了" + key);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // 释放写锁
            rwLock.writeLock().unlock();
        }
    }

    // 取数据
    public Object get(String key) {
        // 添加读锁
        rwLock.readLock().lock();

        Object result = null;
        try {
            System.out.println(Thread.currentThread().getName() + " 正在读取操作" + key);
            // 暂停一会
            TimeUnit.MICROSECONDS.sleep(300);
            result = map.get(key);
            System.out.println(Thread.currentThread().getName() + " 取完了" + key);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // 释放读锁
            rwLock.readLock().unlock();
        }
        return result;
    }
}

/**
 * 写锁是独占锁
 * 读锁是共享锁
 */
public class ReadWriteLockDemoSolution {
    public static void main(String[] args) {
        MyCacheSolution myCache = new MyCacheSolution();
        // 创建线程放数据
        for (int i = 0; i < 5; i++) {
            // lambda表达式中必须是final的值
            final int num = i;
            new Thread(() -> {
                myCache.put(num + "", num + "");
            }, String.valueOf(i)).start();
        }
        // 创建线程取数据
        for (int i = 0; i < 5; i++) {
            // lambda表达式中必须是final的值
            final int num = i;
            new Thread(() -> {
                myCache.get(num + "");
            }, String.valueOf(i)).start();
        }
    }
}
