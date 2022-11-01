package threadcoreknowledge.createthreads.wrongways;

/**
 * Lambda方式
 */
public class Lambda {
    public static void main(String[] args) {
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName());
        }).start();
    }
}
