package com.example.jhutti.myapplication;


public class BasketItem {

    public double getIdentifier() {
        return identifier;
    }

    public void setIdentifier(float identifier) {
        this.identifier = identifier;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    private double identifier;
    private String description;
    private double price;

    public  BasketItem(double identifier, String desc, double price){
        this.identifier=identifier;
        this.description=desc;
        this.price=price;

    }



}
