package org.example.pz4;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class Factorial {

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        CompletableFuture<int[]> arrayFuture = CompletableFuture.supplyAsync(() -> {
            long start = System.currentTimeMillis();
            int[] array = IntStream.range(0, 10)
                    .map(i -> ThreadLocalRandom.current().nextInt(1, 101))
                    .toArray();
            printTime("Array generation", start);
            return array;
        });

        CompletableFuture<int[]> incrementedArrayFuture = arrayFuture.thenApplyAsync(array -> {
            long start = System.currentTimeMillis();
            int[] incrementedArray = IntStream.of(array)
                    .map(i -> i + 5)
                    .toArray();
            printTime("Array increment", start);
            return incrementedArray;
        });

        CompletableFuture<Long> factorialFuture = incrementedArrayFuture.thenCombineAsync(arrayFuture, (incrementedArray, originalArray) -> {
            long start = System.currentTimeMillis();
            int sum1 = IntStream.of(incrementedArray).sum();
            int sum2 = IntStream.of(originalArray).sum();
            long factorial = calculateFactorial(sum1 + sum2);
            printTime("Factorial calculation", start);
            return factorial;
        });

        arrayFuture.thenAcceptAsync(array -> {
            long start = System.currentTimeMillis();
            System.out.println("Original array: " + arrayToString(array));
            printTime("Print original array", start);
        });

        incrementedArrayFuture.thenAcceptAsync(array -> {
            long start = System.currentTimeMillis();
            System.out.println("Incremented array: " + arrayToString(array));
            printTime("Print incremented array", start);
        });

        factorialFuture.thenAcceptAsync(factorial -> {
            long start = System.currentTimeMillis();
            System.out.println("Factorial result: " + factorial);
            printTime("Print factorial result", start);
        });

        factorialFuture.thenRunAsync(() -> {
            long totalTime = System.currentTimeMillis() - startTime;
            System.out.println("All tasks completed in: " + totalTime + " ms");
        });

        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private static long calculateFactorial(int number) {
        long result = 1;
        for (int i = 2; i <= number; i++) {
            result *= i;
        }
        return result;
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
