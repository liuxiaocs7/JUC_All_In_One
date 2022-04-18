package com.liuxiaocs.n4.exercise;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Vector;

/**
 * 测试卖票问题
 *
 * 23:31:11.066 [main] DEBUG c.ExerciseSell - 余票: 3958
 * 23:31:11.073 [main] DEBUG c.ExerciseSell - 卖出的票数: 6045
 */
@Slf4j(topic = "c.ExerciseSell")
public class ExerciseSell {
    public static void main(String[] args) throws InterruptedException {
        // 模拟多人买票
        // 一共有1000张票
        TicketWindow window = new TicketWindow(1000);

        // 所有线程的集合
        List<Thread> threadList = new ArrayList<>();
        // 卖出的票数统计
        List<Integer> amountList = new Vector<>();
        for (int i = 0; i < 2000; i++) {
            Thread thread = new Thread(() -> {
                // 买票
                // sell方法对共享变量有读写操作
                // sell方法和add方法组合是没有问题的，因为这两个线程的共享变量(window/amountList)是不同的
                int amount = window.sell(randomAmount());
                // try {
                //     Thread.sleep(randomAmount());
                // } catch (InterruptedException e) {
                //     e.printStackTrace();
                // }
                // add方法需要保护，但是由于Vector内部已经同步保护了
                amountList.add(amount);
            });
            // 主线程中执行的，直接使用ArrayList即可
            threadList.add(thread);
            thread.start();
        }
        // 等待所有的线程结束
        for(Thread thread : threadList) {
            thread.join();
        }

        // 统计卖出的票数和剩余的票数
        log.debug("余票: {}", window.getCount());
        log.debug("卖出的票数: {}", amountList.stream().mapToInt(i->i).sum());
    }

    // Random为线程安全
    static Random random = new Random();

    // 随机 1~5
    public static int randomAmount() {
        return random.nextInt(5) + 1;
    }
}

// 售票窗口
class TicketWindow {
    private int count;

    public TicketWindow(int count) {
        this.count = count;
    }

    // 获取余票数量
    public int getCount() {
        return count;
    }

    // 售票
    // 这里是临界区的代码需要进行保护
    public synchronized int sell(int amount) {
        if(this.count >= amount) {
            this.count -= amount;
            return amount;
        } else {
            return 0;
        }
    }
}