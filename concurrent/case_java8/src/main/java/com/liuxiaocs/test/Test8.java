package com.liuxiaocs.test;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * 测试使用TimeUnit类
 */
@Slf4j(topic = "c.Test8")
public class Test8 {
    public static void main(String[] args) throws InterruptedException {
        log.debug("enter");
        // 睡眠1s
        TimeUnit.SECONDS.sleep(1);
        log.debug("end");
        // Thread.sleep(1000);
    }
}
