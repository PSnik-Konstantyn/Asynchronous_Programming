package org.example.pz3;

import org.example.pz3.additional.PairSumTask;

import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.ForkJoinPool;

public class PairSumWorkStealing {
    public static void main(String[] args) {
        System.err.println("Work stealing");
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

        long startTime = System.currentTimeMillis();

        try (ForkJoinPool pool = new ForkJoinPool()) {
            long result = pool.invoke(new PairSumTask(array, 0, array.length));
            long endTime = System.currentTimeMillis();

            System.out.println("Result: " + result);
            System.out.println("Time Work Stealing: " + (endTime - startTime) + "ms");
        }

    }
}
