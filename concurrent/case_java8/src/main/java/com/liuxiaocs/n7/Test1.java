package com.liuxiaocs.n7;

import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;

/**
 * 测试日期解析
 *
 * 内部成员变量是可变的，多线程访问时会出现问题
 */
@Slf4j(topic = "c.Test1")
public class Test1 {
    public static void main(String[] args) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    log.debug("{}", sdf.parse("1951-04-21"));
                } catch (Exception e) {
                    log.error("{}", e);
                }
            }).start();
        }
    }
}
