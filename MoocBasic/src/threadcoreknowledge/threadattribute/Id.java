package threadcoreknowledge.threadattribute;

/**
 * ID从1开始，JVM运行起来后，我们自己创建的线程的ID早已不是0.
 */
public class Id {
    public static void main(String[] args) {
        Thread thread = new Thread();
        System.out.println("主线程的ID: " + Thread.currentThread().getId());  // 1
        System.out.println("子线程的ID: " + thread.getId());  // 20
    }
}


