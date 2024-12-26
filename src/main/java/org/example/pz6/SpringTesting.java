package org.example.pz6;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;

import java.time.LocalTime;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
@EnableAsync
public class SpringTesting implements CommandLineRunner {

    private volatile boolean stopTasks = false;

    public static void main(String[] args) {
        SpringApplication.run(SpringTesting.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        CompletableFuture<Void> task1 = incrementCounter();
        CompletableFuture<Void> task2 = printTime();
        CompletableFuture.allOf(task1, task2).join();
        System.out.println("Усі задачі завершено.");
    }

    @Async
    public CompletableFuture<Void> incrementCounter() {
        return CompletableFuture.runAsync(() -> {
            int counter = 0;
            try {
                while (counter < 10) {
                    counter++;
                    System.out.println("Лічильник: " + counter);
                    TimeUnit.SECONDS.sleep(2);
                }
                System.out.println("Лічильник досяг значення 10. Завершення.");
                stopTasks = true;
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Помилка в задачі збільшення лічильника: " + e.getMessage());
            }
        });
    }

    @Async
    public CompletableFuture<Void> printTime() {
        return CompletableFuture.runAsync(() -> {
            try {
                while (!stopTasks) {
                    System.out.println("Час: " + LocalTime.now());
                    TimeUnit.SECONDS.sleep(5);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Помилка в задачі виводу часу: " + e.getMessage());
            }
        });
    }
}
