package com.liuxiaocs.test;

import com.liuxiaocs.n2.util.Sleeper;
import lombok.extern.slf4j.Slf4j;


/**
 * 保护性暂停
 *
 * 增加超时效果
 *
 * 正常：
 * 13:08:56.188 [t1] DEBUG c.Test20 - begin
 * 13:08:56.188 [t2] DEBUG c.Test20 - begin
 * 13:08:57.193 [t1] DEBUG c.Test20 - 结果是: java.lang.Object@1f542960
 *
 * 超时：
 * 13:08:07.164 [t2] DEBUG c.Test20 - begin
 * 13:08:07.164 [t1] DEBUG c.Test20 - begin
 * 13:08:09.179 [t1] DEBUG c.Test20 - 结果是: null
 *
 * 虚假唤醒：
 * 13:10:09.143 [t1] DEBUG c.Test20 - begin
 * 13:10:09.143 [t2] DEBUG c.Test20 - begin
 * 13:10:11.149 [t1] DEBUG c.Test20 - 结果是: null
 */
@Slf4j(topic = "c.Test20")
public class Test20_1 {
    // 线程1等待线程2的下载结果
    public static void main(String[] args) {
        GuardedObject1 guardedObject = new GuardedObject1();
        new Thread(() -> {
            log.debug("begin");
            Object response = guardedObject.get(2000);
            log.debug("结果是: {}", response);
        }, "t1").start();

        new Thread(() -> {
            log.debug("begin");
            // 正常
            Sleeper.sleep(1);
            // 超时
            // Sleeper.sleep(3);
            // guardedObject.complete(new Object());
            // 虚假唤醒
            guardedObject.complete(null);
        }, "t2").start();
    }
}

/**
 * 增加超时效果
 */
class GuardedObject1 {
    // 结果
    private Object response;

    // 获取结果
    // timeout表示最多要等待多久
    public Object get(long timeout) {
        synchronized (this) {
            // 开始时间
            long begin = System.currentTimeMillis();
            // 经历的时间
            long passedTime = 0;
            // 没有结果
            while(response == null) {
                // 这一轮循环应该等待的时间
                long waitTime = timeout - passedTime;
                // 经历的时间超过了最大等待时间时，退出循环
                if(waitTime <= 0) {
                    break;
                }
                try {
                    this.wait(waitTime);  // 虚假唤醒
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // 求得经历时间
                passedTime = System.currentTimeMillis() - begin;
            }
            return response;
        }
    }

    // 产生结果
    public void complete(Object response) {
        synchronized (this) {
            // 给结果成员变量赋值
            this.response = response;
            this.notifyAll();
        }
    }
}
