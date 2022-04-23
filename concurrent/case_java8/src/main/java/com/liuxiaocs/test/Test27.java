package com.liuxiaocs.test;

import lombok.extern.slf4j.Slf4j;

/**
 * 使用wait-notify实现交替输出
 * 输出内容abcabcabc
 *
 * 输出内容   等待标记   下一个标记
 * a           1         2
 * b           2         3
 * c           3         1
 */
@Slf4j(topic = "c.Test27")
public class Test27 {
    public static void main(String[] args) {
        WaitNotify wn = new WaitNotify(1, 5);
        new Thread(() -> {wn.print("a", 1, 2);}).start();
        new Thread(() -> {wn.print("b", 2, 3);}).start();
        new Thread(() -> {wn.print("c", 3, 1);}).start();
    }
}

class WaitNotify {

    // 打印
    public void print(String str, int waitFlag, int nextFlag) {
        for (int i = 0; i < loopNumber; i++) {
            synchronized (this) {
                // 两个一致可以继续向下运行，否则需要等待
                while (flag != waitFlag) {
                    try {
                        this.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.print(str);
                flag = nextFlag;
                this.notifyAll();
            }
        }
    }

    // 等待标记
    private int flag;
    // 循环次数
    private int loopNumber;

    public WaitNotify(int flag, int loopNumber) {
        this.flag = flag;
        this.loopNumber = loopNumber;
    }
}
