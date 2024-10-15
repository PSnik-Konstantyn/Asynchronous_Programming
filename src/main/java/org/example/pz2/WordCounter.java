package org.example.pz2;

import java.util.concurrent.*;
import java.util.*;

public class WordCounter {

    public static void main(String[] args) throws InterruptedException {
        String text = "Тут тут багато тексту тексту тексту ага угук угук";

        ConcurrentHashMap<String, Integer> wordCounts = new ConcurrentHashMap<>();

        List<String> words = Arrays.asList(text.split("\\s+"));

        int numParts = 2;
        List<List<String>> partitions = partition(words, numParts);

        ExecutorService executor = Executors.newFixedThreadPool(numParts);
        List<Future<Void>> futures = new ArrayList<>();

        for (List<String> partition : partitions) {
            Callable<Void> task = () -> {
                for (String word : partition) {
                    wordCounts.merge(word.toLowerCase(), 1, Integer::sum);
                }
                return null;
            };

            futures.add(executor.submit(task));
        }

        for (Future<Void> future : futures) {
            while (!future.isDone()) {
                Thread.sleep(1);
                System.out.println("Завдання ще виконується...");
            }
        }

        executor.shutdown();


        System.out.println("Частота слів:");
        wordCounts.forEach((word, count) -> System.out.println(word + ": " + count));
    }

    private static List<List<String>> partition(List<String> list, int numParts) {
        int size = list.size();
        int chunkSize = (size + numParts - 1) / numParts;
        List<List<String>> partitions = new ArrayList<>();

        for (int i = 0; i < size; i += chunkSize) {
            partitions.add(list.subList(i, Math.min(size, i + chunkSize)));
        }

        return partitions;
    }
}
