package com.liuxiaocs.readwrite;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

// 资源类
class MyCache {
    // 创建map集合
    // volatile，Map中的数据会不断发生变化
    private volatile Map<String, Object> map = new HashMap<>();

    // 放数据
    public void put(String key, Object value) {
        System.out.println(Thread.currentThread().getName() + " 正在写操作" + key);

        // 暂停一会
        try {
            TimeUnit.MICROSECONDS.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 放数据
        map.put(key, value);
        System.out.println(Thread.currentThread().getName() + " 写完了" + key);
    }

    // 取数据
    public Object get(String key) {
        Object result = null;
        System.out.println(Thread.currentThread().getName() + " 正在读取操作" + key);
        // 暂停一会
        try {
            TimeUnit.MICROSECONDS.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        result = map.get(key);
        System.out.println(Thread.currentThread().getName() + " 取完了" + key);
        return result;
    }
}

/**
 * 没有写完就开始读了
 */
public class ReadWriteLockDemo {
    public static void main(String[] args) {
        MyCache myCache = new MyCache();
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
