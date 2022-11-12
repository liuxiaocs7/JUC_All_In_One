package background;

/**
 * 用工厂模式修复刚才的初始化问题
 */
public class MultiThreadsError7 {

    int count;
    private EventListener listener;

    private MultiThreadsError7(MySource source) {
        listener = new EventListener() {
            @Override
            public void onEvent(Event e) {
                System.out.println("\n我得到的数字是" + count);
            }
        };
        for (int i = 0; i < 10000; i++) {
            System.out.print(i);
        }
        count = 100;
    }

    public static MultiThreadsError7 getInstance(MySource source) {
        // 全部初始化完成
        MultiThreadsError7 safeListener = new MultiThreadsError7(source);
        // 注册
        source.registerListener(safeListener.listener);
        return safeListener;
    }

    public static void main(String[] args) {
        MySource mySource = new MySource();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // 子线程触发事件
                mySource.eventCome(new Event() {
                });
            }
        }).start();
        MultiThreadsError7 multiThreadsError5 = new MultiThreadsError7(mySource);
    }

    static class MySource {
        private EventListener listener;

        void registerListener(EventListener eventListener) {
            this.listener = eventListener;
        }

        void eventCome(Event e) {
            if (listener != null) {
                listener.onEvent(e);
            } else {
                System.out.println("还未初始化完毕");
            }
        }
    }

    interface EventListener {
        void onEvent(Event e);
    }

    interface Event {

    }
}
