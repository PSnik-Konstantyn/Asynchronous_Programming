package org.example.pz1;

public class Admin implements Runnable {
    private Store store;

    public Admin(Store store) {
        this.store = store;
    }

    public void run() {
        try {
            // Додавання кількох товарів адміністратором
            store.addProduct(new Product("Телефон", 1));
            Thread.sleep(1000);
            store.addProduct(new Product("Ноутбук", 1));
            Thread.sleep(1000);
            store.addProduct(new Product("Епл вотч", 2));
        } catch (InterruptedException e) {
            System.out.println("Адміністратор не зміг додати товари через помилку.");
        }

        // Через деякий час магазин зачиняється
        try {
            System.err.println("Магазин відкрився.");
            Thread.sleep(9000); // Магазин працює 5 секунд
            store.closeStore();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
