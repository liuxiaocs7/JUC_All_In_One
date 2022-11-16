package singleton;

/**
 * 双重检查 推荐面试使用
 */
public class Singleton6 {
    // 必须要有可见性
    private volatile static Singleton6 instance;

    private Singleton6() {

    }

    public static Singleton6 getInstance() {
        if (instance == null) {
            synchronized (Singleton6.class) {
                // 快捷键 ifn
                if (instance == null) {
                    instance = new Singleton6();
                }
            }
        }
        return instance;
    }
}
