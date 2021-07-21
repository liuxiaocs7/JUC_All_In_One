package com.liuxiaocs.lock;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * List集合线程不安全
 * java.util.ConcurrentModificationException 并发修改异常
 */
public class ThreadDemo4 {
    public static void main(String[] args) {
        test8();
    }

    // ConcurrentHashMap
    static void test8() {
        Map<String, String> map = new ConcurrentHashMap<>();
        for (int i = 0; i < 20; i++) {
            String key = String.valueOf(i);
            new Thread(() -> {
                map.put(key, UUID.randomUUID().toString().substring(0, 8));
                System.out.println(map);
            }, String.valueOf(i)).start();
        }
    }

    // 演示HashMap
    static void test7() {
        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < 20; i++) {
            String key = String.valueOf(i);
            new Thread(() -> {
                map.put(key, UUID.randomUUID().toString().substring(0, 8));
                System.out.println(map);
            }, String.valueOf(i)).start();
        }
    }

    // 演示HashSet
    static void test6() {
        Set<String> set = new CopyOnWriteArraySet<>();
        for (int i = 0; i < 20; i++) {
            new Thread(() -> {
                set.add(UUID.randomUUID().toString().substring(0, 8));
                System.out.println(set);
            }, String.valueOf(i)).start();
        }
    }

    // 演示HashSet
    static void test5() {
        Set<String> set = new HashSet<>();
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                set.add(UUID.randomUUID().toString().substring(0, 8));
                System.out.println(set);
            }, String.valueOf(i)).start();
        }
    }

    // 使用JUC包中提供的 CopyOnWriteArrayList()  写时复制技术
    // 读的时候支持并发读，
    // 写是独立写，写的时候会拷贝一份出来，当把内容写入完成之后，和之前的内容进行合并(或者覆盖之前的)，让读取的人都来读取新合并的
    // 既兼顾到并发读，也照顾到独立写操作，就不会产生并发修改的异常
    static void test4() {
        List<String> list = new CopyOnWriteArrayList<>();
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                list.add(UUID.randomUUID().toString().substring(0, 8));
                System.out.println(list);
            }, String.valueOf(i)).start();
        }
    }

    // 在ArrayList外面再套一层Collections.synchronizedList()
    static void test3() {
        List<String> list = Collections.synchronizedList(new ArrayList<>());
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                list.add(UUID.randomUUID().toString().substring(0, 8));
                System.out.println(list);
            }, String.valueOf(i)).start();
        }
    }

    // 使用Vector 不会出现并发修改问题
    static void test2() {
        List<String> list = new Vector<>();
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                // 向集合添加元素
                list.add(UUID.randomUUID().toString().substring(0, 8));
                // 从集合获取内容
                System.out.println(list);
            }, String.valueOf(i)).start();
        }
    }

    // 使用默认的ArrayList
    static void test1() {
        // 创建ArrayList集合，线程不安全
        List<String> list = new ArrayList<>();

        // 创建多个线程
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                // 向集合添加内容
                list.add(UUID.randomUUID().toString().substring(0, 8));
                // 从集合获取内容
                System.out.println(list);
            }, String.valueOf(i)).start();
        }
    }
}
