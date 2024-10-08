package org.example;

public class Main {
    public static void main(String[] args) {
        Store store = new Store(2);

        Thread admin = new Thread(new Admin(store));
        Thread customer1 = new Thread(new Customer(store, "Товар 1"));
        Thread customer2 = new Thread(new Customer(store, "Товар 2"));
        Thread customer3 = new Thread(new Customer(store, "Товар 6"));
        Thread customer4 = new Thread(new Customer(store, "Товар 3"));
        Thread customer5 = new Thread(new Customer(store, "Товар 3"));

        admin.start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println("Помилка під час очікування.");
        }

        System.err.println("Покупець 1 намагається купити товар...");
        customer1.start();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            System.out.println("Помилка під час очікування.");
        }

        System.err.println("Покупець 2 намагається купити товар...");
        customer2.start();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            System.out.println("Помилка під час очікування.");
        }

        System.err.println("Покупець 3 намагається купити товар...");
        customer3.start();

        System.err.println("Покупець 4 намагається купити товар...");
        customer4.start();


        try {
            Thread.sleep(10000);
            store.closeStore();
        } catch (InterruptedException e) {
            System.out.println("Помилка під час очікування.");
        }

        System.err.println("Покупець 5 намагається купити товар...");
        customer5.start();
    }
}
