package org.example;

public class Customer implements Runnable {
    private Store store;
    private String productName;

    public Customer(Store store, String productName) {
        this.store = store;
        this.productName = productName;
    }

    @Override
    public void run() {
        try {
            store.buyProduct(productName);
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            System.out.println("Помилка в роботі покупця.");
        }
    }
}