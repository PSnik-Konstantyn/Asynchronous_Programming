package org.example.pz4;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class Natural {

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();

        CompletableFuture<int[]> sequenceFuture = CompletableFuture.supplyAsync(() -> {
            long start = System.currentTimeMillis();
            int[] sequence = IntStream.range(0, 20)
                    .map(i -> ThreadLocalRandom.current().nextInt(1, 101))
                    .toArray();
            printTime("Sequence generation", start);
            return sequence;
        });

        CompletableFuture<Integer> minSumFuture = sequenceFuture.thenApplyAsync(sequence -> {
            long start = System.currentTimeMillis();
            int minSum = IntStream.range(0, sequence.length - 1)
                    .map(i -> sequence[i] + sequence[i + 1])
                    .min()
                    .orElseThrow(() -> new RuntimeException("No min value found"));
            printTime("Minimum sum calculation", start);
            return minSum;
        });

        sequenceFuture.thenAcceptAsync(sequence -> {
            long start = System.currentTimeMillis();
            System.out.println("Original sequence: " + arrayToString(sequence));
            printTime("Print original sequence", start);
        });

        minSumFuture.thenAcceptAsync(minSum -> {
            long start = System.currentTimeMillis();
            System.out.println("Minimum sum of consecutive pairs: " + minSum);
            printTime("Print minimum sum", start);
        });

        minSumFuture.thenRunAsync(() -> {
            long totalTime = System.currentTimeMillis() - startTime;
            System.out.println("All tasks completed in: " + totalTime + " ms");
        });

        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private static String arrayToString(int[] array) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < array.length; i++) {
            sb.append(array[i]);
            if (i < array.length - 1) sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }

    private static void printTime(String taskName, long startTime) {
        long duration = System.currentTimeMillis() - startTime;
        System.out.println(taskName + " completed in: " + duration + " ms");
    }
}
