package com.liuxiaocs.test;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicMarkableReference;

import static com.liuxiaocs.n2.util.Sleeper.sleep;

/**
 * 原子引用：AtomicMarkableReference
 *
 * 16:06:57.786 [main] DEBUG c.Test38 - start...
 * 16:06:57.788 [main] DEBUG c.Test38 - com.liuxiaocs.test.GarbageBag@27fa135a 装满了垃圾
 * 16:06:58.795 [main] DEBUG c.Test38 - 想换一只新垃圾袋
 * 16:06:58.795 [main] DEBUG c.Test38 - 换了么?true
 * 16:06:58.795 [main] DEBUG c.Test38 - com.liuxiaocs.test.GarbageBag@46f7f36a 空垃圾袋
 *
 * 16:09:13.393 [main] DEBUG c.Test38 - start...
 * 16:09:13.395 [main] DEBUG c.Test38 - com.liuxiaocs.test.GarbageBag@27fa135a 装满了垃圾
 * 16:09:13.422 [Thread-0] DEBUG c.Test38 - start...
 * 16:09:13.422 [Thread-0] DEBUG c.Test38 - com.liuxiaocs.test.GarbageBag@27fa135a 空垃圾袋
 * 16:09:14.424 [main] DEBUG c.Test38 - 想换一只新垃圾袋
 * 16:09:14.424 [main] DEBUG c.Test38 - 换了么?false
 * 16:09:14.424 [main] DEBUG c.Test38 - com.liuxiaocs.test.GarbageBag@27fa135a 空垃圾袋
 */
@Slf4j(topic = "c.Test38")
public class Test38 {
    public static void main(String[] args) {
        GarbageBag bag = new GarbageBag("装满了垃圾");
        // 参数2 mark可以看作一个标记，表示垃圾袋装满了
        AtomicMarkableReference<GarbageBag> ref = new AtomicMarkableReference<>(bag, true);

        log.debug("start...");
        GarbageBag prev = ref.getReference();
        log.debug(prev.toString());

        // 将垃圾袋重新置空
        new Thread(() -> {
            log.debug("start...");
            bag.setDesc("空垃圾袋");
            // 改变状态
            ref.compareAndSet(bag, bag, true, false);
            log.debug(bag.toString());
        }).start();

        sleep(1);
        log.debug("想换一只新垃圾袋");
        // 原来的是满的，替换完之后就不是满的了
        boolean success = ref.compareAndSet(prev, new GarbageBag("空垃圾袋"), true, false);
        log.debug("换了么?" + success);
        log.debug(ref.getReference().toString());
    }
}

class GarbageBag {
    String desc;

    public GarbageBag(String desc) {
        this.desc = desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return super.toString() + " " + desc;
    }
}
