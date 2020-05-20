package com.example.hoang_minh_shop.models;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Product implements Serializable {
    private int id;

    private String name;

    private String image;

    private String description;

    private long price;

    private int amount;

    private String phoneNumber;

    public Product(int id, String name, String image, String description, int price) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.description = description;
        this.price = price;
    }

    public Product( String phoneNumber, int id, String name, String image, long price, int amount) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.price = price;
        this.amount = amount;
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public int getAmount() {
        return amount;
    }

    public int getId() {
        return id;
    }


    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public String getDescription() {
        return description;
    }

    public long getPrice() {
        return price;
    }

    @NonNull
    @Override
    public String toString() {
        return phoneNumber + " " + id + " " + name + " " + image + " " + price + " " + amount + "\n";
    }
}
