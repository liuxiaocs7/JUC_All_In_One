package threadcoreknowledge.sixstates;

/**
 * 展示线程的NEW、RUNNABLE、Terminated状态。
 * 即使是正在运行，也是Runnable状态而不是Running。
 */
public class NewRunnableTerminated implements Runnable {

    public static void main(String[] args) {
        Thread thread = new Thread(new NewRunnableTerminated());
        // 打印出NEW的状态
        System.out.println(thread.getState());  // NEW
        thread.start();
        System.out.println(thread.getState());  // RUNNABLE
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 打印出RUNNABLE的状态，即使是正在运行，也是RUNNABLE，而不是RUNNING
        System.out.println(thread.getState());

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 打印出TERMINATED的状态
        System.out.println(thread.getState());
    }

    @Override
    public void run() {
        for (int i = 0; i < 10000; i++) {
            System.out.println(i);
        }
    }
}
