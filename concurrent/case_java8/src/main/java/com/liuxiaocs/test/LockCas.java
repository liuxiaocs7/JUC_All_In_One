package com.liuxiaocs.test;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;

import static com.liuxiaocs.n2.util.Sleeper.sleep;

/**
 * CAS通过标记位实现锁
 *
 * 不要用于生产实践!!!
 *
 * 13:26:11.849 [Thread-0] DEBUG c.LockCas - begin
 * 13:26:11.849 [Thread-1] DEBUG c.LockCas - begin...
 * 13:26:11.852 [Thread-0] DEBUG c.LockCas - lock...
 * 13:26:12.859 [Thread-0] DEBUG c.LockCas - unlock...
 * 13:26:12.859 [Thread-1] DEBUG c.LockCas - lock...
 * 13:26:12.859 [Thread-1] DEBUG c.LockCas - unlock...
 */
@Slf4j(topic = "c.LockCas")
public class LockCas {
    // 0没加锁；1加锁
    private AtomicInteger state = new AtomicInteger(0);

    public void lock() {
        while (true) {
            // 尝试将无锁状态变为加锁状态
            if (state.compareAndSet(0, 1)) {
                break;
            }
        }
    }

    public void unlock() {
        log.debug("unlock...");
        state.set(0);
    }

    public static void main(String[] args) {
        LockCas lock = new LockCas();
        new Thread(() -> {
            log.debug("begin");
            lock.lock();
            try {
                log.debug("lock...");
                sleep(1);
            } finally {
                lock.unlock();
            }
        }).start();

        new Thread(() -> {
            log.debug("begin...");
            lock.lock();
            try {
                log.debug("lock...");
            } finally {
                lock.unlock();
            }
        }).start();
    }
}
