package com.liuxiaocs.test;

import lombok.extern.slf4j.Slf4j;

/**
 * 测试两阶段终止
 *
 * 00:53:21.553 [Thread-0] DEBUG c.TwoPhaseTermination - 执行监控记录
 * 00:53:22.561 [Thread-0] DEBUG c.TwoPhaseTermination - 执行监控记录
 * 00:53:23.569 [Thread-0] DEBUG c.TwoPhaseTermination - 执行监控记录
 * java.lang.InterruptedException: sleep interrupted
 * 	at java.lang.Thread.sleep(Native Method)
 * 	at com.liuxiaocs.test.TwoPhaseTermination.lambda$start$0(Test13.java:33)
 * 	at java.lang.Thread.run(Thread.java:748)
 * 00:53:24.043 [Thread-0] DEBUG c.TwoPhaseTermination - 料理后事
 */
@Slf4j(topic = "c.Test13")
public class Test13 {
    public static void main(String[] args) throws InterruptedException {
        TwoPhaseTermination tpt = new TwoPhaseTermination();
        tpt.start();

        Thread.sleep(3500);
        tpt.stop();
    }
}

@Slf4j(topic = "c.TwoPhaseTermination")
class TwoPhaseTermination {
    private Thread monitor;

    // 启动监控线程
    public void start() {
        monitor = new Thread(() -> {
            while (true) {
                Thread current = Thread.currentThread();
                if(current.isInterrupted()) {
                    log.debug("料理后事");
                    break;
                }
                try {
                    Thread.sleep(1000);  // 情况1被打断(非正常打断，睡眠过程中被打断，会进入catch块，打断标记会被重置为false)
                    log.debug("执行监控记录");   // 情况2被打断(正常情况，打断标记会被置为true)
                    // 第二种情况被打断，不会清除打断标记，因此判断打断标记为true时可以退出循环，正常运行
                    // current.interrupt();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    // 重新设置打断标记
                    // 如果这里不加interrupt()，会一直执行监控记录下去，因为打断标记一直是false
                    current.interrupt();
                }
            }
        });
        monitor.start();
    }

    // 停止监控线程
    public void stop() {
        // 打断
        monitor.interrupt();
    }
}
