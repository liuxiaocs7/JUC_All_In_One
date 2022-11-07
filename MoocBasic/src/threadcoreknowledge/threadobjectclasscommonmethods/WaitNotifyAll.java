package threadcoreknowledge.threadobjectclasscommonmethods;

/**
 * 3个线程，线程1和线程2首先被阻塞，线程3唤醒它们。notify, notifyAll。
 * start先执行不代表线程先启动。
 *
 * ThreadA got resourceA lock.
 * ThreadA waits to start.
 * ThreadB got resourceA lock.
 * ThreadB waits to start.
 * ThreadC notified.
 * ThreadB's waiting to end.
 * ThreadA's waiting to end.
 */
public class WaitNotifyAll implements Runnable {

    public static final Object resourceA = new Object();

    public static void main(String[] args) throws InterruptedException {
        Runnable r = new WaitNotifyAll();
        Thread threadA = new Thread(r, "ThreadA");
        Thread threadB = new Thread(r, "ThreadB");

        Thread threadC = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (resourceA) {
                    // A，B两个线程都被唤醒了
                    // resourceA.notify();
                    resourceA.notifyAll();
                    System.out.println("ThreadC notified.");
                }
            }
        });

        threadA.start();
        threadB.start();
        Thread.sleep(200);
        threadC.start();
    }

    @Override
    public void run() {
        synchronized (resourceA) {
            System.out.println(Thread.currentThread().getName() + " got resourceA lock.");
            try {
                System.out.println(Thread.currentThread().getName() + " waits to start.");
                resourceA.wait();
                System.out.println(Thread.currentThread().getName() + "'s waiting to end.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
