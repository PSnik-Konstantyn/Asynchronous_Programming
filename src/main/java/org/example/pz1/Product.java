package org.example.pz1;

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

    public synchronized boolean purchase() {
        if (quantity > 0) {
            quantity--;
            return true;
        }
        return false;
    }

}
