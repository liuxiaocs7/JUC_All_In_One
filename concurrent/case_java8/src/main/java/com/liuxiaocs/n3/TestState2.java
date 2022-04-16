package com.liuxiaocs.n3;

import com.liuxiaocs.Constants;
import com.liuxiaocs.n2.util.FileReader;

/**
 * 测试线程状态
 */
public class TestState2 {
    public static void main(String[] args) {
        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                FileReader.read(Constants.MP4_FULL_PATH);
            }
        }, "t1").start();

        System.out.println("OK");
    }
}
