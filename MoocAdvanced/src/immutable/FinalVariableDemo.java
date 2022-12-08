package immutable;

/**
 * 演示final变量
 */
public class FinalVariableDemo {
    // 1.直接赋值
    // private final int a = 6;
    private final int a;

    // static 直接赋值
    // private final static int b = 5;
    private final static int b;

    // 静态代码块赋值
    static {
        b = 5;
    }

    // 2.构造函数赋值
    // public FinalVariableDemo(int a) {
    //     this.a = a;
    // }

    // 3.代码块赋值
    {
        a = 6;
    }

    void testFinal() {
        final int c;
    }
}
