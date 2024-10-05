package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class Store {
    private List<Product> products = new ArrayList<>();
    private Semaphore semaphore;
    private boolean isOpen = true;

    public Store(int maxProducts) {
        semaphore = new Semaphore(maxProducts, true);
    }

    public void addProduct(Product product) {
        try {
            if (isOpen) {
                semaphore.acquire();
                products.add(product);
                System.out.println("Адміністратор додав товар: " + product.getName());
            } else {
                System.out.println("Магазин зачинений, не можна додати товар.");
            }
        } catch (InterruptedException e) {
            System.out.println("Помилка під час додавання товару.");
        } finally {
            semaphore.release();
        }
    }

    public void buyProduct(String productName) {
        try {
            if (isOpen) {
                semaphore.acquire();
                Product product = findProduct(productName);
                if (product != null) {
                    products.remove(product);
                    System.out.println("Покупець купив товар: " + product.getName());
                } else {
                    System.out.println("Товар " + productName + " відсутній в магазині.");
                }
            } else {
                System.out.println("Магазин зачинений, купівля неможлива.");
            }
        } catch (InterruptedException e) {
            System.out.println("Помилка під час купівлі товару.");
        } finally {
            semaphore.release();
        }
    }

    private Product findProduct(String productName) {
        for (Product product : products) {
            if (product.getName().equals(productName)) {
                return product;
            }
        }
        return null;
    }

    public void closeStore() {
        isOpen = false;
        System.out.println("Магазин зачиняється.");
    }
}