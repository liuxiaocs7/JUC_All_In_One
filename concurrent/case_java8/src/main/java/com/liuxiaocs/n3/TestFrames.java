package com.liuxiaocs.n3;

/**
 * 测试栈帧
 */
public class TestFrames {
    public static void main(String[] args) {
        // 增加一个线程t1执行method1
        Thread t1 = new Thread() {
            @Override
            public void run() {
                method1(20);
            }
        };
        t1.setName("t1");
        t1.start();

        // 主线程执行method1
        method1(10);
    }

    private static void method1(int x) {
        int y = x + 1;
        Object m = method2();
        System.out.println(m);
    }

    private static Object method2() {
        Object n = new Object();
        return n;
    }
}
