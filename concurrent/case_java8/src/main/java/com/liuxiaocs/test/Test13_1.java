package com.liuxiaocs.test;

import lombok.extern.slf4j.Slf4j;

/**
 * 测试两阶段终止
 *
 * 使用volatile关键字
 *
 * 不使用打断：
 * 14:15:10.837 [monitor] DEBUG c.TwoPhaseTermination1 - 执行监控记录
 * 14:15:11.845 [monitor] DEBUG c.TwoPhaseTermination1 - 执行监控记录
 * 14:15:12.857 [monitor] DEBUG c.TwoPhaseTermination1 - 执行监控记录
 * 14:15:13.338 [main] DEBUG c.Test13_1 - 停止监控
 * 14:15:13.867 [monitor] DEBUG c.TwoPhaseTermination1 - 执行监控记录
 * 14:15:13.867 [monitor] DEBUG c.TwoPhaseTermination1 - 料理后事
 *
 *
 * 使用打断：
 * 14:14:33.950 [monitor] DEBUG c.TwoPhaseTermination1 - 执行监控记录
 * 14:14:34.965 [monitor] DEBUG c.TwoPhaseTermination1 - 执行监控记录
 * 14:14:35.967 [monitor] DEBUG c.TwoPhaseTermination1 - 执行监控记录
 * 14:14:36.450 [main] DEBUG c.Test13_1 - 停止监控
 * 14:14:36.451 [monitor] DEBUG c.TwoPhaseTermination1 - 料理后事
 * java.lang.InterruptedException: sleep interrupted
 * 	at java.lang.Thread.sleep(Native Method)
 * 	at com.liuxiaocs.test.TwoPhaseTermination1.lambda$start$0(Test13_1.java:55)
 * 	at java.lang.Thread.run(Thread.java:748)
 */
@Slf4j(topic = "c.Test13_1")
public class Test13_1 {
    public static void main(String[] args) throws InterruptedException {
        TwoPhaseTermination1 tpt = new TwoPhaseTermination1();
        tpt.start();

        Thread.sleep(3500);
        log.debug("停止监控");
        tpt.stop();
    }
}

@Slf4j(topic = "c.TwoPhaseTermination1")
class TwoPhaseTermination1 {
    // 监控线程
    private Thread monitorThread;
    // 停止标记
    private volatile boolean stop = false;

    // 启动监控线程
    public void start() {
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
