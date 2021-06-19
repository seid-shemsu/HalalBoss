package com.izhar.halalboss.models;

public class Food {
    String name, id, price, rate, url;

    public Food() {
    }

    public Food(String name, String id, String price, String rate, String url) {
        this.name = name;
        this.id = id;
        this.price = price;
        this.rate = rate;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getPrice() {
        return price;
    }

    public String getRate() {
        return rate;
    }

    public String getUrl(){
        return url;
    }
}
