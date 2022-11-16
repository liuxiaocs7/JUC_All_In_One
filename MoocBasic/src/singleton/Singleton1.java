package singleton;

/**
 * 饿汉式(静态常量) (可用)
 *
 * 优点:
 *      1. 写法简单
 *      2. 类加载的时候将对象实例化完毕，避免了线程同步的问题 (类的加载由JVM保证线程安全)
 */
public class Singleton1 {
    private final static Singleton1 INSTANCE = new Singleton1();

    private Singleton1() {

    }

    public static Singleton1 getInstance() {
        return INSTANCE;
    }
}
