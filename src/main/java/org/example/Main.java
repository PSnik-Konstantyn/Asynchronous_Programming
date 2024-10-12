package org.example;

public class Main {
    public static void main(String[] args) {
        Store store = new Store(2); // Максимально 2 покупці одночасно

        // Створюємо потік для адміністратора
        Admin admin = new Admin(store);
        Thread adminThread = new Thread(admin);

        // Створюємо покупців
        Customer customer1 = new Customer(store, "Телефон", "Покупець 1");
        Customer customer2 = new Customer(store, "Ноутбук", "Покупець 2");
        Customer customer3 = new Customer(store, "Телевізор", "Покупець 3");
        Customer customer4 = new Customer(store, "Телефон", "Покупець 4");
        Customer customer5 = new Customer(store, "Телефон", "Покупець 5");

        Thread customerThread1 = new Thread(customer1);
        Thread customerThread2 = new Thread(customer2);
        Thread customerThread3 = new Thread(customer3);
        Thread customerThread4 = new Thread(customer4);
        Thread customerThread5 = new Thread(customer5);

        // Запуск потоку адміністратора
        adminThread.start();
        try {
            Thread.sleep(2000); // Чекаємо, поки адмін додасть товари
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Запуск потоків покупців
        customerThread1.start();
        customerThread4.start(); // Намагається купити товар, що закінчився
        customerThread3.start();
        customerThread2.start();


        try {
            Thread.sleep(5000); // Переконуємося, що магазин закрився
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        customerThread5.start();
    }
}
