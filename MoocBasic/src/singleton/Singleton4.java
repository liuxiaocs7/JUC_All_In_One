package singleton;

/**
 * 懒汉式 (线程不安全) (不推荐)
 */
public class Singleton4 {
    private static Singleton4 instance;

    private Singleton4() {

    }

    // 多线程无法同时进入此方法，效率低
    public synchronized static Singleton4 getInstance() {
        if (instance == null) {
            instance = new Singleton4();
        }
        return instance;
    }
}
