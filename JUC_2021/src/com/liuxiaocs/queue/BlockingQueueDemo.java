package com.liuxiaocs.queue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class BlockingQueueDemo {
    public static void main(String[] args) throws InterruptedException {
        test4();
    }

    private static void test4() throws InterruptedException {
        // 1.创建阻塞队列(定长阻塞队列，长度设置为3)
        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(3);

        // 第四组方法
        System.out.println(blockingQueue.offer("a"));
        System.out.println(blockingQueue.offer("b"));
        System.out.println(blockingQueue.offer("c"));
        // 当超过3秒还放不进去就自动结束(第二个参数是值，第三个参数是单位)
        System.out.println(blockingQueue.offer("w", 3L, TimeUnit.SECONDS));
    }

    private static void test3() throws InterruptedException {
        // 1.创建阻塞队列(定长阻塞队列，长度设置为3)
        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(3);

        // 第三组方法
        blockingQueue.put("a");
        blockingQueue.put("b");
        blockingQueue.put("c");
        // (程序一直没有停止，一直处于一个阻塞状态，因为加不进去了)
        // blockingQueue.put("w");

        System.out.println(blockingQueue.take());
        System.out.println(blockingQueue.take());
        System.out.println(blockingQueue.take());
        // (程序一直没有停止，一直处于一个阻塞状态，因为已经为空取不出来了)
        // System.out.println(blockingQueue.take());
    }

    private static void test2() {
        // 1.创建阻塞队列(定长阻塞队列，长度设置为3)
        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(3);

        // 第二组方法
        // true 表示添加成功
        System.out.println(blockingQueue.offer("a"));
        System.out.println(blockingQueue.offer("b"));
        System.out.println(blockingQueue.offer("c"));
        // false 表示添加失败
        System.out.println(blockingQueue.offer("w"));

        // a
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        // null表示此时队列为空，无法取出
        System.out.println(blockingQueue.poll());
    }

    private static void test1() {
        // 1.创建阻塞队列(定长阻塞队列，长度设置为3)
        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(3);

        // 第一组方法
        System.out.println(blockingQueue.add("a"));
        System.out.println(blockingQueue.add("b"));
        System.out.println(blockingQueue.add("c"));
        System.out.println(blockingQueue.element());
        // 超出，抛出异常 java.lang.IllegalStateException: Queue full
        // System.out.println(blockingQueue.add("w"));

        System.out.println(blockingQueue.remove());
        System.out.println(blockingQueue.remove());
        System.out.println(blockingQueue.remove());
        // 抛出异常 java.util.NoSuchElementException
        // System.out.println(blockingQueue.remove());
    }
}
