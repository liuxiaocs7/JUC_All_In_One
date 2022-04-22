package com.liuxiaocs.test;

import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;

import static com.liuxiaocs.n2.util.Sleeper.sleep;

/**
 * 消息队列
 *
 * lambda引用的数必须是final的
 *
 * 01:16:58.593 [生产者0] DEBUG c.MessageQueue - 已生产消息 Message{id=0, value=值0}
 * 01:16:58.595 [生产者2] DEBUG c.MessageQueue - 已生产消息 Message{id=2, value=值2}
 * 01:16:58.595 [生产者1] DEBUG c.MessageQueue - 队列已满，生产者线程等待
 * 01:16:59.599 [消费者] DEBUG c.MessageQueue - 已消费消息 Message{id=0, value=值0}
 * 01:16:59.599 [生产者1] DEBUG c.MessageQueue - 已生产消息 Message{id=1, value=值1}
 * 01:17:00.608 [消费者] DEBUG c.MessageQueue - 已消费消息 Message{id=2, value=值2}
 * 01:17:01.616 [消费者] DEBUG c.MessageQueue - 已消费消息 Message{id=1, value=值1}
 * 01:17:02.624 [消费者] DEBUG c.MessageQueue - 队列为空，消费者线程等待
 */
@Slf4j(topic = "c.Test21")
public class Test21 {
    public static void main(String[] args) {
        // 消息队列
        MessageQueue queue = new MessageQueue(2);

        for (int i = 0; i < 3; i++) {
            int id = i;
            new Thread(() -> {
                queue.put(new Message(id, "值" + id));
            }, "生产者" + i).start();
        }

        // 消费者源源不断取消息
        new Thread(() -> {
            while(true) {
                sleep(1);
                Message message = queue.take();
            }
        }, "消费者").start();
    }
}

// 消息队列类，Java进程之间通信
@Slf4j(topic = "c.MessageQueue")
class MessageQueue {

    // 消息的队列集合
    private LinkedList<Message> list = new LinkedList<>();
    // 消息的容量
    private int capacity;

    public MessageQueue(int capacity) {
        this.capacity = capacity;
    }

    // 获取消息
    public Message take() {
        // 检查队列是否为空，在list对象上等待
        synchronized (list) {
            while(list.isEmpty()) {
                try {
                    log.debug("队列为空，消费者线程等待");
                    list.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            // 从队列头部获取消息并返回
            Message message = list.removeFirst();
            log.debug("已消费消息 {}", message);
            list.notifyAll();
            return message;
        }

    }

    // 存入消息
    public void put(Message message) {
        synchronized (list) {
            // 检查队列是否已满
            while(list.size() == capacity) {
                try {
                    log.debug("队列已满，生产者线程等待");
                    list.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            // 将消息加入队列尾部
            list.addLast(message);
            log.debug("已生产消息 {}", message);
            list.notifyAll();
        }
    }
}

// 内部状态不能修改，线程安全的
final class Message {
    private int id;
    private Object value;

    public Message(int id, Object value) {
        this.id = id;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public Object getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", value=" + value +
                '}';
    }
}