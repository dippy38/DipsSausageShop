package com.example.jhutti.myapplication;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by mjhutti on 17/11/2014.
 */
public class Basket {

    //TODO Add private constructor and singleton methodology

    public static List<BasketItem>myBasket = new ArrayList<BasketItem>();


    public static void addItem(BasketItem myItem){
        myBasket.add(myItem);
    }

    public static void removeItem(BasketItem myItem){
        myBasket.remove(myItem);
    }

    public static void emptyBasket(){
        myBasket.clear();
    }

    public static String displayBasket(){
        String basketContents="";
        for (BasketItem item : myBasket) {
            basketContents = basketContents + item.getIdentifier() + " " + item.getDescription()+ " " + item.getPrice() + "\r\n ";
        }
        if (basketContents.length()<=2) {basketContents="BASKET EMPTY";}
        return basketContents;
    }

}
