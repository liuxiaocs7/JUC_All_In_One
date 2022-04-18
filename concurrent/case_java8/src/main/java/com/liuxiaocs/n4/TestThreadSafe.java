package com.liuxiaocs.n4;

import java.util.ArrayList;

/**
 * 测试线程安全问题-引用
 *
 * Exception in thread "Thread2" Exception in thread "Thread1" java.lang.ArrayIndexOutOfBoundsException: -1
 * 	at java.util.ArrayList.add(ArrayList.java:465)
 * 	at com.liuxiaocs.n4.ThreadSafe.method2(TestThreadSafe.java:36)
 * 	at com.liuxiaocs.n4.ThreadSafe.method1(TestThreadSafe.java:30)
 * 	at com.liuxiaocs.n4.TestThreadSafe.lambda$main$0(TestThreadSafe.java:19)
 * 	at java.lang.Thread.run(Thread.java:748)
 * java.lang.ArrayIndexOutOfBoundsException: -1
 * 	at java.util.ArrayList.remove(ArrayList.java:507)
 * 	at com.liuxiaocs.n4.ThreadSafe.method3(TestThreadSafe.java:40)
 * 	at com.liuxiaocs.n4.ThreadSafe.method1(TestThreadSafe.java:31)
 * 	at com.liuxiaocs.n4.TestThreadSafe.lambda$main$0(TestThreadSafe.java:19)
 *
 *
 * 子类重写导致的不安全
 * Exception in thread "Thread-393" java.lang.IndexOutOfBoundsException: Index: 0, Size: 0
 * 	at java.util.ArrayList.rangeCheck(ArrayList.java:659)
 * 	at java.util.ArrayList.remove(ArrayList.java:498)
 * 	at com.liuxiaocs.n4.ThreadSafeSubClass.lambda$method3$0(TestThreadSafe.java:113)
 */
public class TestThreadSafe {

    static final int THREAD_NUMBER = 2;
    static final int LOOP_NUMBER = 200;

    public static void main(String[] args) {
        // 线程不安全的情况
        // ThreadUnSafe test = new ThreadUnSafe();
        // 线程安全的情况
        // ThreadSafe test = new ThreadSafe();
        // 子类重写导致的不安全
        ThreadSafeSubClass test = new ThreadSafeSubClass();

        // 两个线程访问同一个共享变量list
        for (int i = 0; i < THREAD_NUMBER; i++) {
            new Thread(() -> {
                test.method1(LOOP_NUMBER);
            }, "Thread" + (i + 1)).start();
        }
    }
}

/**
 * 线程不安全的
 */
class ThreadUnSafe {
    ArrayList<String> list = new ArrayList<>();

    public void method1(int loopNumber) {
        for (int i = 0; i < loopNumber; i++) {
            method2();
            method3();
        }
    }

    private void method2() {
        list.add("1");
    }

    private void method3() {
        list.remove(0);
    }
}

/**
 * 线程安全的
 */
class ThreadSafe {

    // final防止子类重写
    public final void method1(int loopNumber) {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < loopNumber; i++) {
            method2(list);
            method3(list);
        }
    }

    private void method2(ArrayList<String> list) {
        list.add("1");
    }

    private void method3(ArrayList<String> list) {
        list.remove(0);
    }
}

class ThreadSafePublic {

    public void method1(int loopNumber) {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < loopNumber; i++) {
            method2(list);
            method3(list);
        }
    }

    public void method2(ArrayList<String> list) {
        list.add("1");
    }

    public void method3(ArrayList<String> list) {
        list.remove(0);
    }
}

/**
 * 子类继承重写会有线程安全问题
 */
class ThreadSafeSubClass extends ThreadSafePublic {
    @Override
    public void method3(ArrayList<String> list) {
        // 存在多个线程访问同一个共享变量的问题
        new Thread(() -> {
            list.remove(0);
        }).start();
    }
}
