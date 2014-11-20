package com.example.jhutti.myapplication;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
/**
 * Created by mjhutti on 17/11/2014.
 */
public class Basket {

    //TODO Add private constructor and singleton methodology

    public static ArrayList<BasketItem>myBasket = new ArrayList<BasketItem>();
    public static String currency = "GBP";
    public static String currencyISO = "£";


    public static void addItem(BasketItem myItem){
        myBasket.add(myItem);
    }

    public static void removeItem(BasketItem myItem){
        myBasket.remove(myItem);
    }

    public static void emptyBasket(){
        myBasket.clear();
    }

    public static String getBasket(){
        String basketContents="";
        for (BasketItem item : myBasket) {
            basketContents = basketContents + item.getIdentifier() + "          " + item.getDescription() + "          " + item.getPrice() + "\r\n ";
        }
        if (basketContents.length()<=2) {basketContents="BASKET EMPTY";}
        return basketContents;
    }


    public static ArrayList<String> getBasketList(){
        ArrayList<String> stringBasket = new ArrayList<String>();
        int i =0;

        for (BasketItem item : myBasket) {
            stringBasket.add(i,item.getIdentifier() + "          " + item.getDescription() + "          " + item.getPrice() );
        }
        Collections.reverse(stringBasket);
        return stringBasket;
    }

    public static ArrayList<String> getFullBasketList(){
        ArrayList<String> stringBasket = new ArrayList<String>();
        stringBasket.add(0,"Identifier" + "          " + "Description" + "          " + "Price");
        int i =0;

        for (BasketItem item : myBasket) {
            stringBasket.add(i,item.getIdentifier() + "          " + item.getDescription() + "          " + currencyISO + item.getPrice() );
        }
        Collections.reverse(stringBasket);
        return stringBasket;
    }

}
