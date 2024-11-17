package org.example.pz3;

import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.List;
import java.util.ArrayList;

public class PairSumWorkDealing {

    public static void main(String[] args) throws Exception {
        System.err.println("work dealing");
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the size of the array:");
        int n = scanner.nextInt();

        System.out.println("Enter the minimum value for array elements:");
        int minValue = scanner.nextInt();

        System.out.println("Enter the maximum value for array elements:");
        int maxValue = scanner.nextInt();

        int[] array = new java.util.Random().ints(n, minValue, maxValue).toArray();

        System.out.println("Generated array of size " + n + " with values between " + minValue + " and " + maxValue);
        System.out.println(Arrays.toString(array));

        int numThreads = Runtime.getRuntime().availableProcessors();
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);

        List<Future<Long>> results = new ArrayList<>();
        int chunkSize = n / numThreads;

        long startTime = System.currentTimeMillis();

        for (int i = 0; i < numThreads; i++) {
            int start = i * chunkSize;
            int end = (i == numThreads - 1) ? n : start + chunkSize;

            results.add(executor.submit(() -> {
                long sum = 0;
                for (int j = start; j < end - 1; j++) {
                    sum += array[j] + array[j + 1];
                }
                return sum;
            }));
        }

        long totalSum = 0;
        for (Future<Long> result : results) {
            totalSum += result.get();
        }
        long endTime = System.currentTimeMillis();

        executor.shutdown();
        System.out.println("Result: " + totalSum);
        System.out.println("Time Work Dealing: " + (endTime - startTime) + "ms");
    }
}
