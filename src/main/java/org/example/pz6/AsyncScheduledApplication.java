package org.example.pz6;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
@EnableScheduling
public class AsyncScheduledApplication {

    private volatile int counter = 0;
    private final Object lock = new Object();

    public static void main(String[] args) {
        SpringApplication.run(AsyncScheduledApplication.class, args);
    }

    @Bean
    public CommandLineRunner run() {
        return args -> {
            ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
            executorService.scheduleAtFixedRate(() -> {
                synchronized (lock) {
                    if (counter < 10) {
                        counter++;
                        System.out.println("Лічильник: " + counter);
                        if (counter == 10) {
                            System.out.println("Лічильник досяг 10. Зупинка...");
                            executorService.shutdown();
                        }
                    }
                }
            }, 0, 2, TimeUnit.SECONDS);
        };
    }

    @Scheduled(fixedRate = 5000)
    public void printCurrentTime() {
        synchronized (lock) {
            if (counter < 10) {
                System.out.println("Час: " + LocalTime.now());
            }
        }
    }
}
