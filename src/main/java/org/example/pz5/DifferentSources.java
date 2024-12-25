package org.example.pz5;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class DifferentSources {
    public static void main(String[] args) {
        CompletableFuture<String> sourceA = CompletableFuture.supplyAsync(() -> {
            simulateDelay(3000);
            return "Дані з джерела A";
        });

        CompletableFuture<String> sourceB = CompletableFuture.supplyAsync(() -> {
            simulateDelay(1500);
            return "Дані з джерела B";
        });

        CompletableFuture<String> sourceC = CompletableFuture.supplyAsync(() -> {
            simulateDelay(2000);
            return "Дані з джерела C";
        });

        CompletableFuture<String> processedData = sourceA.thenCompose(dataA ->
                CompletableFuture.supplyAsync(() -> {
                    return dataA + " оброблені";
                })
        );

        CompletableFuture<String> combinedData = sourceA.thenCombine(sourceB, (dataA, dataB) ->
                dataA + " + " + dataB
        ).thenCombine(sourceC, (partialResult, dataC) ->
                partialResult + " + " + dataC
        );

        CompletableFuture<Void> allTasks = CompletableFuture.allOf(sourceA, sourceB, sourceC);
        CompletableFuture<String> allResults = allTasks.thenApply(v -> {
            try {
                return sourceA.get() + " | " + sourceB.get() + " | " + sourceC.get();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        });

        CompletableFuture<Object> firstResult = CompletableFuture.anyOf(sourceA, sourceB, sourceC);

        try {
            System.out.println("Оброблені дані: " + processedData.get());
            System.out.println("Об'єднані дані: " + combinedData.get());
            System.out.println("Усі результати: " + allResults.get());
            System.out.println("Перший результат: " + firstResult.get());
        } catch (InterruptedException | ExecutionException e) {
            System.out.println("Упс! Помилка");
        }
    }

    private static void simulateDelay(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
