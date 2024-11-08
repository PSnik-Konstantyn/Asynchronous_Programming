package org.example.pz2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.*;

public class ArrayProcessing {

    public static void main(String[] args) {
        int lowerBound = -100;
        int upperBound = 100;
        int arraySize = 60;

        // Генерація масиву випадкових чисел
        int[] array = generateRandomArray(arraySize, lowerBound, upperBound);

        System.out.println("Вихідний масив:");
        for (int num : array) {
            System.out.print(num + " ");
        }
        System.out.println();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введіть множник: ");
        int multiplier = scanner.nextInt();

        // Початок обчислення часу
        long startTime = System.currentTimeMillis();
        System.err.println("Початок обчислення");

        List<Integer> resultList = new ArrayList<>();
        List<Future<List<Integer>>> futures = new ArrayList<>();
        ExecutorService executorService = Executors.newFixedThreadPool(4);

        int partSize = array.length / 4; // Кількість елементів в кожній частині
        for (int i = 0; i < 4; i++) {
            int start = i * partSize;
            int end = (i == 3) ? array.length : start + partSize;

            // Створення Callable для обробки частини масиву
            Callable<List<Integer>> task = new ArrayMultiplier(array, start, end, multiplier);
            Future<List<Integer>> future = executorService.submit(task);
            futures.add(future);
        }

        // Отримання результатів обробки та перевірка стану завдань
        for (Future<List<Integer>> future : futures) {
            try {
                // Перевірка, чи підзавдання завершено
                if (future.isDone()) {
                    // Отримання результату та додавання до resultList
                    resultList.addAll(future.get());
                }

                if (!future.isCancelled()) {
                    // Додаємо що є
                    resultList.addAll(future.get());
                }
            } catch (InterruptedException | ExecutionException e) {
                System.out.println("Виникла помилка!");
            }
        }

        System.out.println("Масив після множення:");
        for (int num : resultList) {
            System.out.print(num + " ");
        }

        // Обчислення часу виконання
        long endTime = System.currentTimeMillis();
        System.out.println("\nЧас роботи програми: " + (endTime - startTime) + " мс");

        executorService.shutdown();
    }

    private static int[] generateRandomArray(int size, int lowerBound, int upperBound) {
        int[] array = new int[size];
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt(upperBound - lowerBound + 1) + lowerBound;
        }
        return array;
    }
}