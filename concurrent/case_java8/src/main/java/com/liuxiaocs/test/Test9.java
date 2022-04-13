package com.liuxiaocs.test;

import lombok.extern.slf4j.Slf4j;

/**
 * 测试Thread.yield();
 *
 * 1.当两个线程任务正常运行时可以看到打印得到的数字差不多
 * 2.当线程t2执行Thread.yield()方法时，线程t1中打印的数字大于线程t2中的数字
 *   说明CPU的时间被让给线程t1去执行了(线程t1得到的时间片多，线程t2得到的时间片少)
 * 3.t2线程的优先级较高，打印的数字比线程t1中的大，说明优先级是有效的
 */
@Slf4j(topic = "c.Test9")
public class Test9 {
    public static void main(String[] args) {
        Runnable task1 = () -> {
            int count = 0;
            for(;;) {
                System.out.println("----->1 " + count++);
            }
        };

        Runnable task2 = () -> {
            int count = 0;
            for(;;) {
                // 1.调用yield()方法
                // Thread.yield();
                System.out.println("                ----->2 " + count++);
            }
        };

        Thread t1 = new Thread(task1, "t1");
        Thread t2 = new Thread(task2, "t2");

        // 2.设置优先级
        t1.setPriority(Thread.MIN_PRIORITY);
        t2.setPriority(Thread.MAX_PRIORITY);

        t1.start();
        t2.start();
    }
}
