package org.example;

public class Product {
    private String name;
    private int quantity;

    public Product(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    // Синхронізований метод для покупки товару
    public synchronized boolean purchase() {
        if (quantity > 0) {
            quantity--;
            return true;
        }
        return false;
    }

    public int getQuantity() {
        return quantity;
    }

    // Метод для додавання товару на склад
    public void restock(int amount) {
        quantity += amount;
    }
}
