package org.example.task4;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class Task {
    public static void main(String[] args) {

        CompletableFuture.runAsync(() ->
                System.err.println("Старт обробки списку товарів")
        ).thenCompose(aVoid -> CompletableFuture.supplyAsync(() -> {
            System.out.println("Отримання списку товарів...");
            return List.of("молоко", "хліб", "цукор", "сіль", "олія", "апельсини");
        })).thenCompose(items ->
                CompletableFuture.supplyAsync(() -> {
                    System.out.println("Обробка товарів: конвертація назв у верхній регістр");
                    return items.stream()
                            .map(String::toUpperCase)
                            .collect(Collectors.toList());
                })
        ).thenCompose(processedItems ->
                CompletableFuture.supplyAsync(() -> {
                    System.out.println("Додаткова обробка: сортування списку");
                    return processedItems.stream()
                            .sorted()
                            .collect(Collectors.toList());
                })
        ).thenApplyAsync(sortedItems -> {
            System.out.println("Обчислення кількості товарів");
            int count = sortedItems.size();
            return "Список: " + sortedItems + " | Кількість: " + count;
        }).thenAcceptAsync(finalResult ->
                System.out.println("Результат після обробки: " + finalResult)
        ).thenRunAsync(() ->
                System.err.println("Усі задачі успішно завершено!")
        ).exceptionally(ex -> {
            System.err.println("Сталася помилка: " + ex.getMessage());
            return null;
        }).join();

    }
}
