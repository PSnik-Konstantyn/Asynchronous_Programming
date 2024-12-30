package org.example.pz6;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
@EnableScheduling
public class AsyncScheduledApplication implements ApplicationContextAware {

    private volatile int counter = 0;
    private final Object lock = new Object();
    private ApplicationContext applicationContext;

    public static void main(String[] args) {
        SpringApplication.run(AsyncScheduledApplication.class, args);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
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
                            shutdownApplication();
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

    private void shutdownApplication() {
        SpringApplication.exit(applicationContext, () -> 0);
        System.exit(0);
    }
}
