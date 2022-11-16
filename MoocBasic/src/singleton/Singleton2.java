package singleton;

/**
 * 饿汉式 (静态代码块) (可用)
 *
 * 优点:
 *      1. 写法简单
 *      2. 类加载的时候将对象实例化完毕，避免了线程同步的问题 (类的加载由JVM保证线程安全)
 */
public class Singleton2 {
    private final static Singleton2 INSTANCE;

    static {
        INSTANCE = new Singleton2();
    }

    private Singleton2() {

    }

    public static Singleton2 getInstance() {
        return INSTANCE;
    }
}
