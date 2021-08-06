package com.liuxiaocs;

/**
 * Thread.currentThread().isDaemon()返回当前线程是否是守护线程，如果结果为true，表示是守护线程，否则为false表示不是守护线程
 * 主线程main结束了，用户线程还在运行
 *
 * 当主线程结束后，用户线程还在运行，JVM存活
 * 如果没有用户线程，都是守护线程，JVM结束
 */
public class Main {
    public static void main(String[] args) {
        Thread aa = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "::" + Thread.currentThread().isDaemon());
            while (true) {

            }
        }, "aa");
        // 在执行start之前进行设置
        // 设置守护线程
        // 没有用户线程了，都是守护线程，JVM就结束了
        aa.setDaemon(true);

        aa.start();

        // 主线程结束了
        System.out.println(Thread.currentThread().getName() + "over");
    }
}
