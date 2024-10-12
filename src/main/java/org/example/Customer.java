package org.example;

public class Customer implements Runnable {
    private Store store;
    private String productName;
    private String customerName;

    public Customer(Store store, String productName, String customerName) {
        this.store = store;
        this.productName = productName;
        this.customerName = customerName;
    }

    public void run() {
        store.buyProduct(productName, customerName);

    }
}
