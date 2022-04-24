package com.liuxiaocs.test;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/**
 * 测试字段更新器
 *
 * Exception in thread "main" java.lang.IllegalArgumentException: Must be volatile type
 * 	at java.util.concurrent.atomic.AtomicReferenceFieldUpdater$AtomicReferenceFieldUpdaterImpl.<init>(AtomicReferenceFieldUpdater.java:348)
 * 	at java.util.concurrent.atomic.AtomicReferenceFieldUpdater.newUpdater(AtomicReferenceFieldUpdater.java:110)
 * 	at com.liuxiaocs.test.Test40.main(Test40.java:16)
 */
@Slf4j(topic = "c.Test40")
public class Test40 {
    public static void main(String[] args) {
        Student stu = new Student();
        // 类型，属性类型，属性名
        AtomicReferenceFieldUpdater updater =
                AtomicReferenceFieldUpdater.newUpdater(Student.class, String.class, "name");

        // 对属性值的更新是原子的
        // 原始值是null，需要更新为"张三"
        System.out.println(updater.compareAndSet(stu, null, "张三"));
        System.out.println(stu);
    }
}

class Student {
    volatile String name;

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                '}';
    }
}
