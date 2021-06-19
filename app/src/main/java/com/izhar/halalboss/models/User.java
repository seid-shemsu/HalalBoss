package com.izhar.halalboss.models;

public class User {
    String name, phone, uid;

    public User() {
    }

    public User(String name, String phone, String uid) {
        this.name = name;
        this.phone = phone;
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getUid() {
        return uid;
    }
}
