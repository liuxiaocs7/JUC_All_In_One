package singleton;

/**
 * 懒汉式 (线程不安全) (不可用)
 */
public class Singleton5 {
    private static Singleton5 instance;

    private Singleton5() {

    }

    public static Singleton5 getInstance() {
        if (instance == null) {
            // 两个线程同时进入if判断，第一个线程拿到锁之后创建实例，第二个线程也同样会拿到锁后创建实例
            synchronized (Singleton5.class) {
                instance = new Singleton5();
            }
        }
        return instance;
    }
}
