package com.izhar.halalboss.models;

import java.util.List;

public class Order {
    String id, total_price, address, status;
    List<Food> foods;
    User user;

    public Order() {
    }

    public Order(String id, String total_price, String address, String status, List<Food> foods, User user) {
        this.id = id;
        this.total_price = total_price;
        this.address = address;
        this.status = status;
        this.foods = foods;
        this.user = user;
    }

    public String getId() {
        return id;
    }

    public String getTotal_price() {
        return total_price;
    }

    public String getAddress() {
        return address;
    }

    public String getStatus() {
        return status;
    }

    public List<Food> getFoods() {
        return foods;
    }

    public User getUser() {
        return user;
    }
}
