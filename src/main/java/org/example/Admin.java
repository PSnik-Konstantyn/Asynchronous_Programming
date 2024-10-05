package org.example;

public class Admin implements Runnable {
    private Store store;

    public Admin(Store store) {
        this.store = store;
    }

    @Override
    public void run() {
        try {
            for (int i = 1; i <= 5; i++) {
                store.addProduct(new Product("Товар " + i));
                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
            System.out.println("Помилка в роботі адміністратора.");
        }
    }
}
