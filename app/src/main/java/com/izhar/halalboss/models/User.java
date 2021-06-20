package com.izhar.halalboss.models;

public class User {
    String name, phone, uid, user_image;

    public User() {
    }

    public User(String name, String phone, String uid, String user_image) {
        this.name = name;
        this.phone = phone;
        this.uid = uid;
        this.user_image = user_image;
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

    public String getUser_image() { return user_image; }
}
