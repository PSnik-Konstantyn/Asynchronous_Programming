package org.example.pz5;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadLocalRandom;

public class BestRoute {
    public static void main(String[] args) {
        CompletableFuture<Route> trainOption = CompletableFuture.supplyAsync(() ->
                checkOption("Потяг", 5, 100)
        );

        CompletableFuture<Route> busOption = CompletableFuture.supplyAsync(() ->
                checkOption("Автобус", 7, 70)
        );

        CompletableFuture<Route> flightOption = CompletableFuture.supplyAsync(() ->
                checkOption("Літак", 2, 200)
        );

        CompletableFuture<Route> bestTrainOrBus = trainOption.thenCombine(busOption,
                (r1, r2) -> BestRoute.compareRoutes(r1, r2));

        CompletableFuture<Route> bestOverall = bestTrainOrBus.thenCombine(flightOption,
                (r1, r2) -> BestRoute.compareRoutes(r1, r2));


        CompletableFuture<Void> allChecks = CompletableFuture.allOf(trainOption, busOption, flightOption);

        CompletableFuture<String> summary = allChecks.thenApply(v -> {
            try {
                return "Потяг: " + trainOption.get() + "\n" +
                       "Автобус: " + busOption.get() + "\n" +
                       "Літак: " + flightOption.get();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        });

        CompletableFuture<Object> firstResult = CompletableFuture.anyOf(trainOption, busOption, flightOption);

        try {
            System.out.println("Найкращий маршрут: " + bestOverall.get());
            System.out.println("Огляд всіх варіантів:\n" + summary.get());
            System.out.println("Перший доступний результат: " + firstResult.get());
        } catch (InterruptedException | ExecutionException e) {
            System.out.println("Упс! Помилка");
        }
    }

    private static Route checkOption(String transport, int durationHours, int cost) {
        simulateDelay(ThreadLocalRandom.current().nextInt(500, 2000));
        return new Route(transport, durationHours, cost);
    }

    private static Route compareRoutes(Route r1, Route r2) {
        // fix the logic
        double r1Score = r1.durationHours * 0.7 + r1.cost * 0.3;
        double r2Score = r2.durationHours * 0.7 + r2.cost * 0.3;
        return r1Score < r2Score ? r1 : r2;
    }

    private static void simulateDelay(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
