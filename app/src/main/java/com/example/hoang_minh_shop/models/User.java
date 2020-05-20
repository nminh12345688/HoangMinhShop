package com.example.hoang_minh_shop.models;

import androidx.annotation.NonNull;

public class User {
    private String phoneNumber, fullName, address;

    public User(String phoneNumber, String fullName, String address) {
        this.phoneNumber = phoneNumber;
        this.fullName = fullName;
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getFullName() {
        return fullName;
    }

    public String getAddress() {
        return address;
    }


}
