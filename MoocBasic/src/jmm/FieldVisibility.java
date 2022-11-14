package jmm;

/**
 * 演示可见性带来的问题
 *
 * a = 3, b = 3 (线程1先执行完)
 * a = 1, b = 2 (线程2先执行完)
 * a = 3, b = 2 (交替运行)
 * b = 3, a = 1
 */
public class FieldVisibility {
    int a = 1;
    int abc = 1;
    int abcd = 1;
    volatile int b = 2;

    private void change() {
        // 写入之前的操作
        abc = 7;
        abcd = 70;
        a = 3;
        // 写入操作
        b = 0;
    }

    private void print() {
        if (b == 0) {
            System.out.println("a = " + a);
            System.out.println("abc = " + abc);
            System.out.println("abcd = " + abcd);
        }
        System.out.println("b = " + b + ", a = " + a);
    }

    public static void main(String[] args) {
        while (true) {
            FieldVisibility test = new FieldVisibility();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    test.change();
                }
            }).start();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    test.print();
                }
            }).start();
        }
    }
}
