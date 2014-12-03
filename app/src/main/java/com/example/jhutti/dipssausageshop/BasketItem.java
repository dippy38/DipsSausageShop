package com.example.jhutti.dipssausageshop;


import java.math.BigDecimal;
import java.math.RoundingMode;

public class BasketItem {

    public int getIdentifier() {
        return identifier;
    }

    public String getIdentifierString() {
        return String.valueOf(identifier);
    }
    public void setIdentifier(int identifier) {
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

    public BigDecimal getPriceDecimal() {
        return new BigDecimal(price).setScale(2, RoundingMode.CEILING);
    }

    public void setPrice(float price) {
        this.price = price;
    }

    private int identifier;
    private String description;
    private double price;

    public  BasketItem(int identifier, String desc, double price){
        this.identifier=identifier;
        this.description=desc;
        this.price=price;

    }



}
