package threadcoreknowledge.stopthread.volatiledemo;

import sun.nio.ch.Interruptible;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * 用中断来修复刚才的无尽等待问题
 */
public class WrongWayVolatileFixed {

    public static void main(String[] args) throws InterruptedException {

        WrongWayVolatileFixed body = new WrongWayVolatileFixed();
        ArrayBlockingQueue storage = new ArrayBlockingQueue(10);

        // 使用body来实例化内部类
        Producer producer = body.new Producer(storage);
        Thread producerThread = new Thread(producer);
        // 生产者线程可以将阻塞队列塞满了(10个元素)
        producerThread.start();
        Thread.sleep(1000);

        Consumer consumer = body.new Consumer(storage);
        while(consumer.needMoreNums()) {
            System.out.println(consumer.storage.take() + "被消费了");
            Thread.sleep(100);
        }
        System.out.println("消费者不需要更多数据了。");
        // 一旦消费不需要更多数据了，我们应该让生产者也停下来
        producerThread.interrupt();
        System.out.println(producer.canceled);

        Thread.interrupted();
        producerThread.isInterrupted();
    }

    class Producer implements Runnable {

        protected volatile boolean canceled = false;
        BlockingQueue storage;

        public Producer(BlockingQueue storage) {
            this.storage = storage;
        }

        @Override
        public void run() {
            int num = 0;
            try {
                while (num <= 100000 && !Thread.currentThread().isInterrupted()) {
                    if (num % 100 == 0) {
                        storage.put(num);
                        System.out.println(num + "是100的倍数，被放到仓库中了。");
                    }
                    num++;
                }
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println("生产者停止运行");
            }
        }
    }

    class Consumer {
        BlockingQueue storage;

        public Consumer(BlockingQueue storage) {
            this.storage = storage;
        }

        public boolean needMoreNums() {
            if(Math.random() > 0.95) {
                return false;
            }
            return true;
        }
    }
}
