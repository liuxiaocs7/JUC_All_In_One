package threadcoreknowledge.threadobjectclasscommonmethods;

/**
 * 展示wait和notify的基本用法
 * 1. 研究代码执行顺序
 * 2. 证明wait释放锁
 *
 * Thread-0开始执行了
 * 线程Thread-1调用了notify()
 * 线程Thread-0获取到了锁。
 */
public class Wait {
    public static final Object object = new Object();

    static class Thread1 extends Thread {
        @Override
        public void run() {
            // 获取同步监视器
            synchronized (object) {
                System.out.println(Thread.currentThread().getName() + "开始执行了");
                try {
                    // 释放锁，等待被唤醒
                    object.wait();
                    // 如果在等待期间遇到了中断，需要处理异常
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // 重新获得了锁，继续执行后续代码
                System.out.println("线程" + Thread.currentThread().getName() + "获取到了锁。");
            }
        }
    }

    static class Thread2 extends Thread {

        @Override
        public void run() {
            synchronized (object) {
                object.notify();
                // 此时还没有释放锁，同步代码块里面所有语句执行完毕之后才会释放
                System.out.println("线程" + Thread.currentThread().getName() + "调用了notify()");
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        // 保证wait()先执行，notify()后执行
        Thread1 thread1 = new Thread1();
        Thread2 thread2 = new Thread2();
        thread1.start();
        Thread.sleep(200);
        thread2.start();
    }
}
