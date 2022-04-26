package com.liuxiaocs.test;

import com.liuxiaocs.n4.UnsafeAccessor;
import lombok.extern.slf4j.Slf4j;
import sun.misc.Unsafe;

/**
 * 模拟实现原子整数
 *
 * 0 cost: 53 ms
 */
@Slf4j(topic = "c.Test42")
public class Test42 {
    public static void main(String[] args) {
        Account.demo(new MyAtomicInteger(10000));
    }
}

/**
 * 自定义原子性
 */
class MyAtomicInteger implements Account {
    private volatile int value;
    public static final long valueOffset;
    private static final Unsafe UNSAFE;
    static {
        UNSAFE = UnsafeAccessor.getUnsafe();
        try {
            valueOffset = UNSAFE.objectFieldOffset(MyAtomicInteger.class.getDeclaredField("value"));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public int getValue() {
        return value;
    }

    public void decrement(int amount) {
        while (true) {
            int prev = this.value;
            int next = prev - amount;
            if (UNSAFE.compareAndSwapInt(this, valueOffset, prev, next)) {
                break;
            }
        }
    }

    public MyAtomicInteger(int value) {
        this.value = value;
    }

    @Override
    public Integer getBalance() {
        return getValue();
    }

    @Override
    public void withdraw(Integer amount) {
        decrement(amount);
    }
}
