package jmm;

/**
 * 演示Synchronized可见性
 */
public class FieldVisibilitySyn {
    int a = 1;
    int b = 2;
    int c = 2;
    int d = 2;

    private void change() {
        a = 4;
        b = 4;
        c = 5;
        // 保证abcd都被修改了且被读取到
        synchronized (this) {
            d = 6;
        }
        // 解锁
    }

    private void print() {
        // 加锁
        synchronized (this) {
            int aa = a;
        }
        int bb = b;
        int cc = c;
        int dd = d;

    }

    public static void main(String[] args) {
        while (true) {
            FieldVisibilitySyn test = new FieldVisibilitySyn();
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
