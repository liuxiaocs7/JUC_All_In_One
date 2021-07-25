package com.liuxiaocs.forkjoin;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

class MyTask extends RecursiveTask<Integer> {

    // 拆分差值不能超过10，计算10以内的运算
    private static final Integer VALUE = 10;
    // 拆分开始值
    private final int begin;
    // 拆分结束值
    private final int end;
    // 返回结果
    private int result;

    // 创建有参构造
    public MyTask(int begin, int end) {
        this.begin = begin;
        this.end = end;
    }

    // 拆分和合并过程
    @Override
    protected Integer compute() {
        // 判断相加的两个数值是否大于10
        if((end - begin) <= VALUE) {
            // 直接进行相加操作
            for (int i = begin; i <= end; i++) {
                result += i;
            }
        } else {
            // 进一步做拆分
            // 获取中间值
            int middle = (begin + end) / 2;
            // 拆分左边部分
            MyTask task01 = new MyTask(begin, middle);
            // 拆分右边部分
            MyTask task02 = new MyTask(middle + 1, end);
            // 调用方法拆分(fork)
            task01.fork();
            task02.fork();
            // 合并(join)结果
            result = task01.join() + task02.join();
        }
        return result;
    }
}

public class ForkJoinDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 创建MyTask对象
        MyTask myTask = new MyTask(0, 100);
        // 创建分支合并池对象
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        // 提交任务
        ForkJoinTask<Integer> forkJoinTask = forkJoinPool.submit(myTask);
        // 获取最终合并之后的结果
        Integer result = forkJoinTask.get();
        System.out.println(result);
        // 关闭池对象
        forkJoinPool.shutdown();
    }
}
