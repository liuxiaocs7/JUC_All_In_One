package com.liuxiaocs.test;

import lombok.extern.slf4j.Slf4j;

/**
 * 测试两阶段终止
 *
 * 犹豫模式 Balking
 *
 * 需要保证原子性!!!
 */
@Slf4j(topic = "c.Test13_2")
public class Test13_2 {
    public static void main(String[] args) throws InterruptedException {
        TwoPhaseTermination2 tpt = new TwoPhaseTermination2();
        tpt.start();
        tpt.start();
        tpt.start();

        // Thread.sleep(3500);
        // log.debug("停止监控");
        // tpt.stop();
    }
}

@Slf4j(topic = "c.TwoPhaseTermination2")
class TwoPhaseTermination2 {
    // 监控线程
    private Thread monitorThread;
    // 停止标记
    private volatile boolean stop = false;
    // 判断是否执行过start方法
    private volatile boolean starting = false;

    // 启动监控线程
    public void start() {
        synchronized (this) {
            if(starting) {
                return;
            }
            starting = true;
        }
        monitorThread = new Thread(() -> {
            while (true) {
                if(stop) {
                    log.debug("料理后事");
                    break;
                }
                try {
                    Thread.sleep(1000);
                    log.debug("执行监控记录");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "monitor");
        monitorThread.start();
    }

    // 停止监控线程
    public void stop() {
        stop = true;
        // 让线程可以尽快改变状态，使用打断实现
        monitorThread.interrupt();
    }
}
