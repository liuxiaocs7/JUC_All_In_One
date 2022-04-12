package com.liuxiaocs.test;

import com.liuxiaocs.Constants;
import com.liuxiaocs.n2.util.FileReader;
import lombok.extern.slf4j.Slf4j;

/**
 * 测试run()与start()
 */
@Slf4j(topic = "c.Test4")
public class Test4 {
    public static void main(String[] args) {
        // 使用第一种方式创建线程
        Thread t1 = new Thread("t1") {
            @Override
            public void run() {
                log.debug("running...");
                FileReader.read(Constants.MP4_FULL_PATH);
            }
        };

        // t1.run();
        t1.start();
        log.debug("do other things......");
    }
}
