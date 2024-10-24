package org.example.pz1;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class Store {
    private List<Product> products = new ArrayList<>(); // Список товарів
    private Semaphore semaphore; // Для контролю кількості покупців
    private boolean isOpen = true; // Флаг для відстеження стану магазину

    public Store(int maxCustomers) {
        this.semaphore = new Semaphore(maxCustomers, true); // Обмежує кількість покупців
    }

    // Метод для додавання товару адміністратором
    public void addProduct(Product product) {
        products.add(product);
        System.out.println("Адміністратор додав товар: " + product.getName());
    }

    public void buyProduct(String productName, String customerName) {
        try {
            semaphore.acquire(); // Покупець чекає доступу до товару

            synchronized (this) {
                if (!isOpen) {
                    System.out.println(customerName + " прийшов у магазин, але він вже зачинений.");
                    return; // Вийти, якщо магазин вже закритий
                }
            }

            Product product = findProduct(productName);
            Thread.sleep(3000); // Симуляція часу на покупку

            synchronized (this) {
                if (!isOpen) {
                    System.out.println(customerName + " не зміг завершити покупку, бо магазин зачинено.");
                    return; // Магазин закрився під час покупки
                }
            }

            if (product != null) {
                synchronized (product) {
                    if (product.purchase()) {
                        System.out.println(customerName + " придбав " + product.getName());
                    } else {
                        System.out.println(customerName + " хотів придбати " + product.getName() + ", але його немає в наявності.");
                    }
                }
            } else {
                System.out.println("Товар " + productName + " не знайдено.");
            }
        } catch (InterruptedException e) {
            System.out.println(customerName + " не зміг завершити покупку через помилку.");
        } finally {
            semaphore.release(); // Звільняємо семафор
        }
    }

    // Метод для пошуку товару за назвою
    private Product findProduct(String productName) {
        for (Product product : products) {
            if (product.getName().equals(productName)) {
                return product;
            }
        }
        return null;
    }

    public synchronized void closeStore() {
        isOpen = false;
        System.err.println("Магазин зачинено.");
    }
}
