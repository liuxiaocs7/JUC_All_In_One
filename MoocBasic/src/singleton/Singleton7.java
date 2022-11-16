package singleton;

/**
 * 静态内部类的方式 可用
 * 懒汉  需要的时候才被加载
 */
public class Singleton7 {

    private Singleton7() {

    }

    private static class SingletonInstance {
        private static final Singleton7 INSTANCE = new Singleton7();
    }

    public static Singleton7 getInstance(){
        return SingletonInstance.INSTANCE;
    }
}
