package jmm;

import java.util.concurrent.CountDownLatch;

/**
 * 演示重排序的现象
 * 直到达到某个条件才停止，测试小概率事件
 *
 * case1: x = 0, y = 1
 * case2: x = 1, y = 0
 * case3: x = 1, y = 1
 */
public class OutOfOrderExecution {
    private volatile static int x = 0, y = 0;
    private volatile static int a = 0, b = 0;

    public static void main(String[] args) throws InterruptedException {
        // case1();
        // case2();

        // case3
        for (int i = 0; ; i++) {
            x = 0; y = 0;
            a = 0; b = 0;
            CountDownLatch latch = new CountDownLatch(1);
            Thread one = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        latch.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    a = 1;
                    x = b;
                }
            });

            Thread two = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        latch.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    b = 1;
                    y = a;
                }
            });
            one.start();
            two.start();
            latch.countDown();
            one.join();
            two.join();
            String result = "第" + i + "次 (" + x + "," + y + ")";
            System.out.println(result);
            if (x == 1 && y == 1) break;

            if (x == 0 && y == 0) break;
        }
    }

    /**
     * 原始情况
     */
    private static void case1() throws InterruptedException {
        Thread one = new Thread(new Runnable() {
            @Override
            public void run() {
                a = 1;
                x = b;
            }
        });

        Thread two = new Thread(new Runnable() {
            @Override
            public void run() {
                b = 1;
                y = a;
            }
        });
        one.start();
        two.start();
        one.join();
        two.join();
        System.out.println("x = " + x + ", y = " + y);
    }

    /**
     * 尝试调换顺序解决  错误
     */
    private static void case2() throws InterruptedException {
        Thread one = new Thread(new Runnable() {
            @Override
            public void run() {
                a = 1;
                x = b;
            }
        });

        Thread two = new Thread(new Runnable() {
            @Override
            public void run() {
                b = 1;
                y = a;
            }
        });
        // 交换顺序
        two.start();
        one.start();
        one.join();
        two.join();
        System.out.println("x = " + x + ", y = " + y);
    }
}
