package com.liuxiaocs.test;

import com.liuxiaocs.n2.util.Sleeper;
import javafx.scene.layout.HBox;
import lombok.extern.slf4j.Slf4j;

import java.util.Hashtable;
import java.util.Map;
import java.util.Set;


/**
 * 保护性暂停
 *
 * 增强
 *
 * 等待者和结果产生者是一一对应的关系
 *
 * 00:42:29.934 [Thread-2] DEBUG c.People - 开始收信 id:2
 * 00:42:29.934 [Thread-1] DEBUG c.People - 开始收信 id:3
 * 00:42:29.934 [Thread-0] DEBUG c.People - 开始收信 id:1
 * 00:42:30.939 [Thread-5] DEBUG c.PostMan - 送信 id:1, 内容:内容1
 * 00:42:30.939 [Thread-3] DEBUG c.PostMan - 送信 id:3, 内容:内容3
 * 00:42:30.939 [Thread-4] DEBUG c.PostMan - 送信 id:2, 内容:内容2
 * 00:42:30.940 [Thread-0] DEBUG c.People - 收到信 id:1，内容:内容1
 * 00:42:30.940 [Thread-1] DEBUG c.People - 收到信 id:3，内容:内容3
 * 00:42:30.940 [Thread-2] DEBUG c.People - 收到信 id:2，内容:内容2
 */
@Slf4j(topic = "c.Test20_2")
public class Test20_2 {
    public static void main(String[] args) {
        // 模拟三个居民等着收信
        for (int i = 0; i < 3; i++) {
            new People().start();
        }

        Sleeper.sleep(1);
        for (Integer id : Mailboxes.getIds()) {
            // 找一个邮递员送信
            new PostMan(id, "内容" + id).start();
        }
    }
}

@Slf4j(topic = "c.People")
class People extends Thread {
    @Override
    public void run() {
        // 收信
        GuardedObject2 guardedObject = Mailboxes.createGuardedObject();
        log.debug("开始收信 id:{}", guardedObject.getId());
        Object mail = guardedObject.get(5000);
        log.debug("收到信 id:{}，内容:{}", guardedObject.getId(), mail);
    }
}

/**
 * 邮递员类
 */
@Slf4j(topic = "c.PostMan")
class PostMan extends Thread{

    private int id;
    private String mail;

    public PostMan(int id, String mail) {
        this.id = id;
        this.mail = mail;
    }

    @Override
    public void run() {
        GuardedObject2 guardedObject = Mailboxes.getGuardObject(id);
        log.debug("送信 id:{}, 内容:{}", id, mail);
        guardedObject.complete(mail);
    }
}

/**
 * 邮箱类
 */
class Mailboxes {
    private static Map<Integer, GuardedObject2> boxes = new Hashtable<>();

    private static int id = 1;

    // 产生唯一id(防止线程安全性，相当于对类对象加锁)
    private static synchronized int generateId() {
        return id++;
    }

    public static GuardedObject2 getGuardObject(int id) {
        return boxes.remove(id);
    }

    public static GuardedObject2 createGuardedObject() {
        GuardedObject2 go = new GuardedObject2(generateId());
        boxes.put(go.getId(), go);
        return go;
    }

    public static Set<Integer> getIds() {
        return boxes.keySet();
    }
}

/**
 * 增加超时效果
 */
class GuardedObject2 {

    // 标识 Guarded Object
    private int id;

    public GuardedObject2(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    // 结果
    private Object response;

    // 获取结果
    // timeout表示最多要等待多久
    public Object get(long timeout) {
        synchronized (this) {
            // 开始时间
            long begin = System.currentTimeMillis();
            // 经历的时间
            long passedTime = 0;
            // 没有结果
            while(response == null) {
                // 这一轮循环应该等待的时间
                long waitTime = timeout - passedTime;
                // 经历的时间超过了最大等待时间时，退出循环
                if(waitTime <= 0) {
                    break;
                }
                try {
                    this.wait(waitTime);  // 虚假唤醒
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // 求得经历时间
                passedTime = System.currentTimeMillis() - begin;
            }
            return response;
        }
    }

    // 产生结果
    public void complete(Object response) {
        synchronized (this) {
            // 给结果成员变量赋值
            this.response = response;
            this.notifyAll();
        }
    }
}
