package threadcoreknowledge.createthreads;

/**
 * 同时使用 Runnable和Thread 两种实现线程的方式
 */
public class BothRunnableThread {
    public static void main(String[] args) {

        // 创建了一个匿名内部类，传入了一个Runnable对象，并重写了run()方法
        // 最后输出 ---> 我来自Thread
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("我来自Runnable");
            }
        }){
            @Override
            public void run() {
                System.out.println("我来自Thread");
            }
        }.start();
    }
}
