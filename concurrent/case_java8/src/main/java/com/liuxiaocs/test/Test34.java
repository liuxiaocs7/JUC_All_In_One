package com.liuxiaocs.test;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntUnaryOperator;

/**
 * 测试原子类的使用
 */
public class Test34 {
    public static void main(String[] args) {
        AtomicInteger i = new AtomicInteger(5);

        // 自增并获取值
        // System.out.println(i.incrementAndGet());  // ++i  打印1，i = 1
        // System.out.println(i.getAndIncrement());  // i++  打印1，i = 2
        // System.out.println(i.get());
        //
        // System.out.println(i.getAndAdd(5));  // 打印2，i=7
        // System.out.println(i.addAndGet(5));  // 打印12，i=12

        // 参数是读取到的值，结果是要设置的值
        // i.updateAndGet(value -> value * 10);

        // 使用compareAndSet实现updateAndGet

        System.out.println(updateAndGet(i, operand -> operand * 10));

        System.out.println(i.get());
    }

    // 将计算的操作当作一个变化的变量传递进来
    public static int updateAndGet(AtomicInteger i, IntUnaryOperator operator) {
        while (true) {
            int prev = i.get();
            int next = operator.applyAsInt(prev);
            if (i.compareAndSet(prev, next)) {
                return next;
            }
        }
    }
}
