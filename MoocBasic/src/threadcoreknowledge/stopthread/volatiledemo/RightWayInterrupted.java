package threadcoreknowledge.stopthread.volatiledemo;

/**
 * 注意Thread.interrupted()方法的目标对象是"当前线程"，而不管方法来自于哪个对象
 */
public class RightWayInterrupted {

    public static void main(String[] args) throws InterruptedException {
        Thread threadOne = new Thread(new Runnable() {
            @Override
            public void run() {
                for (; ;) {

                }
            }
        });

        // 启动线程
        threadOne.start();
        // 设置中断表示
        threadOne.interrupt();
        // 获取中断标志【非静态】，不清除状态
        System.out.println("isInterrupted:" + threadOne.isInterrupted());   // true
        // 获取中断标志并重置【静态】，状态被清除了  [执行它的线程是main线程，main函数并没有被中断]
        System.out.println("isInterrupted:" + threadOne.interrupted());     // false
        // 获取中断标志并重置  [主线程的中断状态]
        System.out.println("isInterrupted:" + Thread.interrupted());        // false
        // 获取中断标志
        System.out.println("isInterrupted:" + threadOne.isInterrupted());   // true
        threadOne.join();
        System.out.println("Main thread is over.");
    }
}
