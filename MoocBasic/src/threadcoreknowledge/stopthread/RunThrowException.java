package threadcoreknowledge.stopthread;

/**
 * run()方法抛出checked Exception，只能用try/catch
 */
public class RunThrowException {

    // 普通方法直接在方法签名中抛出异常
    public void aVoid() throws Exception {
        throw new Exception();
    }

    // run() 方法
//    public static void main(String[] args) {
//        new Thread(new Runnable() {
//            @Override
//            public void run() throws Exception {
//                throw new Exception();
//            }
//        });
//    }
}
