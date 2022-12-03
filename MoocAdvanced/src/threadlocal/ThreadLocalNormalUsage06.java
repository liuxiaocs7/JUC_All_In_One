package threadlocal;

/**
 * 演示ThreadLocal用法2：避免传递参数的麻烦
 */
public class ThreadLocalNormalUsage06 {
    public static void main(String[] args) {
        new Service1().process();
    }
}

class Service1 {
    public void process() {
        User user = new User("超哥");
        UserContextHolder.holder.set(user);
        new Service2().process();
        new Service3().process();
    }
}

class Service2 {
    public void process() {
        User user = UserContextHolder.holder.get();
        // ThreadSafeFormatter.dateFormatThreadLocal.get();
        System.out.println("Service2拿到用户名: " + user.name);
        // 清空保存的对象
        UserContextHolder.holder.remove();
        user = new User("王姐");
        // 重新设置
        UserContextHolder.holder.set(user);
    }
}

class Service3 {
    public void process() {
        User user = UserContextHolder.holder.get();
        System.out.println("Service3拿到用户名: " + user.name);
        // 避免内存泄漏
        UserContextHolder.holder.remove();
    }
}

// 这里是通过set方法设置值的
class UserContextHolder {
    public static ThreadLocal<User> holder = new ThreadLocal<>();
}

class User {
    String name;

    public User(String name) {
        this.name = name;
    }
}
