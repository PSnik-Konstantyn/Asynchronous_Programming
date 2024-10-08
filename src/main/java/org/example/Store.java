package org.example;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class Store {
    private final Semaphore semaphore;
    private final List<Product> products = new ArrayList<>();
    private boolean isOpen = true;

    public Store(int maxProducts) {
        semaphore = new Semaphore(maxProducts, true);
    }

    public synchronized void addProduct(Product product) {
        if (isOpen) {
            products.add(product);
            System.out.println("Адміністратор додав товар: " + product.getName());
            notifyAll();
        } else {
            System.out.println("Магазин зачинений, не можна додати товар.");
        }
    }

    public synchronized void buyProduct(String productName) {
        try {
            if (!isOpen) {
                System.out.println("Магазин зачинений, купівля неможлива.");
                return;
            }

            semaphore.acquire();

            Product productToBuy = null;
            for (Product product : products) {
                if (product.getName().equals(productName)) {
                    productToBuy = product;
                    break;
                }
            }

            if (productToBuy != null) {
                products.remove(productToBuy);
                System.out.println("Покупець купив: " + productToBuy.getName());
            } else {
                System.out.println(productName + " відсутній в магазині.");
            }

        } catch (InterruptedException e) {
            System.out.println("Помилка при покупці товару.");
        } finally {
            semaphore.release();
        }
    }

    public synchronized void closeStore() {
        isOpen = false;
        System.out.println("Магазин зачиняється.");
    }
}
