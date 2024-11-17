package org.example.pz3.additional;

import java.util.concurrent.RecursiveTask;

public class PairSumTask extends RecursiveTask<Long> {
    private final int[] array;
    private final int start, end;
    private static final int THRESHOLD = 100;

    public PairSumTask(int[] array, int start, int end) {
        this.array = array;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        if (end - start <= THRESHOLD) {
            long sum = 0;
            for (int i = start; i < end - 1; i++) {
                sum += array[i] + array[i + 1];
            }
            return sum;
        } else {
            int mid = (start + end) / 2;
            PairSumTask leftTask = new PairSumTask(array, start, mid);
            PairSumTask rightTask = new PairSumTask(array, mid, end);
            leftTask.fork();
            return rightTask.compute() + leftTask.join();
        }
    }
}