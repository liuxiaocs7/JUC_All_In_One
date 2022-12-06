package atomic;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.LongAccumulator;
import java.util.stream.IntStream;

/**
 * 演示LongAccumulator用法
 */
public class LongAccumulatorDemo {
    public static void main(String[] args) {
        // LongAccumulator accumulator = new LongAccumulator((x, y) -> x + y, 0);
        // LongAccumulator accumulator = new LongAccumulator((x, y) -> Math.max(x, y), 0);
        LongAccumulator accumulator = new LongAccumulator((x, y) -> x * y, 1);
        // accumulator.accumulate(1);
        // accumulator.accumulate(2);
        ExecutorService executor = Executors.newFixedThreadPool(8);
        IntStream.range(1, 10).forEach(i -> executor.submit(() -> accumulator.accumulate(i)));
        executor.shutdown();
        while (!executor.isTerminated()) {

        }
        System.out.println(accumulator.getThenReset());
    }
}
